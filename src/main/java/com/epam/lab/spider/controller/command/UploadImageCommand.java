package com.epam.lab.spider.controller.command;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 13.06.2015.
 */
public class UploadImageCommand implements ActionCommand {

    enum Type {

        IMAGES("/upload/images", ".jpg", ".bmp", ".gif", ".png", ".jpeg"),
        VIDEOS("/upload/videos", ".avi", ".mpeg", ".mpg", ".mp4", ".mov", ".mkv", ".flv"),
        MUSICS("/upload/musics", ".mp3", ".wav");

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

    private static String parseFileFormat(String fileName) {
        fileName = fileName.toLowerCase();
        int dotPosition = fileName.lastIndexOf(".");
        String format = fileName.substring(dotPosition, fileName.length());
        return format;
    }

    private Type getType(String fileName) {
        String format = parseFileFormat(fileName);
        Type[] values = Type.values();
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].getFormats().length; j++) {
                if (values[i] == Type.IMAGES && values[i].getFormats()[j].equals(format)) {
                    return Type.IMAGES;
                } else if (values[i] == Type.VIDEOS && values[i].getFormats()[j].equals(format)) {
                    return Type.VIDEOS;
                } else if (values[i] == Type.MUSICS && values[i].getFormats()[j].equals(format)) {
                    return Type.MUSICS;
                }
            }
        }
        return null;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext context = request.getSession().getServletContext();

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                String fileName = null;
                String filePath;
                Type type = null;
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                if (multiparts!=null) {
                    System.out.println("ammount of multiparts: " + multiparts.size());
                } else {
                    System.out.println("no multyparts(((");
                }
                for (FileItem item : multiparts) {
                    System.out.println("in loop");
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        type = getType(fileName);
                        filePath = context.getRealPath(type.path);
                        System.out.println("in first cond");
                        if (type != null) {
                            System.out.println("in second cond");
                            SecureRandom random = new SecureRandom();
                            fileName = new BigInteger(130, random).toString(32) +
                                    parseFileFormat(fileName);
                            item.write(new File(filePath + File.separator + fileName));
                            System.out.println("File path: " + context.getRealPath(type.path));
                        } else {
                            throw new IllegalStateException("Wrong file format!");
                        }
                    }
                }
                // response.getWriter().print(json.toString());
                System.out.println("File uploaded successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sorry this Servlet only handles file upload request");
        }
    }
}
