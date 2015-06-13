package com.epam.lab.spider.controller.utils.validation;

import com.epam.lab.spider.controller.utils.FrontEndValidatorBrige;
import com.epam.lab.spider.model.entity.User;

import java.util.Map;


/**
 * Created by shell on 6/13/2015.
 */
public class ValidationTestRun {
    public static void main(String[] args){

        Validator validator = new Validator();

        User user = new User();
        user.setEmail("str number 4");
        ValidateResult rs = validator.valideteWithResult(user);
        for(Map.Entry e:rs.getInvalideMessage().entrySet()){
            System.out.println(""+e);
        }
        System.out.println(validator.isValid(user));
        /**                                                 */
        user.setEmail("str@number.four");
        user.setName("str4");
        System.out.println(validator.isValid(user));

        FrontEndValidatorBrige brige = new FrontEndValidatorBrige();
        String code = brige.getFrontEndValidationCodeByFieldPath("user.email");
        System.out.println(code);


    }
}
