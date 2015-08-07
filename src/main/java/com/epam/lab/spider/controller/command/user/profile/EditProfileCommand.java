package com.epam.lab.spider.controller.command.user.profile;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.UserService;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marian Voronovskyi
 */
public class EditProfileCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(EditProfileCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");

        String name = request.getParameter("name");
        String value = request.getParameter("value");

        response.setContentType("application/json");
        User user = (User) session.getAttribute("user");
        Pattern p = Pattern.compile("[^a-zA-Zа-яА-ЯіІїЇєЄ]");
        Matcher m = p.matcher(value);
        if (name == null || name.equals("") || value == null || value.equals("")) {
            response.getWriter().print(new JSONObject().put("status", "error").put("msg", UTF8.encoding(bundle.getString("notification.field.not.empty"))));
        } else if (value.length() >= 30) {
            response.getWriter().print(new JSONObject().put("status", "error").put("msg", UTF8.encoding(bundle.getString("notification.value.less.than"))));
        } else if (value.matches("[a-zA-Zа-яА-ЯіІїЇєЄ ]*\\d+.*")) {
            response.getWriter().print(new JSONObject().put("status", "error").put("msg", UTF8.encoding(bundle.getString("notification.field.digits"))));
        } else if (m.find()) {
            response.getWriter().print(new JSONObject().put("status", "error").put("msg", UTF8.encoding(bundle
                    .getString("notification.field.wrongnameformat"))));
        } else {
            try {
                UserService userService = new UserService();
                userService.updateByParameter(name, value, user.getId());
                user = userService.getById(user.getId());
                session.setAttribute("user", user);
                JSONObject json = new JSONObject().put("status", "success").put("fname", user.getName()).put("lname",
                        user.getSurname()).put("msg", UTF8.encoding(bundle.getString("notification.change.name.to")) + user.getName() + " " + user.getSurname());
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(json.toString());
            } catch (Exception e) {
                LOG.error(e.getLocalizedMessage(), e);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", "error");
                jsonObject.put("msg", UTF8.encoding(bundle.getString("notification.enter.correct.data")));
                response.getWriter().print(jsonObject.toString());
            }
        }
    }
}
