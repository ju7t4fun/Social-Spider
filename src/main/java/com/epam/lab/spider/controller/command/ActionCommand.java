package com.epam.lab.spider.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 08.06.2015.
 */
public interface ActionCommand {

    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
