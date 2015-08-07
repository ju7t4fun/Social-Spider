package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Node;
import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Group;
import com.epam.lab.spider.model.vk.Link;
import com.epam.lab.spider.model.vk.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Groups extends Methods {

    public Groups(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает информацию о том, является ли пользователь участником сообщества.
     */
    public boolean isMember(Parameters param) throws VKException {
        if (param.isField("extended"))
            param.remove("extended");
        Response response = request("groups.isMember", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает информацию о том, является ли пользователь участником сообщества.
     */
    public Map<Integer, Boolean> isMemberEx(Parameters param) throws VKException {
        Response response = request("groups.isMember", param).execute();
        Map<Integer, Boolean> args = new HashMap<>();
        List<Node> ids = response.root().child("user_id");
        List<Node> member = response.root().child("member");
        for (int i = 0; i < ids.size(); i++)
            args.put(ids.get(i).value().toInt(), member.get(i).value().toBoolean());
        return args;
    }

    /**
     * Возвращает информацию о заданном сообществе или о нескольких сообществах.
     */
    public List<Group> getById(Parameters param) throws VKException {
        Response response = request("groups.getById", param).execute();
        return Group.parseGroup(response.root());
    }

    /**
     * Возвращает список сообществ указанного пользователя.
     */
    public List<Group> get(Parameters param) throws VKException {
        param.add("extended", 1);
        Response response = request("groups.get", param).execute();
        return Group.parseGroup(response.root().child("items").get(0));
    }

    /**
     * Возвращает список участников сообщества.
     */
    public List<User> getMembers(Parameters param) throws VKException {
        Response response = request("groups.getMembers", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Данный метод позволяет вступить в группу, публичную страницу, а также подтвердить участие во встрече.
     */
    public boolean join(Parameters param) throws VKException {
        Response response = request("groups.join", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Данный метод позволяет выходить из группы, публичной страницы, или встречи.
     */
    public boolean leave(Parameters param) throws VKException {
        Response response = request("groups.leave", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Осуществляет поиск сообществ по заданной подстроке.
     */
    public List<Group> search(Parameters param) throws VKException {
        Response response = request("groups.search", param).execute();
        return Group.parseGroup(response.root().child("items").get(0));
    }

    /**
     * Данный метод возвращает список приглашений в сообщества и встречи текущего пользователя.
     */
    public List<Group> getInvites(Parameters param) throws VKException {
        Response response = request("groups.getInvites", param).execute();
        return Group.parseGroup(response.root().child("items").get(0));
    }

    /**
     * Возвращает список пользователей, которые были приглашены в группу.
     */
    public List<User> getInvitedUsers(Parameters param) throws VKException {
        Response response = request("groups.getInvitedUsers", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Добавляет пользователя в черный список группы.
     */
    public boolean banUser(Parameters param) throws VKException {
        Response response = request("groups.banUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Убирает пользователя из черного списка сообщества.
     */
    public boolean unbanUser(Parameters param) throws VKException {
        Response response = request("groups.unBanUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список забаненных пользователей в сообществе.
     */
    public List<User> getBanned(Parameters param) throws VKException {
        Response response = request("groups.getBanned", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Позволяет создавать новые сообщества.
     */
    public int create(Parameters param) throws VKException {
        Response response = request("groups.create", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Позволяет редактировать информацию групп.
     */
    public boolean edit(Parameters param) throws VKException {
        Response response = request("groups.edit", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет редактировать информацию о месте группы.
     */
    public boolean editPlace(Parameters param) throws VKException {
        Response response = request("groups.editPlace", param).execute();
        return response.root().child("success").get(0).value().toBoolean();
    }

    /**
     * Позволяет получать данные, необходимые для отображения страницы редактирования данных сообщества.
     */
    public void getSettings(Parameters param) throws VKException {
        Response response = request("groups.getSettings", param).execute();
    }

    /**
     * Возвращает список заявок на вступление в сообщество.
     */
    public List<User> getRequests(Parameters param) throws VKException {
        Response response = request("groups.getRequests", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Позволяет назначить/разжаловать руководителя в сообществе или изменить уровень его полномочий.
     */
    public boolean editManager(Parameters param) throws VKException {
        Response response = request("groups.editManager", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет приглашать друзей в группу.
     */
    public boolean invite(Parameters param) throws VKException {
        Response response = request("groups.invite", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет добавлять ссылки в сообщество.
     */
    public Link addLink(Parameters param) throws VKException {
        Response response = request("groups.addLink", param).execute();
        return new Link(response.root());
    }

    /**
     * Позволяет удалить ссылки из сообщества.
     */
    public boolean deleteLink(Parameters param) throws VKException {
        Response response = request("groups.deleteLink", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет редактировать ссылки в сообществе.
     */
    public boolean editLink(Parameters param) throws VKException {
        Response response = request("groups.editLink", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет менять местоположение ссылки в списке.
     */
    public boolean reorderLink(Parameters param) throws VKException {
        Response response = request("groups.reorderLink", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет исключить пользователя из группы.
     */
    public boolean removeUser(Parameters param) throws VKException {
        Response response = request("groups.removeUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет одобрить заявку в группу от пользователя.
     */
    public boolean approveRequest(Parameters param) throws VKException {
        Response response = request("groups.approveRequest", param).execute();
        return response.root().value().toBoolean();
    }

}
