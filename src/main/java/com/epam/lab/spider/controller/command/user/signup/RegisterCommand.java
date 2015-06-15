package com.epam.lab.spider.controller.command.user.signup;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.utils.hash.HashMD5;
import com.epam.lab.spider.controller.utils.hash.HashSHA;
import com.epam.lab.spider.controller.utils.mail.MailSender;
import com.epam.lab.spider.controller.utils.mail.MailSenderFactory;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.service.ServiceFactory;
import com.epam.lab.spider.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Dmytro on 11.06.2015.
 */
public class RegisterCommand implements ActionCommand {

    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService userService = factory.create(UserService.class);

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = UTF8.encoding(request.getParameter("name"));
        String surname = UTF8.encoding(request.getParameter("surname"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.getByEmail(email);
        if (user != null && !user.getDeleted()) {
            // Користувач з таким email-ом існує
            request.setAttribute("registerMessage", "User with email :" + user.getEmail() + " already exists!");
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            request.getRequestDispatcher("jsp/user/registration.jsp").forward(request, response);
        } else {
            // Ствоерення новго користувача
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setSurname(surname);
            user.setPassword(new HashMD5().hash(password));
            user.setRole(User.Role.USER);
            userService.insert(user);

            // Надсилання повідомлення активації
            sendActivationEmail(userService.getById(user.getId()), request);

            request.setAttribute("activationMessage", "You have been Successfully registered. " +
                    "Please activate your account");
            request.getRequestDispatcher("/register?action=activateResult").forward(request, response);
        }
    }

    private void sendActivationEmail(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");

        HashSHA sha = new HashSHA();
        String emailPartUri = "&email=" + user.getEmail();
        String hashPartUri = "&hash=" + sha.hash(user.getCreateTime().toString() + false);
        String activateUrl = "http://localhost:8080/register?action=activate" + emailPartUri + hashPartUri;

        // Створення вмісту повідомлення
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(UTF8.encoding(bundle.getString("reg.activate.welcome"))).append("<h1>")
                .append("<br>").append(UTF8.encoding(bundle.getString("reg.activate.message"))).append("<br>")
                .append(UTF8.encoding(bundle.getString("reg.activate.confirm"))).append("<br>")
                .append("<a href=").append(activateUrl).append(">").append(UTF8.encoding(bundle.getString("reg" +
                ".activate.click"))).append("</a>");

        MailSender mail = MailSenderFactory.createMailSender(MailSender.ContextType.HTML);
        mail.send(UTF8.encoding(bundle.getString("reg.activate.subject")), html.toString(), user.getEmail());
    }

}
