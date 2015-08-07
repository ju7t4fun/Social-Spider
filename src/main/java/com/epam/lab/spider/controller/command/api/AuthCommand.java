package com.epam.lab.spider.controller.command.api;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.utils.hash.HashMD5;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.UserService;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

/**
 * @author Boyarsky Vitaliy
 */
public class AuthCommand implements ActionCommand {

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final UserService service = factory.create(UserService.class);
    private static HashMD5 md5 = new HashMD5();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        if (service.checkPassword(email, md5.hash(password))) {
            User user = service.getByEmail(email);
            switch (user.getState()) {
                case ACTIVATED:
                    json.put("response", "success");
                    json.put("message", UTF8.encoding(bundle.getString("extension.auth.success"))
                            .replace("{user}", user.getName() + " " + user.getSurname()));
                    json.put("user_id", user.getId());
                    break;
                case CREATED:
                    json.put("response", "warning");
                    json.put("message", UTF8.encoding(bundle.getString("extension.auth.created"))
                            .replace("{user}", user.getName() + " " + user.getSurname()));
                    json.put("user_id", user.getId());
                    break;
                case BANNED:
                    json.put("response", "error");
                    json.put("message", UTF8.encoding(bundle.getString("extension.auth.banned"))
                            .replace("{user}", user.getName() + " " + user.getSurname()));
                    json.put("user_id", user.getId());
            }
        } else {
            json.put("response", "error");
            json.put("message", UTF8.encoding(bundle.getString("extension.auth.error")));
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(json.toJSONString());
        writer.flush();
    }

}
