package com.epam.lab.spider.model.tag;

import com.epam.lab.spider.controller.utils.UTF8;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ResourceBundle;

/**
 * Created by Boyarsky Vitaliy on 08.06.2015.
 */
public class LocaleTag extends BodyTagSupport {
    static DocumentBuilderFactory factory;
    static{
        factory =
                DocumentBuilderFactory.newInstance();

    }

    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int doStartTag() throws JspException {
        return  EVAL_BODY_BUFFERED ;
    }

    @Override
    public int doAfterBody() throws JspException {
        return SKIP_BODY;
    }


    private String simpleFormat(String key, String value){
        StringBuilder sb = new StringBuilder();
        sb.append("<span class=\"loc-t\" locres=\"");
        sb.append(key);
        sb.append("\">");
        sb.append(value);
        sb.append("</span>");
        return sb.toString();
    }
    private void safeWrite(String code) throws JspException{
        try {
            JspWriter out = pageContext.getOut();
            out.write(code);
        } catch (IOException e) {

            throw new JspException(e.getMessage());
        }
    }

    @Override
    public int doEndTag() throws JspException {
        Long begin = System.nanoTime();
        HttpSession session = pageContext.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        String value = UTF8.encoding(bundle.getString(key));

        if(getBodyContent()!=null){
            String body = this.getBodyContent().getString();
            try {
                //if(body!=null) System.out.println(body);
                DocumentBuilder builder = factory.newDocumentBuilder();
                ByteArrayInputStream input =  new ByteArrayInputStream(
                        body.getBytes("UTF-8"));
                Document doc = builder.parse(input);
                Element root = doc.getDocumentElement();
                root.setTextContent(value);

                root.setAttribute("locres",key);
                String classes = root.getAttribute("class");
                if(classes!=null && classes.isEmpty()){
                    classes = "loc-t";
                }else{
                    classes+= "loc-t";
                }root.setAttribute("class",classes);

                //System.out.println(root.getNodeName());

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                StringWriter writer = new StringWriter();
                transformer.transform(new DOMSource(doc), new StreamResult(writer));
                String output = writer.getBuffer().toString().replaceAll("\n|\r", "");

                safeWrite(output);

            } catch (ParserConfigurationException| SAXException | IOException|NullPointerException|TransformerException e) {
                e.printStackTrace();
                safeWrite(simpleFormat(key,value));
            }
        }else{
            safeWrite(simpleFormat(key,value));
        }


        Long end = System.nanoTime();
        Long d = (end - begin)/1000;
        System.out.println("Tag creating time:" + d);
        return EVAL_PAGE;
    }

}