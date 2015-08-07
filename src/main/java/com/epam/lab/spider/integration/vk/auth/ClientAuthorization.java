package com.epam.lab.spider.integration.vk.auth;

import com.epam.lab.spider.integration.vk.Authorization;
import com.epam.lab.spider.integration.vk.Configuration;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Boyarsky Vitaliy
 */
public class ClientAuthorization implements Authorization {
    private static final Logger LOG = Logger.getLogger(ClientAuthorization.class);

    private static final String REDIRECT_URI = "https://oauth.vk.com/blank.html";
    private static final String API_VERSION = "5.27";
    private static final String DISPLAY = "page";
    private static final String RESPONSE_TYPE = "token";

    private Configuration conf;

    public ClientAuthorization(Configuration conf) {
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
                .append("&display=").append(DISPLAY)
                .append("&v=").append(API_VERSION)
                .append("&response_type=").append(RESPONSE_TYPE)
                .append("&revoke=").append(revoke ? 1 : 0);
        try {
            response.sendRedirect(sb.toString());
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public AccessToken signIn(HttpServletRequest request) {
        AccessToken token = new AccessToken();
        token.setUserId(Integer.parseInt(request.getParameter("user_id")));
        token.setAccessToken(request.getParameter("access_token"));
        token.setExpirationMoment(Long.parseLong(request.getParameter("expires_in")));
        return token;
    }

}
