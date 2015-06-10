package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Comment;
import com.epam.lab.spider.model.vk.Photo;
import com.epam.lab.spider.model.vk.Tag;

import java.net.URL;
import java.util.List;

public class Photos extends Methods {

    public Photos(AccessToken token) {
        super(token);
    }

    /**
     * Создает пустой альбом для фотографий.
     */
    public Photo.Album createAlbum(Parameters param) throws VKException {
        Response response = request("photos.createAlbum", param).execute();
        return new Photo.Album(response.root().child("album").get(0));
    }

    /**
     * Редактирует данные альбома для фотографий пользователя.
     */
    public boolean editAlbum(Parameters param) throws VKException {
        Response response = request("photos.editAlbum", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список альбомов пользователя или сообщества.
     */
    public List<Photo.Album> getAlbums(Parameters param) throws VKException {
        Response response = request("photos.getAlbums", param).execute();
        return Photo.Album.parseAlbum(response.root().child("items").get(0));
    }

    /**
     * Возвращает список фотографий в альбоме.
     */
    public List<Photo> get(Parameters param) throws VKException {
        Response response = request("photos.get", param).execute();
        return Photo.parsePhoto(response.root().child("items").get(0));
    }

    /**
     * Возвращает количество доступных альбомов пользователя или сообщества.
     */
    public int getAlbumsCount(Parameters param) throws VKException {
        Response response = request("photos.getAlbumsCount", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Возвращает список фотографий со страницы пользователя или сообщества.
     */
    public List<Photo> getProfile(Parameters param) throws VKException {
        Response response = request("photos.getProfile", param).execute();
        return Photo.parsePhoto(response.root().child("items").get(0));
    }

    /**
     * Возвращает информацию о фотографиях по их идентификаторам.
     */
    public List<Photo> getById(Parameters param) throws VKException {
        Response response = request("photos.getById", param).execute();
        return Photo.parsePhoto(response.root());
    }

    /**
     * Возвращает адрес сервера для загрузки фотографий.
     */
    public String getUploadServer(Parameters param) throws VKException {
        Response response = request("photos.getUploadServer", param).execute();
        return response.root().child("upload_url").get(0).value().toString();
    }

    /**
     * Возвращает адрес сервера для загрузки главной фотографии на страницу пользователя или сообщества.
     */
    public String getOwnerPhotoUploadServer(Parameters param) throws VKException {
        Response response = request("photos.getOwnerPhotoUploadServer", param).execute();
        return response.root().child("upload_url").get(0).value().toString();
    }

    /**
     * Возвращает адрес сервера для загрузки главной фотографии на страницу пользователя или сообщества.
     */
    public URL getChatUploadServer(Parameters param) throws VKException {
        Response response = request("photos.getOwnerPhotoUploadServer", param).execute();
        return response.root().child("upload_url").get(0).value().toURL();
    }

    /**
     * Позволяет сохранить главную фотографию пользователя или сообщества.
     */
    public URL saveOwnerPhoto(Parameters param) throws VKException {
        Response response = request("photos.saveOwnerPhoto", param).execute();
        return response.root().child("photo_src").get(0).value().toURL();
    }

    /**
     * Сохраняет фотографии после успешной загрузки на URI, полученный методом photos.getWallUploadServer.
     */
    public List<Photo> saveWallPhoto(Parameters param) throws VKException {
        Response response = request("photos.saveWallPhoto", param).execute();
        return Photo.parsePhoto(response.root());
    }

    /**
     * Возвращает адрес сервера для загрузки фотографии на стену пользователя или сообщества.
     */
    public URL getWallUploadServer(Parameters param) throws VKException {
        Response response = request("photos.getWallUploadServer", param).execute();
        return response.root().child("upload_url").get(0).value().toURL();
    }

    /**
     * Возвращает адрес сервера для загрузки фотографии в личное сообщение пользователю.
     */
    public URL getMessagesUploadServer(Parameters param) throws VKException {
        Response response = request("photos.getMessagesUploadServer", param).execute();
        return response.root().child("upload_url").get(0).value().toURL();
    }

    /**
     * Сохраняет фотографию после успешной загрузки на URI, полученный методом photos.getMessagesUploadServer.
     */
    public void saveMessagesPhoto(Parameters param) throws VKException {
        Response response = request("photos.saveMessagesPhoto", param).execute();
    }

    /**
     * Позволяет пожаловаться на фотографию.
     */
    public boolean report(Parameters param) throws VKException {
        Response response = request("photos.report", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет пожаловаться на комментарий к фотографии.
     */
    public boolean reportComment(Parameters param) throws VKException {
        Response response = request("photos.reportComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Осуществляет поиск изображений по местоположению или описанию.
     */
    public List<Photo> search(Parameters param) throws VKException {
        Response response = request("photos.search", param).execute();
        return Photo.parsePhoto(response.root().child("items").get(0));
    }

    /**
     * Сохраняет фотографии после успешной загрузки.
     */
    public List<Photo> save(Parameters param) throws VKException {
        Response response = request("photos.save", param).execute();
        return Photo.parsePhoto(response.root());
    }

    /**
     * Позволяет скопировать фотографию в альбом "Сохраненные фотографии"
     */
    public int copy(Parameters param) throws VKException {
        Response response = request("photos.copy", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Изменяет описание у выбранной фотографии.
     */
    public boolean edit(Parameters param) throws VKException {
        Response response = request("photos.edit", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Переносит фотографию из одного альбома в другой.
     */
    public boolean move(Parameters param) throws VKException {
        Response response = request("photos.move", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Делает фотографию обложкой альбома.
     */
    public boolean makeCover(Parameters param) throws VKException {
        Response response = request("photos.makeCover", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Меняет порядок альбома в списке альбомов пользователя.
     */
    public boolean reorderAlbums(Parameters param) throws VKException {
        Response response = request("photos.reorderAlbums", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Меняет порядок фотографии в списке фотографий альбома пользователя.
     */
    public boolean reorderPhotos(Parameters param) throws VKException {
        Response response = request("photos.reorderPhotos", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает все фотографии пользователя или сообщества в антихронологическом порядке.
     */
    public List<Photo> getAll(Parameters param) throws VKException {
        Response response = request("photos.getAll", param).execute();
        return Photo.parsePhoto(response.root().child("items").get(0));
    }

    /**
     * Возвращает список фотографий, на которых отмечен пользователь.
     */
    public List<Photo> getUserPhotos(Parameters param) throws VKException {
        Response response = request("photos.getUserPhotos", param).execute();
        return Photo.parsePhoto(response.root().child("items").get(0));
    }

    /**
     * Удаляет указанный альбом для фотографий у текущего пользователя.
     */
    public boolean deleteAlbum(Parameters param) throws VKException {
        Response response = request("photos.deleteAlbum", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаление фотографии на сайте.
     */
    public boolean delete(Parameters param) throws VKException {
        Response response = request("photos.delete", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает удаленную фотографию.
     */
    public boolean restore(Parameters param) throws VKException {
        Response response = request("photos.restore", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Подтверждает отметку на фотографии.
     */
    public boolean confirmTag(Parameters param) throws VKException {
        Response response = request("photos.confirmTag", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список комментариев к фотографии.
     */
    public List<Comment> getComments(Parameters param) throws VKException {
        Response response = request("photos.getComments", param).execute();
        return Comment.parseComment(response.root().child("items").get(0));
    }

    /**
     * Возвращает отсортированный в антихронологическом порядке список всех комментариев к конкретному альбому или
     * ко всем альбомам пользователя.
     */
    public List<Comment> getAllComments(Parameters param) throws VKException {
        Response response = request("photos.getAllComments", param).execute();
        return Comment.parseComment(response.root().child("items").get(0));
    }

    /**
     * Создает новый комментарий к фотографии.
     */
    public int createComment(Parameters param) throws VKException {
        Response response = request("photos.createComment", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Удаляет комментарий к фотографии.
     */
    public boolean deleteComment(Parameters param) throws VKException {
        Response response = request("photos.deleteComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает удаленный комментарий к фотографии.
     */
    public boolean restoreComment(Parameters param) throws VKException {
        Response response = request("photos.restoreComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Изменяет текст комментария к фотографии.
     */
    public boolean editComment(Parameters param) throws VKException {
        Response response = request("photos.editComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список отметок на фотографии.
     */
    public List<Tag> getTags(Parameters param) throws VKException {
        Response response = request("photos.getTags", param).execute();
        return Tag.parseTag(response.root().child("items").get(0));
    }

    /**
     * Добавляет отметку на фотографию.
     */
    public int putTag(Parameters param) throws VKException {
        Response response = request("photos.putTag", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Удаляет отметку с фотографии.
     */
    public boolean removeTag(Parameters param) throws VKException {
        Response response = request("photos.removeTag", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список фотографий, на которых есть непросмотренные отметки.
     */
    public List<Photo> getNewTags(Parameters param) throws VKException {
        Response response = request("photos.getNewTags", param).execute();
        return Photo.parsePhoto(response.root().child("items").get(0));
    }

}