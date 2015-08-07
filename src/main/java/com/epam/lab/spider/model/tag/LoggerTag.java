package com.epam.lab.spider.model.tag;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author Oleksandra Lobanok
 */
public class LoggerTag extends BodyTagSupport {
    private static final String[] LEVELS = {"debug", "info", "warn", "error", "fatal"};
    private Logger log = null;
    private String configFile = null;
    private String level = null;

    public void setConfigFile(String fileName) {
        this.configFile = fileName;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public int doEndTag() throws JspException {
        String realPath = pageContext.getServletContext().getRealPath("/");
        String fileSep = System.getProperty("file.separator");
        if (realPath != null && (!realPath.endsWith(fileSep))) {
            realPath = realPath + fileSep;
        }
        if (configFile != null) {
            PropertyConfigurator.configure(realPath + "WEB-INF/classes/" + configFile);
        }

        level = level.toLowerCase();

        if (!contains(level)) {
            throw new JspException("The value given for the level attribute is invalid.");
        }

        log = Logger.getLogger(LoggerTag.class);
        String message = getBodyContent().getString().trim();
        try {
            log.log(Level.toLevel(level.toUpperCase()), message);
        } catch (Exception e) {

        }
        return EVAL_PAGE;
    }

    public void release() {
        log = null;
        configFile = null;
        level = null;
    }

    private boolean contains(String str) {
        for (int i = 0; i < LEVELS.length; i++) {
            if (LEVELS[i].equals(str)) {
                return true;
            }
        }
        return false;
    }
}
