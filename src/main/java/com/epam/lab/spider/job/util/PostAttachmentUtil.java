package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.vk.*;
import com.epam.lab.spider.model.vk.Audio;
import com.epam.lab.spider.model.vk.Photo;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by shell on 6/30/2015.
 * Refactor by shell on 7/6/2015.
 */
public class PostAttachmentUtil {
    public static final Logger LOG = Logger.getLogger(PostAttachmentUtil.class);



    public static String uploadPhoto(Vkontakte vk, String file, Integer wall) throws VKException {
        boolean badExecution = false;
        do {
            try {
                if (badExecution) {
                    Thread.sleep(250);
                    badExecution = false;
                }
                Parameters parameters;
                parameters = new Parameters();
                if (wall < 0) parameters.add("group_id", -wall);
                URL uri = vk.photos().getWallUploadServer(parameters);
                LOG.debug("SERVER URL to upload photo :" + uri);

                LOG.debug("Begin upload photo with url: " + file);

                HttpPost httpPost = new HttpPost(uri.toString());

                HttpEntity entity = MultipartEntityBuilder.create()
                        .addBinaryBody("photo", new URL(file).openStream(), ContentType.create("image/jpeg"), "image" +
                                ".jpg").build();
                httpPost.setEntity(entity);

                JSONObject jsonObject = null;
                Response response = AlphaLimitVKExecutor.getInstance().execute(httpPost, Response.Type.JSON);
                if (response instanceof ResponseJson)
                    jsonObject = ((ResponseJson) response).jsonRoot();
                else {
                    LOG.fatal("Impossible.");
                }
                LOG.debug("RESPONSE FROM FILE SERVER: " + response);


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
                if (x.getExceptionCode() == VKException.VK_PROTOCOL_ERROR) {
                    badExecution = true;
                } else throw x;
            } catch (NullPointerException x){
                badExecution = true;
                x.printStackTrace();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }while (badExecution);
        return null;
    }

    public static String uploadAudio(Vkontakte vk, String file) throws VKException {
        boolean  badExecution = false;
        int attemptCount = 0;
        do {
            attemptCount ++;
            try {
                // filename
                SecureRandom random = new SecureRandom();
                String fileSecureName =  new BigInteger(130, random).toString(32);
                if ( badExecution) {
                    Thread.sleep(250);
                    badExecution = false;
                }
                Parameters parameters;
                URL uri = new URL(vk.audio().getUploadServer());
                LOG.debug("SERVER URL to upload audio :" + uri);
                LOG.debug("Begin upload audio with url: " + file);

                HttpPost httpPost = new HttpPost(uri.toString());
                InputStream inputStream = new URL(file).openStream();
                byte[] audioByteFile = IOUtils.toByteArray(inputStream);
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addBinaryBody("file", audioByteFile, ContentType.create("multipart/form-data"), "audio_"+fileSecureName +
                                ".mp3").build();
                httpPost.setEntity(entity);

                JSONObject jsonObject = null;
                Response response = AlphaLimitVKExecutor.getInstance().execute(httpPost, Response.Type.JSON);
                if(response instanceof  ResponseJson)
                    jsonObject = ((ResponseJson)response).jsonRoot();
                else{
                    LOG.fatal("Impossible.");
                }
                LOG.debug("RESPONSE FROM FILE SERVER: " + response);

                parameters = new Parameters();
                parameters.add("server", jsonObject.get("server").toString());
                parameters.add("audio", jsonObject.get("audio").toString());
                parameters.add("hash", jsonObject.get("hash").toString());
                List<Audio> audios = vk.audio().save(parameters);
                for (Audio audio : audios) {
                    String audioInVk = "audio" + audio.getOwnerId() + "_" + audio.getId();
                    LOG.info("Succeed audio upload. Attempt #"+attemptCount);
                    return audioInVk;
                }
            } catch (VKException x) {
                if(x.getExceptionCode() == VKException.VK_PROTOCOL_ERROR){
                    badExecution = true;
                }else if (x.getExceptionCode() == VKException.VK_AUDIO_HOLDER_WAIN){
                    throw x;
                }else throw x;
            } catch (NullPointerException x){
                badExecution = true;
                x.printStackTrace();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ( badExecution);
        return null;
    }
    public static String uploadVideo(Vkontakte vk, String file, Integer wall) throws VKException {
        boolean badExecution = false;
        do {
            try {
                if (badExecution) {
                    Thread.sleep(250);
                    badExecution = false;
                }

                String groupId;
                String customParameters = "";
                if (wall < 0) {
                    groupId = "group_id" + "=" + -wall;
                    customParameters = groupId + "&";
                }

                URL uri = new URL("https://api.vk.com/method/video.save?" + customParameters + "access_token=" + vk.getAccessToken().getAccessToken());
                LOG.debug("Begin upload video with url: " + file);


                HttpPost httpPost = new HttpPost(uri.toString());

                JSONObject jsonObject = null;
                Response response = AlphaLimitVKExecutor.getInstance().execute(httpPost, Response.Type.JSON);
                if (response instanceof ResponseJson)
                    jsonObject = ((ResponseJson) response).jsonRoot();
                else {
                    LOG.fatal("Impossible.");
                }


                JSONObject responseJson = (JSONObject) jsonObject.get("response");
                String uploadUrl = responseJson.get("upload_url").toString();
                uri = new URL(uploadUrl);

                httpPost = new HttpPost(uri.toString());
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addBinaryBody("video_file", new URL(file).openStream(), ContentType.create("video/mp4"), "video" +
                                ".mp4").build();
                httpPost.setEntity(entity);


                response = AlphaLimitVKExecutor.getInstance().execute(httpPost, Response.Type.JSON);
                if (response instanceof ResponseJson)
                    jsonObject = ((ResponseJson) response).jsonRoot();
                else {
                    LOG.fatal("Impossible.");
                }
                LOG.debug("RESPONSE FROM FILE SERVER: " + response);
                return "video" + responseJson.get("owner_id") + "_" + jsonObject.get("video_id").toString();

            } catch (VKException x){
                if(x.getExceptionCode() == VKException.VK_PROTOCOL_ERROR){
                    badExecution = true;
                }else throw x;
            } catch (NullPointerException x) {
                badExecution = true;
                x.printStackTrace();
            }
            catch (InterruptedException | IOException  e) {
                e.printStackTrace();
            }
        } while ( badExecution );
        return null;
    }
}
