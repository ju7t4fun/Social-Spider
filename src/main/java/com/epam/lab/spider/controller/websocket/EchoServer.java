package com.epam.lab.spider.controller.websocket;

/**
 * Created by Орест on 18.06.2015.
 */

import com.epam.lab.spider.model.db.entity.User;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo
 * Where "localhost" is the address of the host,
 * "EchoChamber" is the name of the package
 * and "echo" is the address to access this class from the server
 */
@ServerEndpoint(value = "/echo", configurator = GetHttpSessionConfigurator.class)
public class EchoServer {

    public static Set<Session> sessions = new HashSet<>();
    public static Set<HttpSession> httpSessions = new HashSet<>();

    private HttpSession thisHttpSession = null;

   /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was
     * successful.
     */

    @OnOpen
    public void onOpen(final Session session, final EndpointConfig config) {
        try {
            printAllOtherUser(session,config);
            printUserToAll(session, config);
            sessions.add(session);
            thisHttpSession = getHttpSession(config);
            httpSessions.add(thisHttpSession);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        session.addMessageHandler(new MessageHandler.Whole<String>(){
            /**
             * When a user sends a message to the server, this method will intercept the message
             * and allow us to react to it. For now the message is read as a String.
             */
            @OnMessage
            public void onMessage(String message) {
                try {
                    String[] parts = message.split("|");
                    if (parts[0].equals("remove")) {
                        System.out.println("doiwlo do remove");
                    } else {
                        for (Session s : sessions) {
                            s.getBasicRemote().sendText(message);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

    }

    /**
     * The user closes the connection.
     * <p>
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");
        sessions.remove(session);
        httpSessions.remove(thisHttpSession);
        try {
            for (Session s : sessions) {

                s.getBasicRemote().sendText("remove|"+((User)thisHttpSession.getAttribute("user")).getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printAllOtherUser(Session session, EndpointConfig config) {
       try {
           for (HttpSession s : httpSessions) {
               session.getBasicRemote().sendText("user|" + ((User) s.getAttribute("user")).getName());
           }
       } catch (Exception ex) {
       }
    }

    private void printUserToAll(Session session, EndpointConfig config) {
        try {
            for (Session s : sessions) {
                s.getBasicRemote().sendText(((User) getHttpSession(config).getAttribute("user")).
                        getName() + ": has entered");
                s.getBasicRemote().sendText("user|" + ((User) getHttpSession(config).getAttribute("user")).getName());
//                for (HttpSession ses : httpSessions) {
//                    if (ses != null && !ses.equals(getHttpSession(config))) {
//                        s.getBasicRemote().sendText(((User) ses.getAttribute("user")).
//                                getName() + ": has entered");
//                        s.getBasicRemote().sendText("user|" + ((User) ses.getAttribute("user")).getName());
//                    }
//                }
            }
        } catch (Exception ex) {
        }
    }

    @OnError
    public void error(Session session, Throwable t) {
        System.out.println("lol error was");
    }

    private HttpSession getHttpSession(EndpointConfig config) {
        HttpSession curSession = (HttpSession)config.getUserProperties()
                .get(HttpSession.class.getName());
        return curSession;

    }
}