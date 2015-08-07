package com.epam.lab.spider.integration.vk;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ResponseJson implements Response {
    private static final Logger LOG = Logger.getLogger(ResponseJson.class);

    private JSONObject rootJson;

    public ResponseJson(HttpEntity entity) throws VKException{
        try{
            String response = EntityUtils.toString(entity, "UTF-8");
            JSONParser parser = new JSONParser();
            JSONObject jsonRootObject = (JSONObject) parser.parse(response);
            processingVKException(jsonRootObject);
            rootJson = jsonRootObject;
        }catch(IOException x){
            x.printStackTrace();
        }catch(ParseException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }


    private void processingVKException(JSONObject jsonRootObject) throws VKException {
        try {
            Object errorObject = jsonRootObject.get("error");
            if(errorObject == null)return;
            if(errorObject != null && errorObject instanceof JSONObject){
                JSONObject jsonError = (JSONObject) errorObject;
                int code = Integer.parseInt(jsonError.get("error_code").toString());
                String msg = jsonError.get("error_msg").toString();
                throw new VKException(code, msg);
            }
        } catch (NullPointerException e) {
            throw new VKException(VKException.VK_UNKNOWN_ERROR, "");
        }
    }

    public JSONObject jsonRoot(){
        return rootJson;
    }
    @Override
    public Node root() {
        return null;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("com.vk.Response {")
                .append("root='").append(rootJson).append("'}");
        return sb.toString();
    }
}
