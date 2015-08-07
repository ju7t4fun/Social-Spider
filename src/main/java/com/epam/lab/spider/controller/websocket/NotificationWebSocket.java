package com.epam.lab.spider.controller.websocket;

import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.controller.utils.Receiver;
import com.epam.lab.spider.controller.utils.Sender;
import com.epam.lab.spider.model.entity.Event;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.EventService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boyarsky Vitaliy
 */
@ServerEndpoint(value = "/websocket/notification", configurator = GetHttpSessionConfigurator.class)
public class NotificationWebSocket implements Receiver {

    private static final Logger LOG = Logger.getLogger(NotificationWebSocket.class);
    private static final List<Sender> senders = new ArrayList<>();
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static EventService service = factory.create(EventService.class);
    private static Map<Integer, Session> sessions = new HashMap<>();
    private int id;

    public NotificationWebSocket() {
        senders.add(new EventLogger.NotificationSender());
        for (Sender sender : senders) {
            sender.accept(this);
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = getHttpSession(config);
        User user = (User) httpSession.getAttribute("user");
        id = user.getId();
        sessions.put(id, session);
        try {
            sendCountNotification(id);
            List<Event> events = service.getLastUnShownByUserId(id, 5);
            for (Event event : events) {
                send(id, event);
            }
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        if (LOG.isDebugEnabled())
            LOG.debug("onOpen (clientId=" + id + ")");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (LOG.isDebugEnabled())
            LOG.debug("onMessage (clientId=" + id + ")");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessions.remove(id);
        if (LOG.isDebugEnabled())
            LOG.debug("onClose (clientId=" + id + ")");
    }

    @OnError
    public void onError(Session session, Throwable t) {
        LOG.warn(t.getMessage());
    }

    private HttpSession getHttpSession(EndpointConfig config) {
        return (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    }

    private void sendCountNotification(int id) throws IOException {
        if (sessions.containsKey(id)) {
            Session session = sessions.get(id);
            int count = service.getCountUnShownNotificationByUserId(id);
            session.getBasicRemote().sendText("count|" + count);
        }
    }

    public boolean send(int id, Event event) {
        String msg = "notf|" + event.getType().toString() + "|" + event.getTitle() + "|" +
                new SimpleDateFormat("HH:mm").format(event.getTime());
        return send(id, msg);
    }

    @Override
    public void visit(Sender sender) {
        sender.accept(this);
    }

    @Override
    public boolean send(int id, String message) {
        if (sessions.containsKey(id)) {
            try {
                Session session = sessions.get(id);
                int count = service.getCountUnShownNotificationByUserId(id);
                session.getBasicRemote().sendText("count|" + count);
                session.getBasicRemote().sendText(message);
                return true;
            } catch (IllegalStateException e) {
                LOG.warn(e.getMessage());
            } catch (IOException e) {
                LOG.error(e.getLocalizedMessage(), e);
            }
        }
        return false;
    }

}
