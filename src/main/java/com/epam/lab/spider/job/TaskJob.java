package com.epam.lab.spider.job;

import com.epam.lab.spider.job.limit.UserLimitProcessor;
import com.epam.lab.spider.job.limit.UserLimitsFactory;
import com.epam.lab.spider.job.util.TaskInfoUtil;
import com.epam.lab.spider.job.util.TaskProcessingUtil;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.persistence.service.TaskService;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Yura Kovalik
 */
public class TaskJob implements Job {
    public static final Logger LOG = Logger.getLogger(TaskJob.class);
    TaskService taskService = new TaskService();

    public static UserLimitProcessor limit = UserLimitsFactory.getUserLimitProcessor();

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
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

}
