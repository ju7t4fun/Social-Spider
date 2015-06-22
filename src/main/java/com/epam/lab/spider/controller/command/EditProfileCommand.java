package com.epam.lab.spider.controller.command;

import com.epam.lab.spider.controller.utils.FileType;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 21.06.2015.
 */
public class EditProfileCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        String jsonError = "";
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("application/json");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("NAME: " + name);
        System.out.println("VALUE: " + value);
        try {
            UserService userService = new UserService();
            userService.updateByParameter(name, value, 1);
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "error");
            jsonObject.put("msg", "Error, please enter correct data!");
            response.getWriter().print(jsonObject.toString());
        }

        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                String fileName;
                String filePath;
                FileType.Type type;
                FileItem item = (FileItem) new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        type = FileType.getType(fileName);
                        if (type != null) {
                            filePath = context.getRealPath(type.getPath());
                            SecureRandom random = new SecureRandom();
                            fileName = new BigInteger(130, random).toString(32) +
                                    FileType.parseFileFormat(fileName);
                            item.write(new File(filePath + File.separator + fileName));
                            System.out.println("File uploaded successfully");
                            response.getWriter().print(new JSONObject().put("success", "File uploaded " +
                                    "successfully"));
                            // System.out.println("File path: " + context.getRealPath(type.path));
                        } else {
                            jsonError = "Wrong file format!";
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
