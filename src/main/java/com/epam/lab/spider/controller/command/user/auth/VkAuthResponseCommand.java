package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 10.06.2015.
 */
public class VkAuthResponseCommand implements ActionCommand {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Vkontakte vk = (Vkontakte) session.getAttribute("siteVk");
        AccessToken token = vk.OAuth().signIn(request);
        session.setAttribute("user_id", token.getUserId());
        session.setAttribute("access_token", token.getAccessToken());
        session.setAttribute("exp_moment", token.getExpirationMoment());

        boolean existsInDB = false;
        if(existsInDB) {
            response.sendRedirect("/login");
        }
        else {
            String name = "some name";
            String surname = "some surname";
            String email = "some email";
            String photoSrc = "some  photo uri";

            request.setAttribute("photoSrc",photoSrc);
            request.setAttribute("surname",surname);
            request.setAttribute("email",email);
            request.setAttribute("name",name);
            RequestDispatcher rd = request
                    .getRequestDispatcher("/register");
            rd.forward(request,response);

            /*response.sendRedirect("/register?name="+name+"&surname="+surname+
                "&email=" + email + "&photoSrc=" + photoSrc );*/
        }
        response.sendRedirect("/login");
    }
}
