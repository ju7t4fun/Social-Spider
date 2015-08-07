package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.User;

import java.util.List;

public class Account extends Methods {

    public Account(AccessToken token) {
        super(token);
    }

    /**
     * Устанавливает короткое название приложения (до 17 символов), которое выводится пользователю в левом меню.
     */
    public boolean setNameInMenu(Parameters param) throws VKException {
        Response response = request("account.setNameInMenu", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Помечает текущего пользователя как online на 15 минут.
     */
    public boolean setOnline(Parameters param) throws VKException {
        Response response = request("account.setOnline", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Помечает текущего пользователя как offline.
     */
    public boolean setOffline() throws VKException {
        Response response = request("account.setOffline", new Parameters()).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Подписывает устройство на базе iOS, Android или Windows Phone на получение Push-уведомлений.
     */
    public boolean registerDevice(Parameters param) throws VKException {
        Response response = request("account.registerDevice", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Отписывает устройство от Push уведомлений.
     */
    public boolean unregisterDevice() throws VKException {
        Response response = request("account.unregisterDevice", new Parameters()).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Отключает push-уведомления на заданный промежуток времени.
     */
    public boolean setSilenceMode(Parameters param) throws VKException {
        Response response = request("account.setSilenceMode", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Получает настройки текущего пользователя в данном приложении.
     */
    public long getAppPermissions(Parameters param) throws VKException {
        Response response = request("account.getAppPermissions", param).execute();
        return response.root().value().toLong();
    }

    /**
     * Добавляет пользователя в черный список.
     */
    public boolean banUser(Parameters param) throws VKException {
        Response response = request("account.banUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Убирает пользователя из черного списка.
     */
    public boolean unBanUser(Parameters param) throws VKException {
        Response response = request("account.unBanUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список пользователей, находящихся в черном списке.
     */
    public List<User> getBanned(Parameters param) throws VKException {
        Response response = request("account.getBanned", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Возвращает информацию о текущем аккаунте.
     */
    public Info getInfo() throws VKException {
        final Response response = request("account.getInfo", new Parameters()).execute();
        return new Info() {
            @Override
            public String getCountry() {
                return response.root().child("country").get(0).value().toString();
            }

            @Override
            public boolean getHttpsRequired() {
                return response.root().child("country").get(0).value().toBoolean();
            }

            @Override
            public long getIntro() {
                return response.root().child("country").get(0).value().toLong();
            }

            @Override
            public int getLang() {
                return response.root().child("country").get(0).value().toInt();
            }
        };
    }

    /**
     * Позволяет редактировать информацию о текущем аккаунте.
     */
    public boolean setInfo(Parameters param) throws VKException {
        Response response = request("account.setInfo", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет сменить пароль пользователя после успешного восстановления доступа к аккаунту через СМС,
     * используя метод auth.restore.
     */
    public AccessToken changePassword(Parameters param) throws VKException {
        Response response = request("account.changePassword", param).execute();
        AccessToken token = new AccessToken();
        token.setAccessToken(response.root().child("token").get(0).value().toString());
        token.setExpirationMoment(86400);
        return token;
    }

    /**
     * Возвращает информацию о текущем профиле.
     */
    public User getProfileInfo() throws VKException {
        Response response = request("account.getProfileInfo", new Parameters()).execute();
        return new User(response.root());
    }

    /**
     * Редактирует информацию текущего профиля.
     */
    public boolean saveProfileInfo(Parameters param) throws VKException {
        Response response = request("account.saveProfileInfo", param).execute();
        return response.root().child("changed").get(0).value().toBoolean();
    }

    public interface Info {
        String getCountry();
        boolean getHttpsRequired();
        long getIntro();
        int getLang();
    }

}