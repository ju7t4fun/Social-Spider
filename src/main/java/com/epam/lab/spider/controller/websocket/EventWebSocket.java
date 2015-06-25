package com.epam.lab.spider.controller.websocket;

import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.controller.utils.Receiver;
import com.epam.lab.spider.controller.utils.Sender;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 19.06.2015.
 */
@ServerEndpoint("/event/{clientId}")
public class EventWebSocket implements Receiver {

    private static final Logger LOG = Logger.getLogger(EventWebSocket.class);

    private final Map<Integer, Session> sessions = new HashMap<>();
    private List<Sender> senders = new ArrayList<Sender>() {
        {
            add(new EventLogger.EventSender());
        }
    };

    public EventWebSocket() {
        super();
        for (Sender sender : senders) {
            sender.accept(this);
        }
    }

    @OnOpen
    public void onOpen(@PathParam("clientId") String clientId, Session session) {
        sessions.put(Integer.parseInt(clientId), session);
        LOG.debug("onOpen (clientId=" + clientId + ")");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        LOG.debug("onMessage (message=" + message + ")");
    }

    @OnClose
    public void onClose(@PathParam("clientId") String clientId, Session session) {
        sessions.remove(Integer.parseInt(clientId));
        LOG.debug("onClose (clientId=" + clientId + ")");
    }

    @OnError
    public void onError(@PathParam("clientId") String clientId, Session session, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void visit(Sender sender) {
        sender.accept(this);
    }

    @Override
    public void send(int id, String message) {
        try {
            if (sessions.containsKey(id))
                sessions.get(id).getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
