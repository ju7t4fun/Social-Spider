package com.epam.lab.spider.controller.command.admin;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Marian Voronovskyi on 09.07.2015.
 */
public class AdminAddTaskCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.setAttribute("task_id","0");
        request.setAttribute("posting_type", Task.Type.FAVORITE);
        request.setAttribute("grabbing_type",Task.GrabbingType.NEW);
        request.setAttribute("repeat", Task.Repeat.REPEAT_DISABLE);
        request.setAttribute("repeat_count", "10");

        request.setAttribute("signature", "");
        request.setAttribute("hashtags", "");
        request.setAttribute("start_time",Task.StartTimeType.INTERVAL);
        request.setAttribute("work_time", Task.WorkTimeLimit.ROUND_DAILY);

        request.setAttribute("interval_min", "5");
        request.setAttribute("interval_max", "15");
        request.setAttribute("post_count", "1");
        request.setAttribute("post_delay_min", "40");
        request.setAttribute("post_delay_max", "80");
        request.setAttribute("grabbing_mode", Task.GrabbingMode.TOTAL);

        request.setAttribute("likes", "50");
        request.setAttribute("reposts", "0");
        request.setAttribute("comments", "0");


        request.setAttribute("TEXT","true");
        request.setAttribute("PHOTO","true");
        request.setAttribute("AUDIO","true");
        request.setAttribute("VIDEO","true");
        request.setAttribute("DOCUMENTS","true");
        request.setAttribute("REPOSTS","true");

        request.setAttribute("likes_max","1200");
        request.setAttribute("reposts_max","600");
        request.setAttribute("comments_max","300");
        request.setAttribute("delay_limit","120");
        request.setAttribute("interval_limit","120");

        request.getRequestDispatcher("/jsp/admin/admin_addtask.jsp").forward(request, response);
    }
}
