package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.entity.Post;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Marian Voronovskyi on 28.06.2015.
 */
public class GetPostByIdCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postID = request.getParameter("post_id");
        PostService postService = new PostService();
        Post post = null;
        try {
            post = postService.getById(Integer.parseInt(postID));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<Attachment> attachmentSet = post.getAttachments();
        JSONArray jsonArray = new JSONArray(attachmentSet);
        System.out.println(jsonArray);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("postText", post.getMessage());
        jsonObject.put("attachments", jsonArray);
        response.getWriter().print(jsonObject);
    }
}
