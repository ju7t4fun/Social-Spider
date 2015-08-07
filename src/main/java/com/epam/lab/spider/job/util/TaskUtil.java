package com.epam.lab.spider.job.util;

import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.TaskService;

import java.util.Date;
import java.util.Random;

/**
 * @author Yura Kovalik
 */
public class TaskUtil {
    private static TaskService taskService = ServiceFactory.getInstance().create(TaskService.class);
    private static Random random = new Random();

    /**
     * @param task Task as parameter source
     * @return time in second between task.postDelayMin and task.postDelayMax
     */
    public static int getRandomPostDelay(Task task) {
        return random.nextInt(task.getPostDelayMax()-task.getPostDelayMin()+1) + task.getPostDelayMin();
    }
    /**
     * @param task Task as parameter source
     * @return time in second between task.intervalMin and task.intervalMax
     */
    public static int getRandomTaskRunDelay(Task task) {
        return random.nextInt((task.getIntervalMax()-task.getIntervalMin())*60+1) + task.getIntervalMin()*60;
    }

    public static boolean setNewTaskRunTimeAndUpdate(Task task){
        setNewTaskRunTime(task);
        return taskService.updateTimeToNextRun(task);
    }
    public static void setNewTaskRunTime(Task task){
        if(task.getStartTimeType() == Task.StartTimeType.INTERVAL && task.getWorkTimeLimit() == Task.WorkTimeLimit.ROUND_DAILY) {
            int delay = TaskUtil.getRandomTaskRunDelay(task);
            task.setNextTaskRunDate(new Date(System.currentTimeMillis() + delay * 1000));
        }else{
            // TODO REFACTOR THIS
            int delay = TaskUtil.getRandomTaskRunDelay(task);
            task.setNextTaskRunDate(new Date(System.currentTimeMillis() + delay * 1000));
        }
    }
    public static boolean changeStageToRunning(Task task){
        setNewTaskRunTime(task);
        task.setState(Task.State.RUNNING);
        return taskService.updateNextTimeRunAndState(task);
    }

    public static boolean changeStageToStopped(Task task){
        task.setState(Task.State.STOPPED);
        return taskService.updateState(task);
    }
}
