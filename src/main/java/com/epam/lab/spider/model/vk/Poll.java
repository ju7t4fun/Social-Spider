package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Poll extends Model implements Attachment {

    public static final String ID = "id";
    public static final String OWNER_ID = "owner_id";
    public static final String CREATED = "created";
    public static final String QUESTION = "question";
    public static final String VOTES = "votes";
    public static final String ANSWER_ID = "answer_id";

    public Poll() {
        super();
    }

    public Poll(Node root) {
        super(root, Poll.class);
    }

    public static List<Poll> parsePoll(Node root) {
        List<Poll> polls = new ArrayList<>();
        List<Node> nodes = root.child("poll");
        for (Node node : nodes)
            polls.add(new Poll(node));
        return polls;
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    public String getQuestion() {
        return get(QUESTION).toString();
    }

    @Override
    public Type getType() {
        return Type.POLL;
    }

    @Override
    public int getOwnerId() {
        return get(OWNER_ID).toInt();
    }

    public Date getCreated() {
        return get(CREATED).toDate();
    }

    public int getVotes() {
        return get(VOTES).toInt();
    }

    public int getAnswerId() {
        return get(ANSWER_ID).toInt();
    }

    public static class Answers extends Model {

        public static final String ID = "id";
        public static final String TEXT = "text";
        public static final String VOTES = "votes";
        public static final String RATE = "rate";

        public Answers() {
            super();
        }

        public Answers(Node root) {
            super(root, Answers.class);
        }

        public static List<Answers> parseAnswers(Node root) {
            return null;
        }

        public int getId() {
            return get(ID).toInt();
        }

        public String getText() {
            return get(TEXT).toString();
        }

        public int getVotes() {
            return get(VOTES).toInt();
        }

        public String getRate() {
            return get(RATE).toString();
        }

    }

}
