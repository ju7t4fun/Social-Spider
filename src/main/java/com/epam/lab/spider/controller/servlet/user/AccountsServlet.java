package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.accounts.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Boyarsky Vitaliy on 08.06.2015.
 */
public class AccountsServlet extends HttpServlet {

    private static ActionFactory factory = new AccountsActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class AccountsActionFactory extends ActionFactory {

        public AccountsActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowAccountsCommand());
            commands.put("add", new AddAccountCommand());
            commands.put("refresh", new RefreshAccountCommand());
            commands.put("remove", new RemoveAccountCommand());
            commands.put("get", new GetAccountsCommand());
        }

    }

}
