package com.epam.lab.spider.controller.websocket;

import com.epam.lab.spider.controller.utils.Receiver;
import com.epam.lab.spider.controller.utils.Sender;
import com.epam.lab.spider.job.util.Feed;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.CategoryService;
import com.epam.lab.spider.persistence.service.PostService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * @author Boyarsky Vitaliy
 */
@ServerEndpoint(value = "/websocket/feed", configurator = GetHttpSessionConfigurator.class)
public class FeedWebSocket implements Receiver {

    private static final Logger LOG = Logger.getLogger(FeedWebSocket.class);
    private static final List<Sender> senders = new ArrayList<>();
    private static final ServiceFactory factory = ServiceFactory.getInstance();

    private static CategoryService service = factory.create(CategoryService.class);
    private static Map<Integer, Session> sessions = new HashMap<>();
    private static Map<Integer, List<Category>> categoriesUser = new HashMap<>();
    private static CategoryService categoryService = factory.create(CategoryService.class);
    PostService postService = factory.create(PostService.class);
    private HttpSession httpSession;
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
        this.httpSession = httpSession;
        user = (User) httpSession.getAttribute("user");
        sessions.put(user.getId(), session);
        categoriesUser.put(user.getId(), service.getByUserId(user.getId()));
        history(session, user.getId());
        if (LOG.isDebugEnabled())
            LOG.debug("onOpen (clientId=" + user.getId() + ")");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String[] args = message.split("\\|");
        if (LOG.isDebugEnabled())
            LOG.debug("onMessage. Execute command " + Command.valueOf(args[0].toUpperCase()) + " params " + Arrays
                    .toString(args));
        switch (Command.valueOf(args[0].toUpperCase())) {
            case ON_SCROLL: {
                onScroll(session, args);
                break;
            }
            case HISTORY:
                history(session, Integer.parseInt(args[1]));
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessions.remove(user.getId());
        categoriesUser.remove(user.getId());
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
    public boolean send(int postId, String message) {
        List<Category> postCategories = categoryService.getByPostId(postId);
        try {
            for (Integer userId : sessions.keySet()) {
                List<Category> userCategories = categoriesUser.get(userId);
                if (compareByCategoryId(userCategories, postCategories)) {
                    Session session = sessions.get(userId);
                    session.getBasicRemote().sendText(message + "|" + category(postId));
                }
            }
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    private boolean compareByCategoryId(List<Category> categoriesAll, List<Category> categoriesUser) {
        for (Category userCategory : categoriesUser)
            if (categoriesAll.contains(userCategory))
                return true;
        return false;
    }

    private void onScroll(Session session, String[] args) {
        int userId = Integer.parseInt(args[1]);
        int offset = Integer.parseInt(args[2]);
        int limit = Integer.parseInt(args[3]);
        List<Integer> postIds = postService.getByCategoryFromUser(userId, offset, limit);
        try {
            for (Integer id : postIds) {
                session.getBasicRemote().sendText("on_scroll|" + id + "|" + category(id));
            }
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    private void history(Session session, int userId) {
        List<Integer> postIds = postService.getByCategoryFromUser(userId, 0, 5);
        try {
            for (Integer id : postIds) {
                session.getBasicRemote().sendText("history|" + id + "|" + category(id));
            }
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    private String category(int postId) {
        List<Category> categories = service.getByPostId(postId);
        ResourceBundle bundle = (ResourceBundle) httpSession.getAttribute("bundle");
        int lang = Integer.parseInt(bundle.getString("categoryLangCode"));
        List<String> strings = new ArrayList<>();
        for (Category category : categories) {
            strings.add(category.getName().split("\\|")[lang]);
        }
        return strings.toString();
    }

    private enum Command {
        ON_SCROLL, HISTORY
    }

}
