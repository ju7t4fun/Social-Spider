package com.epam.lab.spider.controller.utils;

/**
 * Created by Marian Voronovskyi on 21.06.2015.
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
        String format = fileName.substring(dotPosition, fileName.length());
        return format;
    }

    public static Type getType(String fileName) {
        String format = parseFileFormat(fileName);
        Type[] values = Type.values();
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].getFormats().length; j++) {
                if (values[i] == Type.PHOTO && values[i].getFormats()[j].equals(format)) {
                    return Type.PHOTO;
                } else if (values[i] == Type.VIDEO && values[i].getFormats()[j].equals(format)) {
                    return Type.VIDEO;
                } else if (values[i] == Type.AUDIO && values[i].getFormats()[j].equals(format)) {
                    return Type.AUDIO;
                }
            }
        }
        return null;
    }
}
