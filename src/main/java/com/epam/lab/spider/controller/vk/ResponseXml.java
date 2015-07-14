package com.epam.lab.spider.controller.vk;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class ResponseXml implements Response {

    private static final Logger logger = Logger.getLogger(ResponseXml.class);

    private String contentType;
    private Node root;

    public ResponseXml(HttpEntity entity) throws VKException {
        contentType = entity.getContentType().getValue();
        Document doc = null;
        try {
            InputStream input = entity.getContent();

            if (false) {
                StringWriter writer = new StringWriter();
                IOUtils.copy(input, writer, "UTF-8");
                String out;
                out = writer.toString();
                logger.debug(out);
                InputStream stream = new ByteArrayInputStream(out.getBytes(StandardCharsets.UTF_8));
                input = stream;

            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(input);
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