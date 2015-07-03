package com.epam.lab.spider.controller.vk;

public class VKException extends Exception {

    public static final int VK_UNKNOWN_ERROR = 1;
    public static final int VK_APP_OFF = 2;
    public static final int VK_UNKNOWN_METHOD = 3;
    public static final int VK_INCORRECT_SIGNATURE = 4;
    public static final int VK_AUTHORIZATION_FAILED = 5;
    public static final int VK_MANY_REQUESTS = 6;
    public static final int VK_NO_PERMISSION = 7;
    public static final int VK_INVALID_REQUEST = 8;
    public static final int VK_FLOOD_CONTROL = 9;
    public static final int VK_SERVER_ERROR = 10;
    public static final int VK_TEST_MODE_NOT_LOGGED = 11;
    public static final int VK_CAPTCHA_NEEDED = 14;
    public static final int VK_ACCESS_DENIED = 15;
    public static final int VK_HTTP_AUTHORIZATION_FAILED = 16;
    public static final int VK_VALIDATION_REQUIRED = 17;
    public static final int VK_DISABLED_ACTION = 20;
    public static final int VK_ENABLE_ACTION_OPENAPI = 21;
    public static final int VK_METHOD_DISABLED = 23;
    public static final int VK_INVALID_PARAMETERS = 100;
    public static final int VK_INVALID_APPLICATION_API_ID = 101;
    public static final int VK_INVALID_USER_ID = 113;
    public static final int VK_INVALID_TIMESTAMP = 150;
    public static final int VK_ACCESS_ALBUM_DENIED = 200;
    public static final int VK_ACCESS_AUDIO_DENIED = 201;
    public static final int VK_ACCESS_GROUP_DENIED = 203;
    public static final int VK_AUDIO_HOLDER_WAIN = 270;
    public static final int VK_ALBUM_IS_FULL = 300;

    private int exceptionCode = VK_UNKNOWN_ERROR;

    public VKException() {
        super();
    }

    public VKException(int code, String msg) {
        super(generationMessage(code, msg));
        this.exceptionCode = code;
    }

    private static String generationMessage(int code, String msg) {
        switch (code) {
            case VK_UNKNOWN_ERROR:
                return "Произошла неизвестная ошибка.";
            case VK_APP_OFF:
                return "Приложение выключено.";
            case VK_UNKNOWN_METHOD:
                return "Передан неизвестный метод.";
            case VK_INCORRECT_SIGNATURE:
                return "Неверная подпись.";
            case VK_AUTHORIZATION_FAILED:
                return "Авторизация пользователя не удалась.";
            case VK_MANY_REQUESTS:
                return "Слишком много запросов в секунду.";
            case VK_NO_PERMISSION:
                return "Нет прав для выполнения этого действия.";
            case VK_INVALID_REQUEST:
                return "Неверный запрос.";
            case VK_FLOOD_CONTROL:
                return "Слишком много однотипных действий.";
            case VK_SERVER_ERROR:
                return "Произошла внутренняя ошибка сервера.";
            case VK_TEST_MODE_NOT_LOGGED:
                return "В тестовом режиме приложение должно быть выключено или пользователь должен быть залогинен.";
            case VK_CAPTCHA_NEEDED:
                return "Требуется ввод кода с картинки (Captcha).";
            case VK_ACCESS_DENIED:
                return "Доступ запрещён.";
            case VK_HTTP_AUTHORIZATION_FAILED:
                return "Требуется выполнение запросов по протоколу HTTPS, т.к. пользователь включил настройку, требующую работу через безопасное соединение.";
            case VK_VALIDATION_REQUIRED:
                return "Требуется валидация пользователя.";
            case VK_DISABLED_ACTION:
                return "Данное действие запрещено для не Standalone приложений.";
            case VK_ENABLE_ACTION_OPENAPI:
                return "Данное действие разрешено только для Standalone и Open API приложений.";
            case VK_METHOD_DISABLED:
                return "Метод был выключен.";
            case VK_INVALID_PARAMETERS:
                return "Один из необходимых параметров был не передан или неверен.";
            case VK_INVALID_APPLICATION_API_ID:
                return "Неверный API ID приложения.";
            case VK_INVALID_USER_ID:
                return "Неверный идентификатор пользователя.";
            case VK_INVALID_TIMESTAMP:
                return "Неверный timestamp.";
            case VK_ACCESS_ALBUM_DENIED:
                return "Доступ к альбому запрещён.";
            case VK_ACCESS_AUDIO_DENIED:
                return "Доступ к аудио запрещён.";
            case VK_ACCESS_GROUP_DENIED:
                return "Доступ к группе запрещён.";
            case VK_ALBUM_IS_FULL:
                return "Альбом переполнен.";
            case VK_AUDIO_HOLDER_WAIN:
                return "Аудиозапись была изъята по запросу правообладателя и не может быть загружена. ";
            default:
                return msg;
        }
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

}