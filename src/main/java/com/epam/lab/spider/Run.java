package com.epam.lab.spider;

import com.epam.lab.spider.model.ConnectionManager;
import com.epam.lab.spider.model.dao.BaseDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.UserDAO;
import com.epam.lab.spider.model.dao.VkProfileDAO;
import com.epam.lab.spider.model.dao.mysql.DAOFactoryImp;
import com.epam.lab.spider.model.entity.VkProfile;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        DAOFactory factory = new DAOFactoryImp();
        VkProfileDAO dao = factory.create(VkProfileDAO.class);
        System.out.println(dao);
        VkProfile profile = new VkProfile();
        profile.setUserId(1);
        profile.setVkId(1000);
        profile.setAccessToken("fsdfsdfsdfdgergekfkwefjrijgedfkmjierjfldfiwdsd");
        profile.setExtTime(new Date());
        System.out.println(dao.update(connection, 1, profile));
    }

}
