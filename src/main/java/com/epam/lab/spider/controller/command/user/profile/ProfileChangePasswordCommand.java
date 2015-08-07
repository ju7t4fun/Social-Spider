package com.epam.lab.spider.controller.command.user.profile;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.UserService;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Boyarsky Vitaliy
 */
public class ProfileChangePasswordCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static UserService service = factory.create(UserService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        User user = service.getByEmail(email);
        Date expDate = new Date(new Date().getTime() + 86400000);

        byte[] bytesEncoded = Base64.encodeBase64(Long.toString(expDate.getTime()).getBytes());
        String expDateEncoded = new String(bytesEncoded);

        bytesEncoded = Base64.encodeBase64(user.getPassword().getBytes());
        String passwordEncoded = new String(bytesEncoded);

        String emailPart = "&email=" + user.getEmail();
        String timePart = "&hash=" + expDateEncoded;
        String passwordPart = "&code=" + passwordEncoded;
        request.getRequestDispatcher("forgot_password?action=restore" + emailPart + timePart + passwordPart).forward
                (request, response);
    }
}
