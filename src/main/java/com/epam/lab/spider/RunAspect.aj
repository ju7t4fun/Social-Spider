package com.epam.lab.spider;

/**
 * Created by shell on 6/18/2015.
 */

public aspect RunAspect {
    public String myInjectedField;
    public String RunInterface.getInjectedMethod() {
        return "Injected method";
    }

}
