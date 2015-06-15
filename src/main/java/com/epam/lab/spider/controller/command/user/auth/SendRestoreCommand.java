package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dmytro on 15.06.2015.
 */
public class SendRestoreCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        String str = "HELLO AZAZA";
        byte[]   bytesEncoded = Base64.encodeBase64(str.getBytes());
        System.out.println("ecncoded value is " + new String(bytesEncoded ));

        // Decode data on other side, by processing encoded data
        byte[] valueDecoded= Base64.decodeBase64(bytesEncoded );
        System.out.println("Decoded value is " + new String(valueDecoded));

    }
}
