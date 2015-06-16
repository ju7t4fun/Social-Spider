package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.facebookauth.FBConnection;
import com.epam.lab.spider.controller.utils.facebookauth.FBGraph;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Орест on 16.06.2015.
 */
public class FbAuthResponseCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR: Didn't get code parameter in callback.");
        }
        FBConnection fbConnection = new FBConnection();
        String accessToken = fbConnection.getAccessToken(code);

        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);

        UserService userService = new UserService();
        User user  =  userService.getByEmail(fbProfileData.get("email"));
        if (user!=null) {

            if (!user.getConfirm()) {
                // Користувач не активний
                request.setAttribute("loginMessage", "Your account is non-activated. Please activate " +
                        "your account via email");
                request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
                return;
            }

            request.getSession().setAttribute("user", user);
            response.sendRedirect("/");
        } else {
            request.getSession().setAttribute("name",fbProfileData.get("first_name"));
            request.getSession().setAttribute("surname",fbProfileData.get("last_name"));
            request.getSession().setAttribute("email",fbProfileData.get("email") );
            response.sendRedirect("/register");
        }


    }
}
