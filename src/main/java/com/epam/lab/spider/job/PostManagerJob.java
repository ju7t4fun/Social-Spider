package com.epam.lab.spider.job;


import com.epam.lab.spider.job.limit.UserLimitProcessor;
import com.epam.lab.spider.job.limit.UserLimitsFactory;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.PostingTaskService;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Yura Kovalik
 */
public class PostManagerJob implements Job {
    public static final Logger LOG = Logger.getLogger(PostManagerJob.class);
    public static UserLimitProcessor limit = UserLimitsFactory.getUserLimitProcessor();
    PostingTaskService postingTaskService = new PostingTaskService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        Date date = new Date(System.currentTimeMillis());
        Date nextDate = new Date(System.currentTimeMillis() + 60 * 1000);
        LOG.info("'PostJob' start at " + dateFormat.format(date) + " next 'PostJob' at " + dateFormat.format(nextDate));

        List<PostingTask> postingTasks = postingTaskService.getAllUnpostedByDate(nextDate);
        for (PostingTask postingTask : postingTasks) {
//            Locker.getInstance().isLock(postingTask.get)
            postingTask.setState(PostingTaskImpl.State.POSTING);
            postingTaskService.updateStage(postingTask);
//            SavableServiceUtil.safeSave(postingTask);
            JobDetail jobDetail = newJob(PostExecutorJob.class).usingJobData("new_post_id", postingTask.getId()).build();
            SimpleTrigger jobTrigger = (SimpleTrigger) newTrigger()
                    .startAt(postingTask.getPostTime())
                    .forJob(jobDetail)
                    .build();
            try {
                jobExecutionContext.getScheduler().scheduleJob(jobDetail, jobTrigger);
            } catch (SchedulerException e) {
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
