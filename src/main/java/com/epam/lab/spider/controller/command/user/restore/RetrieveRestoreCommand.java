package com.epam.lab.spider.controller.command.user.restore;

import com.epam.lab.spider.controller.command.ActionCommand;
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
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailEncoded = request.getParameter("hash1");
        String expDateEncoded = request.getParameter("hash2");


        byte[] valueDecoded= Base64.decodeBase64(emailEncoded.getBytes());
        String email = new String(valueDecoded);


        valueDecoded= Base64.decodeBase64(expDateEncoded.getBytes());
        String expDate = new String(valueDecoded);

        if (Long.parseLong(expDate) >=  new Date().getTime() ) {
            request.setAttribute("email",email);
            request.getRequestDispatcher("/forgotpassword?action=showforgotinput&email=" + email).forward(request, response);
            return;
        } else {
            response.sendError(404,"PEZDA");
        }

    }
}
