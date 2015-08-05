package com.epam.lab.spider.controller.command.user.signup;

import com.epam.lab.spider.ServerResolver;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.ReplaceHtmlTags;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.utils.hash.HashMD5;
import com.epam.lab.spider.controller.utils.hash.HashSHA;
import com.epam.lab.spider.controller.utils.mail.MailSender;
import com.epam.lab.spider.controller.utils.mail.MailSenderFactory;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;

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
    private ProfileService profileService = factory.create(ProfileService.class);

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = UTF8.encoding(request.getParameter("name"));
        String surname = UTF8.encoding(request.getParameter("surname"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        String photo200 = request.getParameter("photo_200");

        name = ReplaceHtmlTags.reaplaceAll(name);
        surname = ReplaceHtmlTags.reaplaceAll(surname);
        // Очищуємо сесію від мусору
        HttpSession session = request.getSession();
        session.removeAttribute("name");
        session.removeAttribute("surname");
        session.removeAttribute("vkId");
        session.removeAttribute("email");

        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        if (gRecaptchaResponse == "" || gRecaptchaResponse == null) {
            request.setAttribute("toastr_notification", "error|Captcha error");
            request.getRequestDispatcher("jsp/user/registration.jsp").forward(request, response);
            return;
        }
        User user = userService.getByEmail(email);
        if (user != null && !user.getDeleted()) {
            // Користувач з таким email-ом існує
            request.setAttribute("toastr_notification", "error|" + UTF8.encoding(bundle.getString("reg.notification" +
                    ".exist").replace("{email}", user.getEmail())));
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
            if (photo200 == null)
                user.setAvatarURL(ServerResolver.getServerPath(request)+"/img/avatarDefault.png");
            else
                user.setAvatarURL(photo200);
            userService.insert(user);

            // Якщо зареєстувався через вк привязати user_id до vk_id
            if (!request.getParameter("vkId").equals("")) {
                Profile profile = new Profile();
                profile.setUserId(user.getId());
                profile.setVkId(Integer.parseInt(request.getParameter("vkId")));
                profileService.insert(profile);
            }

            // Надсилання повідомлення активації
            try {
                sendActivationEmail(userService.getById(user.getId()), request);
            }catch (RuntimeException x){
                x.printStackTrace();
            }
            session.setAttribute("toastr_notification", "success|" + UTF8.encoding(bundle.getString("reg.notification" +
                    ".success")));
            response.sendRedirect(ServerResolver.getServerPath(request)+"/login");
        }
    }

    private void sendActivationEmail(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");

        HashSHA sha = new HashSHA();
        String emailPartUri = "&email=" + user.getEmail();
        String hashPartUri = "&hash=" + sha.hash(user.getCreateTime().toString() + user.getState());
        String activateUrl = ServerResolver.getServerPath(request)+"/register?action=activate" + emailPartUri + hashPartUri;

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
