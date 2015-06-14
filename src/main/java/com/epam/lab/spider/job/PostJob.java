package com.epam.lab.spider.job;


import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.entity.NewPost;
import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.model.service.*;
import com.epam.lab.spider.model.vk.App;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by shell on 6/14/2015.
 */
public class PostJob implements Job {
    public static final Logger LOG = Logger.getLogger(PostJob.class);
    NewPostService newPostService = new NewPostService();
    PostService postService = new PostService();
    WallService wallService = new WallService();
    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

//                System.out.println("Executing Job");
        Date date = new Date(System.currentTimeMillis());
        Date nextDate = new Date(System.currentTimeMillis() + 15000);
        System.out.println("Post Job start at " + dateFormat.format(date)+" next hard job at "+dateFormat.format(nextDate));


        List<NewPost> newPosts = newPostService.getAllUnpostedByDate(nextDate);
        for(NewPost newPost:newPosts){
            try{
                newPost.setPost(postService.getById(newPost.getPost().getId()));
                Wall wall = wallService.getById(newPost.getWallId());


                Owner owner = ownerService.getById(wall.getOwner_id());

                Profile profile = profileService.getById(wall.getProfile_id());

                Vkontakte vk = new Vkontakte(4949213);


                // да здраствует безумие!!!!
                AccessToken accessToken = new AccessToken();
                accessToken.setAccessToken(profile.getAccessToken());
                accessToken.setUserId(profile.getVkId());
                accessToken.setExpirationMoment(profile.getExtTime().getTime());
                vk.setAccessToken(accessToken);
                //слава Ктулху!!!


                Parameters parameters = new Parameters();
                parameters.add("owner_id",owner.getVk_id());
                parameters.add("message",newPost.getPost().getMessage());
                long response = vk.wall().post(parameters);

                newPost.setState(NewPost.State.POSTED);
                newPostService.update(newPost.getId(),newPost);


            }catch (NullPointerException|VKException x){
                LOG.error("Posting has failed. Corrupted new_post #" + newPost.getId());
            } 
            System.out.println(newPost.getPost().getMessage());
        }



        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                .startAt(nextDate)                          // some Date
                .forJob(jobExecutionContext.getJobDetail()) // identify job with name, group strings
                .build();

        try {
            jobExecutionContext.getScheduler().scheduleJob(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
