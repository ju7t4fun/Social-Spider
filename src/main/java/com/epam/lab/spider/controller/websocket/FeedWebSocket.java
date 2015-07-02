package com.epam.lab.spider.controller.websocket;

import com.epam.lab.spider.model.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 01.07.2015.
 */
@ServerEndpoint(value = "/websocket/feed", configurator = GetHttpSessionConfigurator.class)
public class FeedWebSocket {

    private static final Logger LOG = Logger.getLogger(FeedWebSocket.class);
    private static Map<Integer, FeedWebSocket> sessions = new HashMap<>();
    private Session session;
    private User user;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = getHttpSession(config);
        user = (User) httpSession.getAttribute("user");
        this.session = session;
        if (LOG.isDebugEnabled())
            LOG.debug("onOpen (clientId=" + user.getId() + ")");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (LOG.isDebugEnabled())
            LOG.debug("onMessage (clientId=" + user.getId() + ")");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessions.remove(user.getId());
        if (LOG.isDebugEnabled())
            LOG.debug("onClose (clientId=" + user.getId() + ")");
    }

    @OnError
    public void onError(Session session, Throwable t) {
        LOG.warn(t.getMessage());
    }

    private HttpSession getHttpSession(EndpointConfig config) {
        return (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    }

}
