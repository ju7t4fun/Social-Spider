package com.epam.lab.spider.controller.command;

import com.epam.lab.spider.controller.utils.FileType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Iterator;

/**
 * Created by Marian Voronovskyi on 20.06.2015.
 */
public class UploadFileFromURLCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext context = request.getSession().getServletContext();
        response.setContentType("application/json");
        String filePath;
        String message ;
        String status;
        String fileName;
        try {
            String urlFromAjax = request.getParameter("url");
            URL url = new URL(urlFromAjax);
            String extension = "." + FilenameUtils.getExtension(url.toString());
            FileType.Type type = FileType.getType(extension);
            filePath = context.getRealPath(type.getPath());
            SecureRandom random = new SecureRandom();
            fileName = new BigInteger(130, random).toString(32) +
                    extension;
            FileUtils.copyURLToFile(url, new File(filePath + File.separator + fileName));
            status = "success";
            message = "File has been successfully uploaded to server!";
        } catch (Exception e) {
            e.printStackTrace();
            status = "fail";
            message = "Wrong URL, please try again!";
        }
        response.getWriter().print(new JSONObject().put(status, message));
    }
}
