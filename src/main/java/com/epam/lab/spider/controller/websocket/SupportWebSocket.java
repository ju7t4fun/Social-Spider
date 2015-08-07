package com.epam.lab.spider.controller.websocket;

import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.controller.utils.ReplaceHtmlTags;
import com.epam.lab.spider.model.entity.Message;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.persistence.service.MessageService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Boyarsky Vitaliy
 */
@ServerEndpoint(value = "/websocket/support", configurator = GetHttpSessionConfigurator.class)
public class SupportWebSocket {

    private static final Logger LOG = Logger.getLogger(SupportWebSocket.class);

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static MessageService service = factory.create(MessageService.class);
    private static UserService userService = factory.create(UserService.class);

    private static Map<Integer, Session> sessions = new HashMap<>();
    private static Set<Integer> admins = new HashSet<>();

    private int id;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = getHttpSession(config);
        User user = (User) httpSession.getAttribute("user");
        id = user.getId();
        sessions.put(id, session);
        try {
            switch (user.getRole()) {
                case USER:
                    userSendCountUnReadMessages();
                    userSendStartDialog();
                    break;
                case ADMIN:
                    admins.add(id);
                    adminSendCountUnReadMessages();
                    adminSendLastUnReadMessages();
                    break;
            }
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        if (LOG.isDebugEnabled())
            LOG.debug("onOpen (clientId=" + id + ")");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        message = ReplaceHtmlTags.reaplaceAll(message);
        String[] args = message.split("\\|");
        if (LOG.isDebugEnabled())
            LOG.debug("onMessage. Execute command " + Command.valueOf(args[0].toUpperCase()) + " params " + Arrays
                    .toString(args));
        try {
            switch (Command.valueOf(args[0].toUpperCase())) {
                case TO_ADMIN: {
                    Message msg = BasicEntityFactory.getSynchronized().createMessage();
                    msg.setUserId(id);
                    msg.setText(args[1]);
                    msg.setType(Message.Type.TO_ADMIN);
                    service.insert(msg);
                    session.getBasicRemote().sendText("me|" + msg.getText() + "|" + new SimpleDateFormat("HH:mm:ss")
                            .format(msg.getDate()));
                    adminNotifyAll(id, msg);
                    break;
                }
                case SCROLL: {
                    int offset = Integer.parseInt(args[1]);
                    int limit = Integer.parseInt(args[2]);
                    List<Message> messages = service.getByUserId(id, offset, limit);
                    for (Message msg : messages) {
                        session.getBasicRemote().sendText("previous|" + msg.getType().toString() + "|" + msg.getText()
                                + "|" + new SimpleDateFormat("HH:mm:ss").format(msg.getDate()));
                    }
                    break;
                }
                case READ:
                    service.markAsReadByUserId(id);
                    break;
                case TO_USER:
                    Message msg = adminSendUser(args);
                    session.getBasicRemote().sendText("me|" + msg.getText() + "|" + new SimpleDateFormat("HH:mm:ss")
                            .format(msg.getDate()));
                    break;
            }
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessions.remove(id);
        if (admins.contains(id))
            admins.remove(id);
        if (LOG.isDebugEnabled())
            LOG.debug("onClose (clientId=" + id + ") " + closeReason.getCloseCode());
    }

    @OnError
    public void onError(Session session, Throwable t) {
        LOG.warn(t.getMessage());
    }

    private HttpSession getHttpSession(EndpointConfig config) {
        return (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    }

    /**
     * USER
     */

    private void userSendCountUnReadMessages() throws IOException {
        Session session = sessions.get(id);
        int count = service.getCountUnReadByUserId(id);
        session.getBasicRemote().sendText("count|" + count);
    }

    private void userSendStartDialog() throws IOException {
        Session session = sessions.get(id);
        List<Message> messages = service.getByUserId(id, 0, 6);
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message msg = messages.get(i);
            String sender = msg.getType() == Message.Type.TO_ADMIN ? "me" : "admin";
            session.getBasicRemote().sendText(sender + "|" + msg.getText() + "|" + new SimpleDateFormat("HH:mm:ss")
                    .format(msg.getDate()));
        }
    }

    /**
     * ADMIN
     */

    private void adminSendCountUnReadMessages() throws IOException {
        Session session = sessions.get(id);
        int count = service.getCountUnReadByAdminId();
        session.getBasicRemote().sendText("count|" + count);
    }

    private void adminSendLastUnReadMessages() throws IOException {
        Session session = sessions.get(id);
        List<Message> messages = service.getLastUnReadMessagesByAdmin(5);
        List<User> users = userService.getAll();
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message msg = messages.get(i);
            User user = getById(users, msg.getUserId());
            session.getBasicRemote().sendText("new|" + user.getId() + "|" + user.getName() + " " + user.getSurname() +
                    "|" + new SimpleDateFormat("HH:mm:ss").format(msg.getDate()));
        }
    }

    private Message adminSendUser(String[] args) throws IOException {
        int id = Integer.parseInt(args[1]);
        Message msg = BasicEntityFactory.getSynchronized().createMessage();
        msg.setUserId(id);
        msg.setText(args[2]);
        msg.setType(Message.Type.TO_USER);
        service.insert(msg);
        EventLogger.getLogger(id).info("Нове повідомлення", msg.getText());
        if (sessions.containsKey(id)) {
            Session session = sessions.get(id);
            int count = service.getCountUnReadByUserId(id);
            session.getBasicRemote().sendText("count|" + count);
            session.getBasicRemote().sendText("new|");
            session.getBasicRemote().sendText("admin|" + msg.getText() + "|" + new SimpleDateFormat
                    ("HH:mm:ss").format(msg.getDate()));
        }
        return msg;
    }

    private User getById(List<User> users, int id) {
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    private void adminNotifyAll(int userId, Message msg) throws IOException {
        User user = userService.getById(userId);
        int count = service.getCountUnReadByAdminId();
        for (Integer adminId : admins) {
            Session session = sessions.get(adminId);
            session.getBasicRemote().sendText("count|" + count);
            session.getBasicRemote().sendText("new|" + user.getId() + "|" + user.getName() + " " + user.getSurname()
                    + "|" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "|" + msg.getText());
            session.getBasicRemote().sendText("updateCountList|" + userId);
        }
    }

    private enum Command {
        TO_ADMIN, TO_USER, SCROLL, READ
    }

}
