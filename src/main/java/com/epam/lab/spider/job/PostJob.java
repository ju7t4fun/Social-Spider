package com.epam.lab.spider.job;


import org.quartz.*;

import java.text.DateFormat;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by shell on 6/14/2015.
 */
public class PostJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

//                System.out.println("Executing Job");
        Date date = new Date(System.currentTimeMillis());
        Date nextDate = new Date(System.currentTimeMillis() + 15000);
        System.out.println("Hard Job at " + dateFormat.format(date)+" next hard job at "+dateFormat.format(nextDate));

        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                .startAt(nextDate) // some Date
                .forJob(jobExecutionContext.getJobDetail()) // identify job with name, group strings
                .build();

        try {
            jobExecutionContext.getScheduler().scheduleJob(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
