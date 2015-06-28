package com.epam.lab.spider;


import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws VKException {
        Vkontakte vk = new Vkontakte();
        Parameters param = new Parameters();
        param.add("screen_name","lviv1256");
        System.out.println(vk.utils().resolveScreenName(param));
    }

}

