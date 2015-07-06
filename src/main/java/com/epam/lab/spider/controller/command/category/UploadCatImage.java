package com.epam.lab.spider.controller.command.category;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.FileType;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import java.util.Map;

/**
 * Created by Орест on 7/5/2015.
 */
public class UploadCatImage implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String jsonError = "";
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("application/json");


        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                String fileName;
                String filePath;
                FileType.Type type;
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        type = FileType.getType(fileName);
                        if (type != null) {
                            filePath = context.getRealPath(type.getPath());
                            SecureRandom random = new SecureRandom();
                            fileName = new BigInteger(130, random).toString(32) +
                                    FileType.parseFileFormat(fileName);
                            item.write(new File(filePath + File.separator + fileName));
                            request.getSession().setAttribute("urlCat", "http://localhost:8080" + type.getPath() + "/" + fileName);
                            System.out.println("File uploaded successfully");
                            response.getWriter().print(new JSONObject().put("success", "File uploaded " +
                                    "successfully"));
                        } else {
                            jsonError = "Wrong file format!";
                        }
                    }
                }
            } else {
                jsonError = "Request error!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonError = "Upload error! Please try again!";
        } finally {
            if (jsonError != "") {
                response.getWriter().print(new JSONObject().put("error", jsonError));
            }
        }
    }
}
