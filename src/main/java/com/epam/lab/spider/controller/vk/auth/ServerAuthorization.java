package com.epam.lab.spider.controller.vk.auth;

import com.epam.lab.spider.ServerResolver;
import com.epam.lab.spider.controller.vk.Authorization;
import com.epam.lab.spider.controller.vk.Configuration;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 10.06.2015.
 */
public class ServerAuthorization implements Authorization {

    private static final String API_VERSION = "5.27";
    private static final String REDIRECT_URI = ServerResolver.getDefaultServerPath() + "/login";

    private Configuration conf;

    public ServerAuthorization(Configuration conf) {
        this.conf = conf;
    }
    // TODO REFACTOR TO NEW WINDOW
    @Override
    public void open(HttpServletResponse response, boolean revoke) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://oauth.vk.com/authorize?")
                .append("client_id=").append(conf.getAppId())
                .append("&scope=").append(conf.getPermissions())
                .append("&redirect_uri=").append(REDIRECT_URI)
                .append("&v=").append(API_VERSION)
                .append("&response_type=").append("code")
                .append("&revoke=").append(revoke ? 1 : 0);
        try {
            response.sendRedirect(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccessToken signIn(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://oauth.vk.com/access_token?")
                .append("client_id=").append(conf.getAppId())
                .append("&client_secret=").append(conf.getSecretKey())
                .append("&code=").append(request.getParameter("code"))
                .append("&redirect_uri=").append(REDIRECT_URI);
        String context = getResponseContext(sb.toString());
        return parserAccessToken(context);
    }

    private String getResponseContext(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpEntity entity = client.execute(httpGet).getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private AccessToken parserAccessToken(String context) {
        AccessToken token = new AccessToken();
        JSONObject json = new JSONObject(context);
        token.setUserId(json.getInt("user_id"));
        token.setAccessToken(json.getString("access_token"));
        token.setExpirationMoment(json.getLong("expires_in"));
        if (!json.isNull("email"))
            token.setEmail(json.getString("email"));
        return token;
    }

}
