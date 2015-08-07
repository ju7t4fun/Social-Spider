package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.persistence.service.PostingTaskService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Boyarsky Vitaliy
 */
public class GetPostedDateCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostingTaskService service = factory.create(PostingTaskService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("post_id"));
        PostingTask post = service.getById(postId);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        JSONObject json = new JSONObject();
        {
            json.put("now", date.format(new Date()));
            json.put("date", date.format(post.getPostTime()));
            json.put("time", time.format(post.getPostTime()));
            Date deleteTime = post.getDeleteTime();
            if (deleteTime == null) {
                Date now = new Date(post.getPostTime().getTime() + 60 * 60 * 1000);
                json.put("del_date", date.format(now));
                json.put("del_time", time.format(now));
            } else {
                json.put("del_date", date.format(deleteTime));
                json.put("del_time", time.format(deleteTime));
            }
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
    }
}
