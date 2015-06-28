package com.epam.lab.spider;



import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {
    public  final static Logger LOG = Logger.getLogger(Run.class);



    public static void main(String[] args) throws SQLException, ResolvableDAOException, InvalidEntityException, UnsupportedDAOException, UnsupportedServiseException {

        Vkontakte vk = new Vkontakte();
        Parameters param = new Parameters();
        param.add("screen_name","lviv1256");
        try {
            System.out.println(vk.utils().resolveScreenName(param));
        } catch (VKException e) {
            e.printStackTrace();
        }


    }
}

