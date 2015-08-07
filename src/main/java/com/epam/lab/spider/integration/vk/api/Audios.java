package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Audio;
import com.epam.lab.spider.model.vk.User;

import java.util.List;

public class Audios extends Methods {

    public Audios(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список аудиозаписей пользователя или сообщества.
     */
    public List<Audio> get(Parameters param) throws VKException {
        Response response = request("audio.get", param).execute();
        return Audio.parseAudio(response.root().child("items").get(0));
    }

    /**
     * Возвращает информацию об аудиозаписях.
     */
    public List<Audio> getById(Parameters param) throws VKException {
        Response response = request("audio.getById", param).execute();
        return Audio.parseAudio(response.root());
    }

    /**
     * Возвращает текст аудиозаписи.
     */
    public Audio.Lyrics getLyrics(Parameters param) throws VKException {
        final Response response = request("audio.getLyrics", param).execute();
        return new Audio.Lyrics() {
            @Override
            public int getId() {
                return Integer.parseInt(response.root().parse("lyrics.lyrics_id"));
            }

            @Override
            public String getText() {
                return response.root().parse("lyrics.text");
            }
        };
    }

    /**
     * Возвращает список аудиозаписей в соответствии с заданным критерием поиска.
     */
    public List<Audio> search(Parameters param) throws VKException {
        Response response = request("audio.search", param).execute();
        return Audio.parseAudio(response.root().child("items").get(0));
    }

    /**
     * Возвращает адрес сервера для загрузки аудиозаписей.
     */
    public String getUploadServer() throws VKException {
        Response response = request("audio.getUploadServer", new Parameters()).execute();
        return response.root().value().toString();
    }

    /**
     * Сохраняет аудиозаписи после успешной загрузки.
     */
    public List<Audio> save(Parameters param) throws VKException {
        Response response = request("audio.save", param).execute();
        return Audio.parseAudio(response.root());
    }

    /**
     * Копирует аудиозапись на страницу пользователя или группы.
     */
    public int add(Parameters param) throws VKException {
        Response response = request("audio.add", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Удаляет аудиозапись со страницы пользователя или сообщества.
     */
    public boolean delete(Parameters param) throws VKException {
        Response response = request("audio.delete", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Редактирует данные аудиозаписи на странице пользователя или сообщества.
     */
    public int edit(Parameters param) throws VKException {
        Response response = request("audio.edit", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Изменяет порядок аудиозаписи, перенося ее между аудиозаписями, идентификаторы которых переданы параметрами
     * after и before.
     */
    public boolean reorder(Parameters param) throws VKException {
        Response response = request("audio.reorder", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает аудиозапись после удаления.
     */
    public Audio restore(Parameters param) throws VKException {
        Response response = request("audio.restore", param).execute();
        return new Audio(response.root().child("audio").get(0));
    }

    /**
     * Возвращает список альбомов аудиозаписей пользователя или группы.
     */
    public List<Audio.Album> getAlbums(Parameters param) throws VKException {
        Response response = request("audio.getAlbums", param).execute();
        return Audio.Album.parseAlbum(response.root().child("items").get(0));
    }

    /**
     * Создает пустой альбом аудиозаписей.
     */
    public int addAlbum(Parameters param) throws VKException {
        Response response = request("audio.addAlbum", param).execute();
        return response.root().child("album_id").get(0).value().toInt();
    }

    /**
     * Создает пустой альбом аудиозаписей.
     */
    public boolean editAlbum(Parameters param) throws VKException {
        Response response = request("audio.editAlbum", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет альбом аудиозаписей.
     */
    public boolean deleteAlbum(Parameters param) throws VKException {
        Response response = request("audio.deleteAlbum", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Перемещает аудиозаписи в альбом.
     */
    public boolean moveToAlbum(Parameters param) throws VKException {
        Response response = request("audio.moveToAlbum", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Транслирует аудиозапись в статус пользователю или сообществу.
     */
    public int setBroadcast(Parameters param) throws VKException {
        Response response = request("audio.setBroadcast", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Возвращает список друзей и сообществ пользователя, которые транслируют музыку в статус.
     */
    public List<User> getBroadcastList(Parameters param) throws VKException {
        Response response = request("audio.getBroadcastList", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Возвращает список рекомендуемых аудиозаписей на основе списка воспроизведения заданного пользователя или
     * на основе одной выбранной аудиозаписи.
     */
    public List<Audio> getRecommendations(Parameters param) throws VKException {
        Response response = request("audio.getRecommendations", param).execute();
        return Audio.parseAudio(response.root().child("items").get(0));
    }

    /**
     * Возвращает список аудиозаписей из раздела "Популярное".
     */
    public List<Audio> getPopular(Parameters param) throws VKException {
        Response response = request("audio.getPopular", param).execute();
        return Audio.parseAudio(response.root());
    }

    /**
     * Возвращает количество аудиозаписей пользователя или сообщества.
     */
    public int getCount(Parameters param) throws VKException {
        Response response = request("audio.getCount", param).execute();
        return response.root().value().toInt();
    }

}
