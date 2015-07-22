package com.epam.lab.spider.controller.command.user.profile;

import com.epam.lab.spider.ServerResolver;
import com.epam.lab.spider.controller.command.ActionCommand;
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
 * Created by Marian Voronovskyi on 22.06.2015.
 */
public class ChangeAvatarCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonError = "";
        ServletContext context = request.getSession().getServletContext();
        UserService userService = new UserService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                AvatarExtension avatarExtension = AvatarExtension.IMAGES;
                String fileName;
                String filePath;
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        if (avatarExtension.checkExtension(FileType.parseFileFormat(fileName))) {
                            filePath = context.getRealPath("/upload/images");
                            SecureRandom random = new SecureRandom();
                            fileName = new BigInteger(130, random).toString(32) +
                                    FileType.parseFileFormat(fileName);
                            item.write(new File(filePath + File.separator + fileName));
                            System.out.println("File uploaded successfully: " + fileName);
                            userService.updateByParameter("avatar_url",
                                    (ServerResolver.getServerPath(request)+"/upload/images/" + fileName), user.getId());
                            user = userService.getById(user.getId());
                            session.setAttribute("user", user);
                            response.getWriter().print(new JSONObject().put("success",
                                    ServerResolver.getServerPath(request)+"/upload/images/" + fileName).put("status", "success").put
                                    ("msg", "Change avatar"));
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
                response.getWriter().print(new JSONObject().put("error", jsonError).put("status", "error").put("msg",
                        jsonError));
            }
        }
    }

    enum AvatarExtension {
        IMAGES(".jpg", ".gif", ".jpeg", ".png");
        private String[] extensions;

        AvatarExtension(String... extensions) {
            this.extensions = extensions;
        }

        public String[] getExtensions() {
            return extensions;
        }

        public boolean checkExtension(String extesion) {
            for (String e : extensions) {
                if (e.equals(extesion)) {
                    return true;
                }
            }
            return false;
        }
    }
}
