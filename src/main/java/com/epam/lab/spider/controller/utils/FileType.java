package com.epam.lab.spider.controller.utils;

/**
 * @author Marian Voronovskyi
 */
public class FileType {

    public enum Type {
        PHOTO("/upload/images", ".jpg", ".bmp", ".gif", ".png", ".jpeg"),
        VIDEO("/upload/videos", ".avi", ".mpeg", ".mpg", ".mp4", ".mov", ".mkv", ".flv"),
        AUDIO("/upload/musics", ".mp3", ".wav");

        private String path;
        private String[] formats;

        Type(String path, String... format) {
            this.path = path;
            this.formats = format;
        }

        public String[] getFormats() {
            return formats;
        }

        public String getPath() {
            return path;
        }
    }

    public static String parseFileFormat(String fileName) {
        fileName = fileName.toLowerCase();
        int dotPosition = fileName.lastIndexOf(".");
        return fileName.substring(dotPosition, fileName.length());
    }

    public static Type getType(String fileName) {
        String format = parseFileFormat(fileName);
        Type[] values = Type.values();
        for (Type value : values) {
            for (int j = 0; j < value.getFormats().length; j++) {
                if (value == Type.PHOTO && value.getFormats()[j].equals(format)) {
                    return Type.PHOTO;
                } else if (value == Type.VIDEO && value.getFormats()[j].equals(format)) {
                    return Type.VIDEO;
                } else if (value == Type.AUDIO && value.getFormats()[j].equals(format)) {
                    return Type.AUDIO;
                }
            }
        }
        return null;
    }
}
