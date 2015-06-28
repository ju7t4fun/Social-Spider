package com.epam.lab.spider;


import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;

import java.util.Random;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws VKException {
        Vkontakte vk = new Vkontakte();
        String string = vk.utils().getServerTime().toString();
        System.out.println("TIME: "+string);

//        Parameters param = new Parameters();
//        param.add("screen_name","lviv1256");
//        System.out.println(vk.utils().resolveScreenName(param));

//        Random random = new Random();
//        for (int i = 0; i < 25 ; i++) {
//        System.out.println(random.nextInt(2));
//
//        }

    }

}

