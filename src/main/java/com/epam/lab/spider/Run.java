package com.epam.lab.spider;


import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.controller.utils.ReplaceHtmlTags;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Request;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.job.util.PostAttachmentUtil;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.service.*;
import org.apache.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {
    public static final Logger LOG = Logger.getLogger(Run.class);

    public static int fib(int number) {
        if (number <= 2) return number;
        return fib(number - 1) + fib(number - 2);
    }

    public static void main(String[] args) throws VKException {
        int minNumber = 10, maxNumber = 100;
        int countCouples = 0;
        for (int i = minNumber; i < maxNumber; i++) {
            int numberFib = fib(i);
            System.out.print(numberFib + " ");
            if (numberFib % 2 == 0) countCouples++;
        }
        System.out.print("\nCount couples = " + countCouples);
    }
}

