package com.epam.lab.spider.job;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.job.util.Locker;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.service.*;

import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.vk.Photo;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shell on 6/16/2015.
 */
public class OnePostJob implements Job {

    public static String uploadPhoto(Vkontakte vk,String file, Integer wall){
        try {
            Parameters parameters;
            parameters = new Parameters();
            if(wall<0)parameters.add("group_id", -wall);
            URL uri = vk.photos().getWallUploadServer(parameters);
            LOG.debug("SERVER URL to upload photo :" + uri);

            LOG.debug("Begin upload photo with url: "+file);
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(uri.toString());
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("photo", new URL(file).openStream(), ContentType.create("image/jpeg"), "image.jpg").build();
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
            if(wall>0)
            parameters.add("user_id", wall);
            else
            parameters.add("group_id",-wall);

            List<Photo> photos = vk.photos().saveWallPhoto(parameters);
            for (Photo photo : photos) {
                String photoInVk = "photo" + photo.getOwnerId() + "_" + photo.getId();
                return photoInVk;
            }

        } catch (VKException x) {
            x.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static final Logger LOG = Logger.getLogger(OnePostJob.class);

    NewPostService newPostService = new NewPostService();
    PostService postService = new PostService();
    WallService wallService = new WallService();
    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();

    AttachmentService attachmentService = new AttachmentService();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        NewPost newPost = null;
        {
            Integer id = dataMap.getInt("new_post_id");
            newPost = newPostService.getById(id);
        }
        if (newPost == null) {
            LOG.error("Quartz failed. Have not new_post_id in DataMap!");
            return;
        }
        if (newPost.getState().equals(NewPost.State.ERROR)) {
            LOG.error("Before posting has been found error. Posting Blocked!");
            return;
        }

        try {
            newPost.setPost(postService.getById(newPost.getPost().getId()));
            Wall wall = wallService.getById(newPost.getWallId());
            Owner owner = ownerService.getById(wall.getOwner_id());
            Profile profile = profileService.getById(wall.getProfile_id());

            try {
                Integer appId = profile.getAppId();
                if (appId == null) {
                    appId = 4949213;
                }
                Vkontakte vk = new Vkontakte(appId);


                // Initialization auth_token
                AccessToken accessToken = new AccessToken();
                accessToken.setAccessToken(profile.getAccessToken());
                accessToken.setUserId(profile.getVkId());
                accessToken.setExpirationMoment(profile.getExtTime().getTime());
                vk.setAccessToken(accessToken);
                // !Initialization auth_token
                List<String> attachmentsList = new ArrayList<>();
                for (Attachment attachment : newPost.getPost().getAttachments()) {
                    if(attachment.getType() == Attachment.Type.PHOTO && attachment.getMode() == Attachment.Mode.URL) {
                        String att = uploadPhoto(vk, attachment.getPayload().toString(), owner.getVk_id());
                        if(att!=null)attachmentsList.add(att);
                    }
                    if(attachment.getType() == Attachment.Type.AUDIO) {
                        String att =  attachment.getPayload();
                        if(att!=null)attachmentsList.add(att);
                    }



                }
                StringBuilder attachmetsStringBuilder = new StringBuilder();
                if (!attachmentsList.isEmpty()) attachmetsStringBuilder.append(attachmentsList.get(0));
                for (int i = 1; i < attachmentsList.size(); i++) {
                    attachmetsStringBuilder.append(", ").append(attachmentsList.get(i));
                }
                LOG.debug("Attachments: " + attachmetsStringBuilder.toString());

                Parameters parameters = new Parameters();
                parameters.add("owner_id", owner.getVk_id());
                parameters.add("attachments", attachmetsStringBuilder.toString());
                parameters.add("message", newPost.getPost().getMessage());
                if(owner.getVk_id()<0){
                    parameters.add("from_group",1);
                }


                long response = 0;
                if (true) {
                    response = vk.wall().post(parameters);
                }

                newPost.setState(NewPost.State.POSTED);
                SavableServiceUtil.safeSave(newPost);

                LOG.debug("new post success : " + owner.getVk_id() + "_" + response);
            } catch (VKException x) {
                switch (x.getExceptionCode()) {
                    case VKException.VK_CAPTCHA_NEEDED: {
                        Locker.getInstance().lock(profile);
                        LOG.error("Posting has failed. Profile is locked.");
                    }
                    break;
                    case VKException.VK_ACCESS_DENIED: {
                        Locker.getInstance().lock(wall);
                        LOG.error("Posting has failed. Wall is locked.");
                    }
                    break;
                    default: {
                        LOG.error("Posting has failed. Corrupted new_post #" + newPost.getId());
                        x.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException x) {
            LOG.error("Posting has failed. Corrupted new_post #" + newPost.getId());
            x.printStackTrace();
        }
    }
}
