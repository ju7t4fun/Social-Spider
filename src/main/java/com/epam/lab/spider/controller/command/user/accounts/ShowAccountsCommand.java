package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class ShowAccountsCommand implements ActionCommand {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("token"));
        request.getRequestDispatcher("../jsp/user/accounts.jsp").forward(request, response);
    }
}
