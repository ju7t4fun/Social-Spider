package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Boyarsky Vitaliy on 10.06.2015.
 */
public class VkAuthResponseCommand implements ActionCommand {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String auth = "https://oauth.vk.com/access_token?client_id=4949208&client_secret=780FmVhvpLu8HobgGD8J&code=";
        if (request.getParameter("code") != null) {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(auth + request.getParameter("code") +
                    "&redirect_uri=http://localhost:8080/login");
            HttpEntity entity = client.execute(httpGet).getEntity();
            InputStream input = entity.getContent();

            JSONObject json = new JSONObject(EntityUtils.toString(entity));
            System.out.println(json.toString());


            HttpSession session = request.getSession();
            session.setAttribute("user_id", json.getInt("user_id"));
            session.setAttribute("access_token", json.getString("access_token"));

            int userID = json.getInt("user_id");
            String accessToken = json.getString("access_token");

            String querr = "https://api.vk.com/method/users.get?uids=" + Integer.toString(userID) +
                    "&fields=uid,first_name,last_name,nickname,sex,bdate,city,country,photo";

            httpGet = new HttpGet(querr);
            entity = client.execute(httpGet).getEntity();

            JSONObject jsonUser = new JSONObject(EntityUtils.toString(entity));

            System.out.println(jsonUser.toString());

            //getting 0-th obj from response

            JSONArray jArr = jsonUser.getJSONArray("response");
            jsonUser = jArr.getJSONObject(0);

            String querrCity = "https://api.vk.com/method/database.getCitiesById?city_ids=" +
                    Integer.toString(jsonUser.getInt("city"));
            httpGet = new HttpGet(querrCity);
            entity = client.execute(httpGet).getEntity();

            JSONObject jsonCity = new JSONObject(EntityUtils.toString(entity));

            System.out.println(jsonCity.toString());
            String cityStr = jsonCity.getJSONArray("response").getJSONObject(0).getString("name");
            System.out.println(cityStr);

            response.sendRedirect("/login");
        }
    }
}
