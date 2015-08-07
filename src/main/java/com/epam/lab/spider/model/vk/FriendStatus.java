package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.List;

public class FriendStatus extends Model {

    public static final String USER_ID = "user_id";
    public static final String FRIEND_STATUS = "friend_status";
    public static final String REQUEST_MESSAGE = "request_message";
    public static final String READ_STATE = "read_state";
    public static final String SIGN = "sign";

    public FriendStatus() {
        super();
    }

    public FriendStatus(Node root) {
        super(root, FriendStatus.class);
    }

    public static List<FriendStatus> parseFriendStatus(Node root) {
        List<FriendStatus> list = new ArrayList<>();
        List<Node> nodes = root.child("status");
        for (Node node : nodes)
            list.add(new FriendStatus(node));
        return list;
    }

    public int getUserId() {
        return get(USER_ID).toInt();
    }

    public String getFriendStatus() {
        switch (get(FRIEND_STATUS).toByte()) {
            case 0:
                return "пользователь не является другом";
            case 1:
                return "отправлена заявка/подписка пользователю";
            case 2:
                return "имеется входящая заявка/подписка от пользователя";
            case 3:
                return "пользователь является другом";
            default:
                return null;
        }
    }

    public String getRequestMessage() {
        return get(REQUEST_MESSAGE).toString();
    }

    public boolean getReadState() {
        return get(READ_STATE).toBoolean();
    }

    public String getSign() {
        return get(SIGN).toString();
    }

}
