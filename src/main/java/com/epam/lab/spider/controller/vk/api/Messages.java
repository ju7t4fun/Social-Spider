package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Chat;
import com.epam.lab.spider.model.vk.Message;
import com.epam.lab.spider.model.vk.User;

import java.util.Date;
import java.util.List;

public class Messages extends Methods {

    public Messages(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список входящих либо исходящих личных сообщений текущего пользователя.
     */
    public List<Message> get(Parameters param) throws VKException {
        Response response = request("messages.get", param).execute();
        return Message.parseMessage(response.root().child("items").get(0));
    }

    /**
     * Возвращает список диалогов текущего пользователя.
     */
    public List<Message> getDialogs(Parameters param) throws VKException {
        Response response = request("messages.getDialogs", param).execute();
        return Message.parseMessage(response.root().child("items").get(0));
    }

    /**
     * Возвращает сообщения по их id.
     */
    public List<Message> getById(Parameters param) throws VKException {
        Response response = request("messages.getById", param).execute();
        return Message.parseMessage(response.root().child("items").get(0));
    }

    /**
     * Возвращает список найденных личных сообщений текущего пользователя по введенной строке поиска.
     */
    public List<Message> search(Parameters param) throws VKException {
        Response response = request("messages.search", param).execute();
        return Message.parseMessage(response.root().child("items").get(0));
    }

    /**
     * Возвращает историю сообщений для указанного пользователя.
     */
    public List<Message> getHistory(Parameters param) throws VKException {
        Response response = request("messages.getHistory", param).execute();
        return Message.parseMessage(response.root().child("items").get(0));
    }

    /**
     * Отправляет сообщение.
     */
    public int send(Parameters param) throws VKException {
        Response response = request("messages.send", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Удаляет сообщение.
     */
    public boolean delete(Parameters param) throws VKException {
        Response response = request("messages.delete", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет все личные сообщения в диалоге.
     */
    public boolean deleteDialog(Parameters param) throws VKException {
        Response response = request("messages.deleteDialog", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает удаленное сообщение.
     */
    public boolean restore(Parameters param) throws VKException {
        Response response = request("messages.restore", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Помечает сообщения как прочитанные.
     */
    public boolean markAsRead(Parameters param) throws VKException {
        Response response = request("messages.markAsRead", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Помечает сообщения как важные либо снимает отметку.
     */
    public List<Message> markAsImportant(Parameters param) throws VKException {
        Response response = request("messages.markAsImportant", param).execute();
        return Message.parseMessage(response.root());
    }

    /**
     * Помечает сообщения как важные либо снимает отметку.
     */
    public Chat getChat(Parameters param) throws VKException {
        Response response = request("messages.getChat", param).execute();
        return new Chat(response.root());
    }

    /**
     * Создаёт беседу с несколькими участниками.
     */
    public int createChat(Parameters param) throws VKException {
        Response response = request("messages.createChat", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Изменяет название беседы.
     */
    public boolean editChat(Parameters param) throws VKException {
        Response response = request("messages.editChat", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет получить список пользователей мультидиалога по его id.
     */
    public List<User> getChatUsers(Parameters param) throws VKException {
        Response response = request("messages.getChatUsers", param).execute();
        return User.parseUser(response.root());
    }

    /**
     * Изменяет статус набора текста пользователем в диалоге.
     */
    public boolean setActivity(Parameters param) throws VKException {
        Response response = request("messages.setActivity", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список найденных диалогов текущего пользователя по введенной строке поиска.
     */
    public List<User> searchDialogs(Parameters param) throws VKException {
        Response response = request("messages.searchDialogs", param).execute();
        return User.parseUser(response.root());
    }

    /**
     * Добавляет в мультидиалог нового пользователя.
     */
    public boolean addChatUser(Parameters param) throws VKException {
        Response response = request("messages.addChatUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Исключает из мультидиалога пользователя, если текущий пользователь был создателем беседы либо пригласил
     * исключаемого пользователя.
     */
    public boolean removeChatUser(Parameters param) throws VKException {
        Response response = request("messages.removeChatUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает дату последней активности указанного пользователя.
     */
    public Date getLastActivity(Parameters param) throws VKException {
        Response response = request("messages.getLastActivity", param).execute();
        return response.root().child("time").get(0).value().toDate();
    }

}
