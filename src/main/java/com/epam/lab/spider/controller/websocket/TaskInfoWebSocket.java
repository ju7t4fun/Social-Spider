package com.epam.lab.spider.controller.websocket;


import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.TaskService;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/websocket/task-info", configurator = GetHttpSessionConfigurator.class)
public class TaskInfoWebSocket {

    private static final Logger LOG = Logger.getLogger(TaskInfoWebSocket.class);

    private static final TaskService taskService = new TaskService();
    private static final Map<Session, Integer> sessions = new HashMap<>();
    private static final Map<Integer,List<Session>> sessionsBack = new HashMap<>();

    private HttpSession getHttpSession(EndpointConfig config) {
        return (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = getHttpSession(config);
        User user = (User) httpSession.getAttribute("user");

        if (sessions.containsKey(user.getId())) {
            LOG.fatal("FATAL STATE");
        }else{
            sessions.put(session, user.getId());
            List<Session> userSessions;
            if(sessionsBack.containsKey(user.getId())){
                userSessions = sessionsBack.get(user.getId());
            }else{
                userSessions = new ArrayList<>();
                sessionsBack.put(user.getId(),userSessions);
            }
            userSessions.add(session);
//            AutoWebSocket autoWebSocket = new AutoWebSocket(user);
//            new Thread(autoWebSocket).start();
        }
    }

    public static String formatJSON(Integer taskId, String state, Long unixTimeMS){

        JSONObject response = new JSONObject();

        JSONArray responseJSONArray = new JSONArray();
        JSONObject taskStateCell = new JSONObject();
        taskStateCell.put("id", taskId);
        if (state == "runnable") {
            taskStateCell.put("state", "runnable");
            taskStateCell.put("unix_time_ms", unixTimeMS);
        } else if (state == "running") {
            taskStateCell.put("state", "running");
        } else {
            taskStateCell.put("state", "stopped");
        }
        responseJSONArray.add(taskStateCell);
        response.put("state", "success");
        response.put("data", responseJSONArray);
        return response.toString();
    }
    public static boolean sendTaskInfo(Integer userId, Integer taskId, String state, Long unixTimeMS ) {
        try {
            if (sessions.containsValue(userId)) {
                String message = formatJSON(taskId,state,unixTimeMS);
                for(Session session:sessionsBack.get(userId)){
                    session.getBasicRemote().sendText(message);
                }
                return true;
            }
        } catch (IOException|RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Integer userId = sessions.get(session);
        JSONObject response = new JSONObject();
        boolean noEmpty = true;
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(message);
            JSONArray responseJSONArray = new JSONArray();
            if(jsonArray.isEmpty()){
                noEmpty = false;
            }else {
                for (int i = 0, size = jsonArray.size(); i < size; i++) {

                    try{
                        String taskIdString = jsonArray.get(i).toString();
                        JSONObject taskStateCell = new JSONObject();
                        Integer taskId = Integer.parseInt(taskIdString);
                        Task task = taskService.getByIdAndLimitByUserId(taskId, userId);
                        taskStateCell.put("id", taskIdString);
                        if(task.getState() == Task.State.RUNNING) {
                            taskStateCell.put("state", "runnable");
                            taskStateCell.put("unix_time_ms", task.getNextTaskRunDate().getTime());
                        }else{
                            taskStateCell.put("state", "stopped");
                        }
                        responseJSONArray.add(taskStateCell);
                    }catch (Exception x){
                        x.printStackTrace();
                    }

                }
                response.put("state", "success");
                response.put("data", responseJSONArray);
            }
        } catch (ParseException| RuntimeException e) {
            e.printStackTrace();
            response.put("state","error");
            response.put("error",e.getClass().getName().toString());
        }
        try {
            if(noEmpty)session.getBasicRemote().sendText(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public static boolean send(Integer userId, String message) {
        try {
            if (sessions.containsValue(userId)) {

                for(Session session:sessionsBack.get(userId)){
                    LOG.info("Session " + session + " id " + userId + " - " + message);
                    session.getBasicRemote().sendText(message);
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    static class AutoWebSocket implements Runnable {

        private User user;
        boolean stop = false;

        public AutoWebSocket(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            try {
                while (!stop) {
                    send(user.getId(), "Test Message");
                    Thread.sleep(5000);
                    if(!sessionsBack.containsKey(user.getId())){
                        stop = true;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOG.info("END OF RUN FOR USER " + user.getId());
        }
    }
}
