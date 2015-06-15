package com.epam.lab.spider;


import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.service.CategoryService;
import com.epam.lab.spider.model.service.ServiceFactory;
import com.epam.lab.spider.model.service.UserService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws SQLException {
       ServiceFactory factory = ServiceFactory.getInstance();
        UserService service = factory.create(UserService.class);
        User user = new User();
        user.setName("����");
        user.setSurname("�����");
        user.setEmail("fsdfsdf");
        user.setPassword("dfsdfsdf");
        user.setRole(User.Role.ADMIN);
        service.insert(user);

    }

}
