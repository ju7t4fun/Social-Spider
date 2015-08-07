package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.ServerResolver;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.utils.facebookauth.FBConnection;
import com.epam.lab.spider.controller.utils.facebookauth.FBGraph;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Dzyuba Orest
 */
public class FbAuthResponseCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
        }
        FBConnection fbConnection = new FBConnection();
        String accessToken = fbConnection.getAccessToken(code);

        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);

        UserService userService = new UserService();
        User user = userService.getByEmail(fbProfileData.get("email"));

        if (user != null) {
            HttpSession session = request.getSession();
            ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
            switch (user.getState()) {
                case CREATED:
                    // Користувач не активний
                    request.setAttribute("toastr_notification", "warning|" + UTF8.encoding(bundle.getString("login" +
                            ".notification.created")));
                    request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
                    return;
                case BANNED:
                    // Користувач забанений
                    request.setAttribute("toastr_notification", "warning|" + UTF8.encoding(bundle.getString("login" +
                            ".notification.banned")));
                    request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
                    return;
                case ACTIVATED:
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect("/");
            }
        } else {
            request.getSession().setAttribute("name", fbProfileData.get("first_name"));
            request.getSession().setAttribute("surname", fbProfileData.get("last_name"));
            request.getSession().setAttribute("email", fbProfileData.get("email"));
            response.sendRedirect(ServerResolver.getServerPath(request)+"/register");
        }


    }
}
