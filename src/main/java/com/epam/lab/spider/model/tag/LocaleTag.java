package com.epam.lab.spider.model.tag;

import com.epam.lab.spider.controller.utils.UTF8;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Boyarsky Vitaliy on 08.06.2015.
 */
public class LocaleTag extends TagSupport {

    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        try {
            JspWriter out = pageContext.getOut();
            out.write(UTF8.encoding(bundle.getString(key)));
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}