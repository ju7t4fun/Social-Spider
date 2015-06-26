package com.epam.lab.spider.controller.command.user.restore;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Orest Dzyuba on 15.06.2015.
 */
public class RetrieveRestoreCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static UserService service = factory.create(UserService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String expDateEncoded = request.getParameter("hash");
        String passwordEncoded = request.getParameter("code");

        byte[] valueDecoded = Base64.decodeBase64(expDateEncoded.getBytes());
        String expDate = new String(valueDecoded);
        valueDecoded = Base64.decodeBase64(passwordEncoded.getBytes());
        String password = new String(valueDecoded);

        if (Long.parseLong(expDate) >= new Date().getTime()) {
            User user = service.getByEmail(email);
            if (!user.getPassword().equals(password)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("email", email);
            System.out.println("---------" );
            request.getRequestDispatcher("jsp/user/pwrestore_newpw.jsp").forward(request, response);
        } else
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
