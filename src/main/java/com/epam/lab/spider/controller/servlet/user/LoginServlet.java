package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.auth.ShowLoginCommand;
import com.epam.lab.spider.controller.command.user.auth.SignInCommand;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class LoginServlet extends HttpServlet {

    private static ActionFactory factory = new LoginActionFactory();

    private static class LoginActionFactory extends ActionFactory {

        public LoginActionFactory() {
            commands = new HashMap<String, ActionCommand>();
            commands.put("default", new ShowLoginCommand());
            commands.put("signin", new SignInCommand());
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        ActionCommand command = factory.action(request, response);
        command.doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String auth = "https://oauth.vk.com/access_token?client_id=4949208&client_secret=780FmVhvpLu8HobgGD8J&code=";
        if (request.getParameter("code") != null) {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(auth + request.getParameter("code") + "&redirect_uri=http://localhost:8080/login");
            HttpEntity entity = client.execute(httpGet).getEntity();
            InputStream input = entity.getContent();

            JSONObject json = new JSONObject(EntityUtils.toString(entity));
            System.out.println(json.getString("access_token"));
            return;
        }
        ActionCommand command = factory.action(request, response);
        command.doGet(request, response);
    }
}
