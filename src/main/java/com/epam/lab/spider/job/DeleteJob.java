package com.epam.lab.spider.job;

import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.*;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Created by hell-engine on 7/9/2015.
 */
public class DeleteJob implements Job {
    public static final Logger LOG = Logger.getLogger(PostManagerJob.class);
    NewPostService newPostService = new NewPostService();

    WallService wallService = new WallService();
    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        Date date = new Date(System.currentTimeMillis());
        Date nextDate = new Date(System.currentTimeMillis() + 60 * 1000);
        LOG.info("'DeleteJob' start at " + dateFormat.format(date) + " next 'DeleteJob' at " + dateFormat.format(nextDate));
        List<NewPost> postToDelete = newPostService.getAllUndeletedByDate(nextDate);
        for (NewPost postToDeleting : postToDelete) {
            try {
                Wall wall = wallService.getById(postToDeleting.getWallId());
                Profile profile = wall.getProfile();



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
                    //

                Parameters parameters = new Parameters();
                parameters.add("owner_id", wall.getOwner().getVkId());
                parameters.add("post_id", postToDeleting.getVkPostId());
                boolean response = vk.wall().delete(parameters);
                EventLogger eventLogger = EventLogger.getLogger(postToDeleting.getUserId());

                if(response){
                    postToDeleting.setState(NewPost.State.DELETED);
                    newPostService.updateStage(postToDeleting);
                    String info = "Success to delete post" + wall.getOwner().getVkId() + "_" + postToDeleting.getVkPostId();
                    LOG.info(info);
                    eventLogger.info(info, info);
                }else{
                    String error = "Failed to delete post" + wall.getOwner().getVkId() + "_" + postToDeleting.getVkPostId();
                    LOG.error(error);
                    eventLogger.error(error, error);
                }



            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                .startAt(nextDate)
                .forJob(jobExecutionContext.getJobDetail())
                .build();
        try {
            jobExecutionContext.getScheduler().scheduleJob(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
