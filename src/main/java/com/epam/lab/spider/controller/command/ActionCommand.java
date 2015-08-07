package com.epam.lab.spider.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Boyarsky Vitaliy
 */
public interface ActionCommand {

    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
