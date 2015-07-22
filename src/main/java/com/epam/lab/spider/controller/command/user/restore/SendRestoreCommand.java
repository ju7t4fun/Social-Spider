package com.epam.lab.spider.controller.command.user.restore;

import com.epam.lab.spider.ServerResolver;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.utils.mail.MailSender;
import com.epam.lab.spider.controller.utils.mail.MailSenderFactory;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;


/**
 * Created by Orest Dzyuba on 15.06.2015.
 */
public class SendRestoreCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static UserService service = factory.create(UserService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        User user = service.getByEmail(email);
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        if (user != null) {
            switch (user.getState()) {
                case BANNED:
                    request.setAttribute("toastr_notification", "warning|" + UTF8.encoding(bundle.getString("login" +
                            ".notification.banned")));
                    request.getRequestDispatcher("jsp/user/pwrestore_email.jsp").forward(request, response);
                    return;
                case CREATED:
                    request.setAttribute("toastr_notification", "warning|" + UTF8.encoding(bundle.getString("login" +
                            ".notification.created")));
                    request.getRequestDispatcher("jsp/user/pwrestore_email.jsp").forward(request, response);
                    return;
                case ACTIVATED:
                    session.setAttribute("toastr_notification", "success|" + UTF8.encoding(bundle.getString("restore" +
                            ".notification.send")));
                    sendMail(request, user);
                    response.sendRedirect(ServerResolver.getServerPath(request)+"/login");
                    return;
            }
        }
        request.setAttribute("toastr_notification", "error|" + UTF8.encoding(bundle.getString("restore.notification" +
                ".error")));
        request.getRequestDispatcher("jsp/user/pwrestore_email.jsp").forward(request, response);
    }

    private void sendMail(HttpServletRequest request, User user) {
        Date expDate = new Date(new Date().getTime() + 86400000);

        byte[] bytesEncoded = Base64.encodeBase64(Long.toString(expDate.getTime()).getBytes());
        String expDateEncoded = new String(bytesEncoded);

        bytesEncoded = Base64.encodeBase64(user.getPassword().getBytes());
        String passwordEncoded = new String(bytesEncoded);

        String emailPart = "&email=" + user.getEmail();
        String timePart = "&hash=" + expDateEncoded;
        String passwordPart = "&code=" + passwordEncoded;

        String restoreUrl = ServerResolver.getServerPath(request)+"/forgot_password?action=restore" + emailPart + timePart +
                passwordPart;

        ResourceBundle bundle = (ResourceBundle) request.getSession().getAttribute("bundle");
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(UTF8.encoding(bundle.getString("reg.activate.welcome"))).append("<br>")
                .append("<a href=").append(restoreUrl).append(">").append(UTF8.encoding(bundle.getString("restore." +
                "clickRestoreMessage"))).append("</a>");

        MailSender mail = MailSenderFactory.createMailSender(MailSender.ContextType.HTML);
        mail.send(UTF8.encoding(bundle.getString("restore.restoreMessage")), html.toString(), user.getEmail());
    }

}
