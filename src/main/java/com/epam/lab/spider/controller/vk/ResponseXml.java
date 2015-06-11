package com.epam.lab.spider.controller.vk;

import org.apache.http.HttpEntity;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ResponseXml implements Response {

    private static final Logger logger = Logger.getLogger(ResponseXml.class);

    private String contentType;
    private Node root;

    public ResponseXml(HttpEntity entity) throws VKException {
        contentType = entity.getContentType().getValue();
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(entity.getContent());
            processingVKException(doc);
            root = new NodeXml(doc);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        logger.debug("Создан объект " + this);
    }

    private void processingVKException(Document doc) throws VKException {
        try {
            doc.getElementsByTagName("response").item(0).getNodeName();
        } catch (NullPointerException e) {
            int code = Integer.parseInt(doc.getElementsByTagName("error_code").item(0).getTextContent());
            String msg = doc.getElementsByTagName("error_msg").item(0).getTextContent();
            throw new VKException(code, msg);
        }
    }

    @Override
    public Node root() {
        return root;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("com.vk.Response {")
                .append("content_type='").append(contentType).append("', ")
                .append("root='").append(root).append("'}");
        return sb.toString();
    }

}