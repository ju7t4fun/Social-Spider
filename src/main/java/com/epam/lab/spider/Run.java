package com.epam.lab.spider;

import com.epam.lab.spider.controller.hash.HashMD5;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.service.CategoryService;
import com.epam.lab.spider.model.service.ServiceFactory;
import com.epam.lab.spider.model.service.UserService;
import org.apache.commons.codec.binary.Base64;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws SQLException {
      /* ServiceFactory factory = ServiceFactory.getInstance();
        UserService service = factory.create(UserService.class);
        User user = new User();
        user.setName("����");
        user.setSurname("�����");
        user.setEmail("fsdfsdf");
        user.setPassword("dfsdfsdf");
        user.setRole(User.Role.ADMIN);
        service.insert(user);*/
        // encode data on your side using BASE64
        String str = "HELLO AZAZA";
        byte[]   bytesEncoded = Base64.encodeBase64(str.getBytes());
        System.out.println("ecncoded value is " + new String(bytesEncoded ));

        // Decode data on other side, by processing encoded data
        byte[] valueDecoded= Base64.decodeBase64(bytesEncoded );
        System.out.println("Decoded value is " + new String(valueDecoded));

    }

}
