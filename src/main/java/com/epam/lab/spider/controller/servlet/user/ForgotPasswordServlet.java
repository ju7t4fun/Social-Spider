package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.restore.*;
import com.epam.lab.spider.controller.command.user.signup.ActivateCommand;
import com.epam.lab.spider.controller.command.user.signup.RegisterCommand;
import com.epam.lab.spider.controller.command.user.signup.ShowActivationCommand;
import com.epam.lab.spider.controller.command.user.signup.ShowRegisterCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Орест on 15.06.2015.
 */
public class ForgotPasswordServlet extends HttpServlet {


    private static ActionFactory factory = new ForgotPasswordActionFactory();

    private static class ForgotPasswordActionFactory extends ActionFactory {

        public ForgotPasswordActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowForgotTargetEmailCommand());
            commands.put("showforgotinput", new ShowForgotInput());
            commands.put("retrieverestore", new RetrieveRestoreCommand());
            commands.put("changepassword", new ChangePasswordCommand());
            commands.put("sendmail", new SendRestoreCommand());
        }

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }
}
