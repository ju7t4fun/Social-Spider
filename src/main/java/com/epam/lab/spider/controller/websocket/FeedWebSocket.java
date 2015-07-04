package com.epam.lab.spider.controller.websocket;

import com.epam.lab.spider.controller.utils.Receiver;
import com.epam.lab.spider.controller.utils.Sender;
import com.epam.lab.spider.job.util.Feed;
import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.CategoryService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 01.07.2015.
 */
@ServerEndpoint(value = "/websocket/feed", configurator = GetHttpSessionConfigurator.class)
public class FeedWebSocket implements Receiver {

    private static final Logger LOG = Logger.getLogger(FeedWebSocket.class);
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static CategoryService service = factory.create(CategoryService.class);
    private static Map<Integer, Session> sessions = new HashMap<>();
    private static final List<Sender> senders = new ArrayList<>();
    private static Map<Integer, List<Category>> categoriesUser = new HashMap<>();
    private User user;

    public FeedWebSocket() {
        senders.add(new Feed.FeedSender());
        for (Sender sender : senders) {
            sender.accept(this);
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = getHttpSession(config);
        user = (User) httpSession.getAttribute("user");
        sessions.put(user.getId(), session);
        categoriesUser.put(user.getId(), service.getByUserId(user.getId()));
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

    @Override
    public void visit(Sender sender) {
        sender.accept(this);
    }

    @Override
    public boolean send(int categoryId, String message) {
        try {
            for (Integer id : sessions.keySet()) {
                List<Category> categories = categoriesUser.get(id);
                if (compareByCategoryId(categories, categoryId)) {
                    Session session = sessions.get(id);
                    System.out.println(id + " ============================ " + message);

                    session.getBasicRemote().sendText(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean compareByCategoryId(List<Category> categories, int id) {
        for (Category category : categories) {
            if (category.getId() == id)
                return true;
        }
        return false;
    }
}
