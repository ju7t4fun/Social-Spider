package com.epam.lab.spider.job;


import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.entity.*;
import com.epam.lab.spider.model.service.*;
import com.epam.lab.spider.model.vk.App;
import org.apache.log4j.Logger;
import org.quartz.*;

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

//                System.out.println("Executing Job");
        Date date = new Date(System.currentTimeMillis());
        Date nextDate = new Date(System.currentTimeMillis() + 50*1000);
        System.out.println("Post Job start at " + dateFormat.format(date)+" next hard job at "+dateFormat.format(nextDate));


        List<NewPost> newPosts = newPostService.getAllUnpostedByDate(nextDate);
        for(NewPost newPost:newPosts){
            JobDetail jobDetail = newJob(OnePostJob.class).usingJobData("new_post_id", newPost.getId()).build();
            SimpleTrigger jobTrigger = (SimpleTrigger) newTrigger()
                    .startAt(newPost.getPostTime())                          // some Date
                    .forJob(jobDetail) // identify job with name, group strings
                    .build();
            try {
                jobExecutionContext.getScheduler().scheduleJob(jobDetail,jobTrigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

        }

        nextDate = new Date(System.currentTimeMillis() + 60*1000);
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
