package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.api.Stats;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 08.07.2015.
 */
public class GetStatPostIdCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static NewPostService service = factory.create(NewPostService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("post_id"));
        NewPost post = service.getById(postId);
        Stats.Reach reach = post.getPostReach();
        NewPost.Stats stats = post.getStats();
        JSONObject json = new JSONObject();
        {
            json.put("likes", stats.getLikes());
            json.put("reposts", stats.getReposts());
            json.put("comments", stats.getComments());

            json.put("reach_subscribers", reach.getReachSubscribers());
            json.put("reach_total", reach.getReachTotal());
            json.put("links", reach.getLinks());
            json.put("to_group", reach.getToGroup());
            json.put("join_group", reach.getJoinGroup());
            json.put("hide", reach.getHide());
            json.put("unsubscribe", reach.getUnsubscribe());
            json.put("report", reach.getReport());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
    }

}
