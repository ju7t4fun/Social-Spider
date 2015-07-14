package com.epam.lab.spider.controller.vk;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.List;

public class RequestPost implements Request {

    private static final Logger logger = Logger.getLogger(RequestPost.class);

//    private CloseableHttpClient client = HttpClients.createDefault();
    private HttpPost httpPost;
    private Parameters parameters;

    public RequestPost(String method, Parameters param) {
        httpPost = new HttpPost(HTTP_HOST_METHOD + method + "." + param.getResponseType());
        buildingParameters(param);
        logger.debug("Создан объект " + this);
    }

    private void buildingParameters(Parameters parameters) {
        // о да, серваку надо закодовані 2 рази в URL пост параметри
        // втф???
        List<NameValuePair> param = new ArrayList<>();
        for (String name : parameters.getKeys()) {
            String decode = parameters.get(name);
            try {
                decode = URLDecoder.decode(decode,"UTF-8" );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            param.add(new BasicNameValuePair(name,decode ));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(param,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.parameters = parameters;
    }

    @Override
    public Response execute() throws VKException {
        try {
            //return ResponseFactory.getInstance(parameters.getResponseType(), client.execute(httpPost).getEntity());
            return AlphaLimitVKExecutor.getInstance().execute(httpPost, parameters.getResponseType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        String args, param = null;
        for (String name : parameters.getKeys()) {
            args = name + "='" + parameters.get(name) + "'";
            param = param == null ? args : param + ", " + args;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("com.vk.Request {")
                .append("method='").append(httpPost.getMethod()).append("', ")
                .append("URL='").append(httpPost.getURI().toString()).append("', ")
                .append("fields=[").append(param).append("], ")
                .append("protocol='").append(httpPost.getProtocolVersion()).append("'}");
        return sb.toString();
    }

}
