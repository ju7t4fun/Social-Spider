package com.epam.lab.spider.controller.websocket;


import com.epam.lab.spider.model.entity.User;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint(value = "/websocket/limits-info", configurator = GetHttpSessionConfigurator.class)
public class LimitsInfo {

    private static final Logger LOG = Logger.getLogger(LimitsInfo.class);

    private static final Map<Session, Integer> sessions = new HashMap<>();
    private static final Map<Integer,List<Session>> sessionsBack = new HashMap<>();

    public static String formatJSON(String changeType,int value, int percent){

        JSONObject response = new JSONObject();


        JSONObject taskStateCell = new JSONObject();
        taskStateCell.put("value", value);
        taskStateCell.put("percent", percent);

        response.put("change", changeType);
        response.put("data", taskStateCell);
        return response.toString();
    }

    public static boolean sendLimitInfo(Integer userId, String changeType,int value, int percent ) {
        try {
            if (sessionsBack.containsKey(userId)) {
                String message = formatJSON(changeType,value,percent);
                for(Session session:sessionsBack.get(userId)){
                    session.getBasicRemote().sendText(message);
                }
                return true;
            }
        } catch (IOException|RuntimeException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public static boolean sendTaskLimitInfo(Integer userId, int value, int percent ) {
        return sendLimitInfo(userId, "task", value, percent);
    }

    public static boolean sendPostLimitInfo(Integer userId, int value, int percent ) {
        return sendLimitInfo(userId,"post",value,percent);
    }

    private HttpSession getHttpSession(EndpointConfig config) {
        return (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = getHttpSession(config);
        User user = (User) httpSession.getAttribute("user");

        AutoWebSocket autoWebSocket = new AutoWebSocket(user);
        new Thread(autoWebSocket).start();
        {
            sessions.put(session, user.getId());
            List<Session> userSessions;
            if (sessionsBack.containsKey(user.getId())) {
                userSessions = sessionsBack.get(user.getId());
            } else {
                userSessions = new ArrayList<>();
                sessionsBack.put(user.getId(), userSessions);
            }
            userSessions.add(session);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
//        LOG.debug("onMessage (message=" + message + ")");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {

        if (!sessions.containsKey(session)) {
            LOG.fatal("Проблеми з архітектурою");
        } else {
            Integer userId = sessions.remove(session);
            if(userId!=null){
                List<Session> userSessions;
                userSessions = sessionsBack.get(userId);
                userSessions.remove(session);
                if(userSessions.isEmpty()){
                    sessionsBack.remove(userId);
                }
            }
            LOG.debug("onClose (clientId=" + userId + ")");

        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }
    static class AutoWebSocket implements Runnable {

        boolean stop = false;
        private User user;

        public AutoWebSocket(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            try {
                Random random = new Random();
                while (!stop) {
                    int value = random.nextInt(30);
                    int percent = (int)(value* 100. / 30);
                    sendPostLimitInfo(user.getId(), value, percent );
                    Thread.sleep(5000);
                    if(!sessionsBack.containsKey(user.getId())){
                        stop = true;
                    }
                }
            } catch (InterruptedException e) {
                LOG.error(e.getLocalizedMessage(), e);
            }
            LOG.info("END OF RUN FOR USER " + user.getId());
        }
    }
}
