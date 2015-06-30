package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.vk.Audio;
import com.epam.lab.spider.model.vk.Photo;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shell on 6/30/2015.
 */
public class PostAttachmentUtil {
    public static final Logger LOG = Logger.getLogger(PostAttachmentUtil.class);
    public static String uploadPhoto(Vkontakte vk, String file, Integer wall) {
        boolean manyRequest = false;
        do {
            try {
                if (manyRequest) {
                    Thread.sleep(400);
                    manyRequest = false;
                }
                Parameters parameters;
                parameters = new Parameters();
                if (wall < 0) parameters.add("group_id", -wall);
                URL uri = vk.photos().getWallUploadServer(parameters);
                LOG.debug("SERVER URL to upload photo :" + uri);

                LOG.debug("Begin upload photo with url: " + file);
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(uri.toString());
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addBinaryBody("photo", new URL(file).openStream(), ContentType.create("image/jpeg"), "image" +
                                ".jpg").build();
                httpPost.setEntity(entity);

                String response = EntityUtils.toString(client.execute(httpPost).getEntity(), "UTF-8");
                client.close();
                LOG.debug("RESPONSE FROM FILE SERVER: " + response);

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response);

                parameters = new Parameters();
                parameters.add("server", jsonObject.get("server").toString());
                parameters.add("photo", jsonObject.get("photo").toString());
                parameters.add("hash", jsonObject.get("hash").toString());
                if (wall > 0)
                    parameters.add("user_id", wall);
                else
                    parameters.add("group_id", -wall);

                List<Photo> photos = vk.photos().saveWallPhoto(parameters);
                for (Photo photo : photos) {
                    String photoInVk = "photo" + photo.getOwnerId() + "_" + photo.getId();
                    return photoInVk;
                }

            } catch (VKException x) {
                if (x.getExceptionCode() == VKException.VK_MANY_REQUESTS) manyRequest = true;
                x.printStackTrace();
            }// фікс кривої архітектури
            // перехоплення NullPointerException by UnknownHostException
            catch (NullPointerException x){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            catch (InterruptedException | IOException | ParseException e) {
                e.printStackTrace();
            }
        } while (manyRequest);
        return null;
    }

    public static String uploadAudio(Vkontakte vk, String file) {
        boolean manyRequest = false;
        do {
            try {
                if (manyRequest) {
                    Thread.sleep(400);
                    manyRequest = false;
                }
                Parameters parameters;
                URL uri = new URL(vk.audio().getUploadServer());
                LOG.debug("SERVER URL to upload audio :" + uri);

                LOG.debug("Begin upload audoi with url: " + file);
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(uri.toString());
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addBinaryBody("file", new URL(file).openStream(), ContentType.create("audio/mpeg"), "audio" +
                                ".mp3").build();
                httpPost.setEntity(entity);

                String response = EntityUtils.toString(client.execute(httpPost).getEntity(), "UTF-8");
                client.close();
                LOG.debug("RESPONSE FROM FILE SERVER: " + response);

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response);

                parameters = new Parameters();
                parameters.add("server", jsonObject.get("server").toString());
                parameters.add("audio", jsonObject.get("audio").toString());
                parameters.add("hash", jsonObject.get("hash").toString());
                List<Audio> audios = vk.audio().save(parameters);
                for (Audio audio : audios) {
                    String audioInVk = "audio" + audio.getOwnerId() + "_" + audio.getId();
                    return audioInVk;
                }
            } catch (VKException x) {
                if (x.getExceptionCode() == VKException.VK_MANY_REQUESTS) manyRequest = true;
                x.printStackTrace();
            }// фікс кривої архітектури
            // перехоплення NullPointerException by UnknownHostException
            catch (NullPointerException x){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (manyRequest);
        return null;
    }
    public static String uploadVideo(Vkontakte vk, String file, Integer wall) {
        boolean manyRequest = false;
        do {
            try {
                if (manyRequest) {
                    Thread.sleep(400);
                    manyRequest = false;
                }
                Parameters parameters;
                parameters = new Parameters();
                if (wall < 0) parameters.add("group_id", -wall);
                URL uri = new URL("https://api.vk.com/method/video.save?access_token="+vk.getAccessToken().getAccessToken());
                LOG.debug("Begin upload video with url: " + file);
                CloseableHttpClient client = HttpClients.createDefault();

                HttpPost httpPost = new HttpPost(uri.toString());
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                for (String name : parameters.getKeys()) {
                    param.add(new BasicNameValuePair(name, parameters.get(name)));
                }
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(param));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String response = EntityUtils.toString(client.execute(httpPost).getEntity(), "UTF-8");

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response);
                JSONObject responseJson = (JSONObject) jsonObject.get("response");
                String uploadUrl = responseJson.get("upload_url").toString();
                uri =  new URL(uploadUrl);

                httpPost = new HttpPost(uri.toString());
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addBinaryBody("video_file", new URL(file).openStream(), ContentType.create("video/mp4"), "video" +
                                ".mp4").build();
                httpPost.setEntity(entity);
                response = EntityUtils.toString(client.execute(httpPost).getEntity(), "UTF-8");

                client.close();
                LOG.debug("RESPONSE FROM FILE SERVER: " + response);
                jsonObject = (JSONObject) parser.parse(response);

                return "video"+responseJson.get("owner_id")+"_"+jsonObject.get("video_id").toString();


            } catch (NullPointerException x) {
                 manyRequest = true;
                x.printStackTrace();
            }
            catch (InterruptedException | IOException | ParseException e) {
                e.printStackTrace();
            }
        } while (manyRequest);
        return null;
    }
}
