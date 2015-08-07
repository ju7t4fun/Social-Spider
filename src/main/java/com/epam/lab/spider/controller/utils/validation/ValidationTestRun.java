package com.epam.lab.spider.controller.utils.validation;

import com.epam.lab.spider.controller.utils.FrontEndValidatorBridge;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import org.apache.log4j.Logger;

import java.util.Map;


/**
 * @author Yura Kovalik
 */
public class ValidationTestRun {
    private static final Logger LOG = Logger.getLogger(ValidationTestRun.class);
    public static void main(String[] args){

        Validator validator = new Validator();

        User user = BasicEntityFactory.getSynchronized().createUser();
        user.setEmail("str number 4");
        ValidateResult rs = validator.valideteWithResult(user);
        for(Map.Entry e:rs.getInvalidMessage().entrySet()){
            LOG.info("" + e);
        }
        LOG.info(validator.isValid(user));

        user.setEmail("str@number.four");
        user.setName("str4");
        LOG.info(validator.isValid(user));

        FrontEndValidatorBridge bridge = new FrontEndValidatorBridge();
        String code = bridge.getFrontEndValidationCodeByFieldPath("user.email");
        LOG.info(code);


    }
}
