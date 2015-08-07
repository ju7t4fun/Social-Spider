package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.restore.ChangePasswordCommand;
import com.epam.lab.spider.controller.command.user.restore.RetrieveRestoreCommand;
import com.epam.lab.spider.controller.command.user.restore.SendRestoreCommand;
import com.epam.lab.spider.controller.command.user.restore.ShowForgotPasswordCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Dzyuba Orest
 */
public class ForgotPasswordServlet extends HttpServlet {

    private static ActionFactory factory = new ForgotPasswordActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class ForgotPasswordActionFactory extends ActionFactory {

        public ForgotPasswordActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowForgotPasswordCommand());
            commands.put("restore", new RetrieveRestoreCommand());
            commands.put("change", new ChangePasswordCommand());
            commands.put("sendMail", new SendRestoreCommand());
        }

    }

}
