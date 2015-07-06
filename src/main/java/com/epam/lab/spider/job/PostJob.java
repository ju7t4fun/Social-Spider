package com.epam.lab.spider.job;


import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by shell on 6/14/2015.
 */
public class PostJob implements Job {
    public static final Logger LOG = Logger.getLogger(PostJob.class);
    NewPostService newPostService = new NewPostService();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        Date date = new Date(System.currentTimeMillis());
        Date nextDate = new Date(System.currentTimeMillis() + 60 * 1000);
        LOG.info("'PostJob' start at " + dateFormat.format(date) + " next 'PostJob' at " + dateFormat.format(nextDate));

        List<NewPost> newPosts = newPostService.getAllUnpostedByDate(nextDate);
        for (NewPost newPost : newPosts) {
//            Locker.getInstance().isLock(newPost.get)
            newPost.setState(NewPost.State.POSTING);
            newPostService.updateStage(newPost);
//            SavableServiceUtil.safeSave(newPost);
            JobDetail jobDetail = newJob(OnePostJob.class).usingJobData("new_post_id", newPost.getId()).build();
            SimpleTrigger jobTrigger = (SimpleTrigger) newTrigger()
                    .startAt(newPost.getPostTime())
                    .forJob(jobDetail)
                    .build();
            try {
                jobExecutionContext.getScheduler().scheduleJob(jobDetail, jobTrigger);
            } catch (SchedulerException e) {
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
