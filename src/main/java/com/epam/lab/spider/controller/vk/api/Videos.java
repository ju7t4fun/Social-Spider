package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Comment;
import com.epam.lab.spider.model.vk.Tag;
import com.epam.lab.spider.model.vk.Video;
import com.epam.lab.spider.model.vk.VideoAlbum;

import java.net.URL;
import java.util.List;

public class Videos extends Methods {

    public Videos(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает информацию о видеозаписях.
     */
    public List<Video> get(Parameters param) throws VKException {
        Response response = request("video.get", param).execute();
        return Video.parseVideo(response.root().child("items").get(0));
    }

    /**
     * Редактирует данные видеозаписи на странице пользователя.
     */
    public boolean edit(Parameters param) throws VKException {
        Response response = request("video.edit", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Добавляет видеозапись в список пользователя.
     */
    public boolean add(Parameters param) throws VKException {
        Response response = request("video.add", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает адрес сервера (необходимый для загрузки) и данные видеозаписи.
     */
    public URL save(Parameters param) throws VKException {
        final Response response = request("video.safeSave", param).execute();
        return response.root().child("upload_url").get(0).value().toURL();
    }

    /**
     * Удаляет видеозапись со страницы пользователя.
     */
    public boolean delete(Parameters param) throws VKException {
        Response response = request("video.delete", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает удаленную видеозапись.
     */
    public boolean restore(Parameters param) throws VKException {
        Response response = request("video.restore", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список видеозаписей в соответствии с заданным критерием поиска.
     */
    public List<Video> search(Parameters param) throws VKException {
        Response response = request("video.search", param).execute();
        return Video.parseVideo(response.root().child("items").get(0));
    }

    /**
     * Возвращает список видеозаписей, на которых отмечен пользователь.
     */
    public List<Video> getUserVideos(Parameters param) throws VKException {
        Response response = request("video.getUserVideos", param).execute();
        return Video.parseVideo(response.root().child("items").get(0));
    }

    /**
     * Возвращает список альбомов видеозаписей пользователя или сообщества.
     */
    public List<VideoAlbum> getAlbums(Parameters param) throws VKException {
        Response response = request("video.getAlbums", param).execute();
        return VideoAlbum.parseVideoAlbum(response.root().child("items").get(0));
    }

    /**
     * Позволяет получить информацию об альбоме с видео.
     */
    public VideoAlbum getAlbumById(Parameters param) throws VKException {
        Response response = request("video.getAlbumById", param).execute();
        return new VideoAlbum(response.root());
    }

    /**
     * Создает пустой альбом видеозаписей.
     */
    public long addAlbum(Parameters param) throws VKException {
        Response response = request("video.addAlbum", param).execute();
        return response.root().child("album_id").get(0).value().toLong();
    }

    /**
     * Редактирует название альбома видеозаписей.
     */
    public boolean editAlbum(Parameters param) throws VKException {
        Response response = request("video.editAlbum", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет альбом видеозаписей.
     */
    public boolean deleteAlbum(Parameters param) throws VKException {
        Response response = request("video.deleteAlbum", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет изменить порядок альбомов с видео.
     */
    public boolean reorderAlbums(Parameters param) throws VKException {
        Response response = request("video.reorderAlbums", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет добавить видеозапись в альбом.
     */
    public boolean addToAlbum(Parameters param) throws VKException {
        Response response = request("video.addToAlbum", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет убрать видеозапись из альбома.
     */
    public boolean removeFromAlbum(Parameters param) throws VKException {
        Response response = request("video.removeFromAlbum", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список альбомов, в которых находится видеозапись.
     */
    public List<VideoAlbum> getAlbumsByVideo(Parameters param) throws VKException {
        param.add("extended", 1);
        Response response = request("video.getAlbumsByVideo", param).execute();
        return VideoAlbum.parseVideoAlbum(response.root().child("items").get(0));
    }

    /**
     * Возвращает список комментариев к видеозаписи.
     */
    public List<Comment> getComments(Parameters param) throws VKException {
        Response response = request("video.getComments", param).execute();
        return Comment.parseComment(response.root());
    }

    /**
     * Cоздает новый комментарий к видеозаписи
     */
    public long createComment(Parameters param) throws VKException {
        Response response = request("video.createComment", param).execute();
        return response.root().value().toLong();
    }

    /**
     * Удаляет комментарий к видеозаписи.
     */
    public boolean deleteComment(Parameters param) throws VKException {
        Response response = request("video.deleteComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает удаленный комментарий к видеозаписи.
     */
    public boolean restoreComment(Parameters param) throws VKException {
        Response response = request("video.restoreComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Изменяет текст комментария к видеозаписи.
     */
    public boolean editComment(Parameters param) throws VKException {
        Response response = request("video.editComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список отметок на видеозаписи.
     */
    public List<Tag> getTags(Parameters param) throws VKException {
        Response response = request("video.getTags", param).execute();
        return Tag.parseTag(response.root());
    }

    /**
     * Добавляет отметку на видеозапись.
     */
    public int putTag(Parameters param) throws VKException {
        Response response = request("video.putTag", param).execute();
        return response.root().child("tag id").get(0).value().toInt();
    }

    /**
     * Удаляет отметку с видеозаписи.
     */
    public boolean removeTag(Parameters param) throws VKException {
        Response response = request("video.removeTag", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список видеозаписей, на которых есть непросмотренные отметки.
     */
    public List<Video> getNewTags(Parameters param) throws VKException {
        Response response = request("video.getNewTags", param).execute();
        return Video.parseVideo(response.root().child("items").get(0));
    }

    /**
     * Позволяет пожаловаться на видеозапись.
     */
    public boolean report(Parameters param) throws VKException {
        Response response = request("video.report", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет пожаловаться на комментарий к видеозаписи.
     */
    public boolean reportComment(Parameters param) throws VKException {
        Response response = request("video.reportComment", param).execute();
        return response.root().value().toBoolean();
    }

}
