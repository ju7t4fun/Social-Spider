package com.epam.lab.spider;

import com.epam.lab.spider.model.ConnectionManager;
import com.epam.lab.spider.model.dao.BaseDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.UserDAO;
import com.epam.lab.spider.model.dao.mysql.DAOFactoryImp;
import com.epam.lab.spider.model.dao.mysql.PostDAO;
import com.epam.lab.spider.model.entity.Post;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        DAOFactory factory = new DAOFactoryImp();
        Post post = new Post();
        post.setMessage("testtest");
        PostDAO dao = factory.create(PostDAO.class);
        System.out.println(dao);
        System.out.println(dao.getById(connection, 1));
        //dao.delete(connection, 1);
        dao.update(connection, 2, post);
        System.out.println(dao.getById(connection, 2));
    }

}
