package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.websocket.TaskInfoWebSocket;
import com.epam.lab.spider.model.db.entity.Task;

import java.util.List;

/**
 * Created by hell-engine on 7/20/2015.
 */
public class TaskInfoUtil {
    enum TaskInfo{
        RUNNING, RUNNABLE, STOPPED
    }
    public static void setRunningTaskInfo(Task task){
        TaskInfoWebSocket.sendTaskInfo(task.getUserId(),task.getId(),"running",null);
    }
    public static void setRunnableTaskInfo(Task task){
        TaskInfoWebSocket.sendTaskInfo(task.getUserId(),task.getId(),"runnable",task.getNextTaskRunDate().getTime());
    }
    public static void setStoppedTaskInfo(Task task){
        TaskInfoWebSocket.sendTaskInfo(task.getUserId(),task.getId(),"stopped",null);
    }

    public static void setRunningTaskInfo(List<Task> tasks){
        for (Task task : tasks) {
            setRunningTaskInfo(task);
        }
    }
    public static void setRunnableTaskInfo(List<Task> tasks){
        for (Task task : tasks) {
            setRunnableTaskInfo(task);
        }
    }
    public static void setStoppedTaskInfo(List<Task> tasks){
        for (Task task : tasks) {
            setStoppedTaskInfo(task);
        }
    }
}
