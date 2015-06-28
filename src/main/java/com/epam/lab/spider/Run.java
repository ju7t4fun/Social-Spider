package com.epam.lab.spider;


import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.api.Stats;
import com.epam.lab.spider.controller.vk.auth.AccessToken;

import java.util.Date;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws VKException {
        Vkontakte vk = new Vkontakte(4949213);
        AccessToken token = new AccessToken();
        token.setAccessToken("d24ded071abd3e7458da3070eeb5395cb1ed6c6e8fc24e43e45b14b851a096a953fff342720d618615cf5");
        token.setUserId(1);
        token.setExpirationMoment(new Date());
        vk.setAccessToken(token);
        Parameters param = new Parameters();
        param.add("owner_id","-24056415");
        param.add("post_id","21770");
        Stats.Reach reach = vk.stats().getPostReach(param);
        System.out.println(reach.getReachSubscribers());
    }

}

