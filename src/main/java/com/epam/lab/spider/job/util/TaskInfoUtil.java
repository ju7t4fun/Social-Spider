package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.websocket.TaskInfoWebSocket;
import com.epam.lab.spider.model.entity.Task;

import java.util.List;

/**
 * @author Yura Kovalik
 */
public class TaskInfoUtil {
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

    enum TaskInfo {
        RUNNING, RUNNABLE, STOPPED
    }
}
