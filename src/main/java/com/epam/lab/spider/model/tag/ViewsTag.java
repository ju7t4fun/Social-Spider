package com.epam.lab.spider.model.tag;

import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 09.07.2015.
 */
public class ViewsTag extends BodyTagSupport {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService service = factory.create(ProfileService.class);

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int doStartTag() throws JspException {
        List<Profile> profiles = service.getByUserId(userId);
        for (Profile profile : profiles) {
            try {
                Vkontakte vk = new Vkontakte(profile.getAppId());
                AccessToken accessToken = new AccessToken();
                accessToken.setAccessToken(profile.getAccessToken());
                accessToken.setUserId(profile.getVkId());
                accessToken.setExpirationMoment(profile.getExtTime().getTime());
                vk.setAccessToken(accessToken);
                vk.stats().trackVisitor();
                return SKIP_BODY;
            } catch (Exception ignored) {
            }
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
