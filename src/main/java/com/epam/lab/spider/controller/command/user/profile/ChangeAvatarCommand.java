package com.epam.lab.spider.controller.command.user.profile;

import com.epam.lab.spider.ServerLocationUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.FileType;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
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
import java.util.Objects;

/**
 * @author Marian Voronovskyi
 */
public class ChangeAvatarCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(ChangeAvatarCommand.class) ;

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
                List<FileItem> multiParts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiParts) {
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        if (avatarExtension.checkExtension(FileType.parseFileFormat(fileName))) {
                            filePath = context.getRealPath("/upload/images");
                            SecureRandom random = new SecureRandom();
                            fileName = new BigInteger(130, random).toString(32) +
                                    FileType.parseFileFormat(fileName);
                            item.write(new File(filePath + File.separator + fileName));
                            LOG.info("File uploaded successfully: " + fileName);
                            userService.updateByParameter("avatar_url",
                                    (ServerLocationUtils.getServerPath(request)+"/upload/images/" + fileName), user.getId());
                            user = userService.getById(user.getId());
                            session.setAttribute("user", user);
                            response.getWriter().print(new JSONObject().put("success",
                                    ServerLocationUtils.getServerPath(request)+"/upload/images/" + fileName).put("status", "success").put
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
            LOG.error(e.getLocalizedMessage(), e);
            jsonError = "Upload error! Please try again!";
        } finally {
            if (!Objects.equals(jsonError, "")) {
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

        public boolean checkExtension(String extension) {
            for (String e : extensions) {
                if (e.equals(extension)) {
                    return true;
                }
            }
            return false;
        }
    }
}
