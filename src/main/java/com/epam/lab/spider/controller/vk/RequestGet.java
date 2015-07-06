package com.epam.lab.spider.controller.vk;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.UnknownHostException;

public class RequestGet implements Request {

    private static final Logger logger = Logger.getLogger(RequestGet.class);

//    private CloseableHttpClient client = HttpClients.createDefault();
    private HttpGet httpGet;
    private Response.Type type;

    public RequestGet(String method, Parameters param) {
        httpGet = new HttpGet(HTTP_HOST_METHOD + method + buildingParameters(param));
        type = param.getResponseType();
        logger.debug("Создан объект " + this);
    }

    private String buildingParameters(Parameters parameters) {
        String args, param = null, method = "." + parameters.getResponseType();
        for (String name : parameters.getKeys()) {
            args = name + "=" + parameters.get(name);
            param = param == null ? method + "?" + args : param + "&" + args;
        }
        return param;
    }

    @Override
    public Response execute() throws VKException {
        try {
            return AlphaLimitVKExecutor.getInstance().execute(httpGet, type);
            //return ResponseFactory.getInstance(type, client.execute(httpGet).getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("com.vk.Request {")
                .append("method='").append(httpGet.getMethod()).append("', ")
                .append("URL='").append(httpGet.getURI().toString()).append("', ")
                .append("protocol='").append(httpGet.getProtocolVersion()).append("'}");
        return sb.toString();
    }

}
