package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Node;
import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Poll;
import com.epam.lab.spider.model.vk.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Polls extends Methods {

    public Polls(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает детальную информацию об опросе по его идентификатору.
     */
    public Poll getById(Parameters param) throws VKException {
        Response response = request("polls.getById", param).execute();
        return new Poll(response.root());
    }

    /**
     * Отдает голос текущего пользователя за выбранный вариант ответа в указанном опросе.
     */
    public boolean addVote(Parameters param) throws VKException {
        Response response = request("polls.addVote", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Снимает голос текущего пользователя с выбранного варианта ответа в указанном опросе.
     */
    public boolean deleteVote(Parameters param) throws VKException {
        Response response = request("polls.deleteVote", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Получает список идентификаторов пользователей, которые выбрали определенные варианты ответа в опросе.
     */
    public Map<Integer, List<User>> getVoters(Parameters param) throws VKException {
        Response response = request("polls.getVoters", param).execute();
        Map<Integer, List<User>> vote = new HashMap<>();
        List<Node> nodes = response.root().child("answer");
        for (Node node : nodes)
            vote.put(node.child("answer_id").get(0).value().toInt(),
                    User.parseUser(node.child("users").get(0).child("items").get(0)));
        return vote;
    }

    /**
     * Позволяет создавать опросы, которые впоследствии можно прикреплять к записям на странице пользователя или
     * сообщества.
     */
    public Poll create(Parameters param) throws VKException {
        Response response = request("polls.create", param).execute();
        return new Poll(response.root());
    }

    /**
     * Позволяет редактировать созданные опросы.
     */
    public boolean edit(Parameters param) throws VKException {
        Response response = request("polls.edit", param).execute();
        return response.root().value().toBoolean();
    }

}
