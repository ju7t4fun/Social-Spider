package com.epam.lab.spider.job;

import com.epam.lab.spider.job.limit.UserLimit;
import com.epam.lab.spider.job.limit.UserLimitsFactory;
import com.epam.lab.spider.job.util.*;
import com.epam.lab.spider.model.db.entity.*;


import com.epam.lab.spider.model.db.service.*;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by shell on 6/16/2015.
 */
public class TaskJob implements Job {
    public static final Logger LOG = Logger.getLogger(TaskJob.class);
    TaskService taskService = new TaskService();

    public static UserLimit limit = UserLimitsFactory.getUserLimit();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        LOG.info(" TaskJob start processing at " + new Date(startTime).toString());
        Date nextRunDate = new Date(System.currentTimeMillis() + (60 * 1000));
        List<Task> tasks = taskService.getRunnableByNextRunDate(nextRunDate);
        // відображаємо новий стан - в обробціz
        TaskInfoUtil.setRunningTaskInfo(tasks);

        for (Task task : tasks) {
            if(limit.checkTaskExecute(task.getUserId()) && TaskProcessingUtil.processingTask(task)){
                limit.markTaskExecute(task.getUserId());
            }
        }
        // відображаємо новий стан - в черзі
        TaskInfoUtil.setRunnableTaskInfo(tasks);

        long finishTime = System.currentTimeMillis();
        float workTimeInSecond = (finishTime - startTime) / 1000.f;

        NumberFormat formatter = new DecimalFormat("#0.000");
        LOG.info("TaskJob has finish. Work time " + formatter.format(workTimeInSecond) + "s");
        LOG.info("Next run 'TaskJob' at " + nextRunDate.toString());

        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                .startAt(nextRunDate)                          // some Date
                .forJob(jobExecutionContext.getJobDetail()) // identify job with name, group strings
                .build();
        try {
            jobExecutionContext.getScheduler().scheduleJob(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
