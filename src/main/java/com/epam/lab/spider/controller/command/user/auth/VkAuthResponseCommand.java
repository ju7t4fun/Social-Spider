package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.api.Users;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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
        //needtoDo : if such a user exists in DB (check by uid i guess) then :
        if (existsInDB) {

            // if he is activated then redirect him to main page
            //else : redirect him to login with message that he is not activated

            return;
        } else {
            String name = "some name";
            String surname = "some surname";
            /*String email = "some email";
            String photoSrc = "some  photo uri";*/

            vk.setAccessToken(token);
            Parameters param = new Parameters();
            param.add("fields","photo_200");
            try {
                List<User> users = vk.users().get(param);
                User user = users.get(0);
                name = user.getFirstName();
                surname = user.getLastName();
            } catch (VKException e) {
                e.printStackTrace();
            }

            request.getSession().setAttribute("username", name+ " " +surname);
            /*request.setAttribute("photoSrc", photoSrc);
            request.setAttribute("email", email);*/

            response.sendRedirect("/register");
            return;
        }
    }
}
