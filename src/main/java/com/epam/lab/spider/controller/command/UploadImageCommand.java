package com.epam.lab.spider.controller.command;

import com.epam.lab.spider.controller.utils.Encryptor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 13.06.2015.
 */
public class UploadImageCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getSession().getServletContext();
        String UPLOAD_DIRECTORY = context.getRealPath("/upload/images");
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        if (!(name.toLowerCase().endsWith(".jpg")
                                || name.toLowerCase().endsWith(".jpeg")
                                || name.toLowerCase().endsWith(".png")
                                || name.toLowerCase().endsWith(".gif"))) {
                            throw new IllegalStateException("Wrong image format!");
                        } else {
                            name = Encryptor.encoding(name) + new Date().getTime() + ".jpg";
                            item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                        }
                    }
                }
                System.out.println("File uploaded successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sorry this Servlet only handles file upload request");
        }
    }
}
