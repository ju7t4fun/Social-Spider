package testchat;

/**
 * Created by Орест on 18.06.2015.
 */

import java.util.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat")
public class MyWebSocketEndPoint {

    private static final String USERNAME_KEY = "username";
    private static Map<String, Session> chatRooms = Collections.synchronizedMap(new LinkedHashMap<String, Session>());

    @OnOpen
    public void onOpen(Session session) throws Exception {

        //Get the new socket's username from url
        //e.g. url: ws://localhost:8080/chat?username=Andi, so Andi is the username
        Map<String, List<String>> parameter = session.getRequestParameterMap();
        List<String> list = parameter.get(USERNAME_KEY);
        String newUsername = list.get(0);

        //Add the new socket to the collection
        chatRooms.put(newUsername, session);

        //also set username property of the session.
        //so when there a new message from a particular socket's session obj
        //we can get the username whom send the message
        session.getUserProperties().put(USERNAME_KEY, newUsername);

        //Give a list current online users to the new socket connection
        //because we store username as the key of the map, we can get all
        //  username list from the map's keySet
        String response = "newUser|" + String.join("|", chatRooms.keySet());
        session.getBasicRemote().sendText(response);

        //Loop through all socket's session obj, then send a text message
        for (Session client : chatRooms.values()) {
            if(client == session) continue;
            client.getBasicRemote().sendText("newUser|" + newUsername);
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws Exception {
        //Extract the information of incoming message.
        //Message format: 'From|Message'
        //so we split the incoming message with '|' token
        //to get the destination and message content data
        String[] data = message.split("\\|");
        String destination = data[0];
        String messageContent = data[1];

        //Retrieve the sender's username from it's property
        String sender = (String)session.getUserProperties().get(USERNAME_KEY);

        //Deliver the message according to the destination
        //Outgoing Message format: "message|sender|content|messageType?"
        //the message type is optional, if the message is intended to be broadcast
        //  then the message type value is "|all"
        if(destination.equals("all")) {
            //if the destination chat is 'all', then we broadcast the message
            for (Session client : chatRooms.values()) {
                if(client.equals(session)) continue;
                client.getBasicRemote().sendText("message|" + sender + "|" + messageContent + "|all" );
            }
        } else {
            //find the username to be sent, then deliver the message
            Session client = chatRooms.get(destination);
            String response = "message|" + sender + "|" + messageContent;
            client.getBasicRemote().sendText(response);
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        //remove client from collecton
        String username = (String)session.getUserProperties().get(USERNAME_KEY);
        chatRooms.remove(username);

        //broadcast to all people, that the current user is leaving the chat room
        for (Session client : chatRooms.values()) {
            client.getBasicRemote().sendText("removeUser|" + username);
        }
    }
    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }
}