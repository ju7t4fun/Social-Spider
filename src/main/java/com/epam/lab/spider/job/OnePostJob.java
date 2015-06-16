package com.epam.lab.spider.job;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.job.util.Locker;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.service.*;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by shell on 6/16/2015.
 */
public class OnePostJob implements Job {
    public static final Logger LOG = Logger.getLogger(OnePostJob.class);

    NewPostService newPostService = new NewPostService();
    PostService postService = new PostService();
    WallService wallService = new WallService();
    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();


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


                Parameters parameters = new Parameters();
                parameters.add("owner_id", owner.getVk_id());
                parameters.add("message", newPost.getPost().getMessage());
                long response = 0;
                if (true) {
                    response = vk.wall().post(parameters);
                }

                newPost.setState(NewPost.State.POSTED);
                newPostService.update(newPost.getId(), newPost);

                LOG.debug("new post success : " + owner.getVk_id() + "_" + response);
            } catch (VKException x) {
                switch (x.getExceptionCode()){
                    case VKException.VK_CAPTCHA_NEEDED:{
                        Locker.getInstance().lock(profile);
                        LOG.error("Posting has failed. Profile is locked.");
                    }break;
                    case VKException.VK_ACCESS_DENIED:{
                        Locker.getInstance().lock(wall);
                        LOG.error("Posting has failed. Wall is locked.");
                    }break;
                    default:{
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
