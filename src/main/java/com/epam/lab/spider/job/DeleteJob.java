package com.epam.lab.spider.job;

import com.epam.lab.spider.SocialNetworkUtils;
import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Vkontakte;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.PostingTaskService;
import com.epam.lab.spider.persistence.service.WallService;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static org.quartz.TriggerBuilder.newTrigger;


/**
 * @author Yura Kovalik
 */
public class DeleteJob implements Job {
    public static final Logger LOG = Logger.getLogger(PostManagerJob.class);
    PostingTaskService postingTaskService = new PostingTaskService();

    WallService wallService = new WallService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        Date date = new Date(System.currentTimeMillis());
        Date nextDate = new Date(System.currentTimeMillis() + 60 * 1000);
        LOG.info("'DeleteJob' start at " + dateFormat.format(date) + " next 'DeleteJob' at " + dateFormat.format(nextDate));
        List<PostingTask> postToDelete = postingTaskService.getAllUndeletedByDate(nextDate);
        for (PostingTask postToDeleting : postToDelete) {
            try {
                Wall wall = wallService.getById(postToDeleting.getWallId());
                Profile profile = wall.getProfile();



                    Integer appId = profile.getAppId();
                    if (appId == null) {
                        appId = SocialNetworkUtils.getDefaultVkAppsIdAsApps();
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
                    postToDeleting.setState(PostingTaskImpl.State.DELETED);
                    postingTaskService.updateStage(postToDeleting);
                    String info = "Success to delete post" + wall.getOwner().getVkId() + "_" + postToDeleting.getVkPostId();
                    LOG.info(info);
                    eventLogger.info(info, info);
                }else{
                    String error = "Failed to delete post" + wall.getOwner().getVkId() + "_" + postToDeleting.getVkPostId();
                    LOG.error(error);
                    eventLogger.error(error, error);
                }



            } catch (Throwable e) {
                LOG.error(e.getLocalizedMessage(), e);
            }
        }
        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                .startAt(nextDate)
                .forJob(jobExecutionContext.getJobDetail())
                .build();
        try {
            jobExecutionContext.getScheduler().scheduleJob(trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }

    }
}
