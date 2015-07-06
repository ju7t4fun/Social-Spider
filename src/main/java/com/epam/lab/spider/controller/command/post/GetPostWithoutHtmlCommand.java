package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Marian Voronovskyi on 06.07.2015.
 */
public class GetPostWithoutHtmlCommand implements ActionCommand {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService service = factory.create(PostService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postID = Integer.parseInt(request.getParameter("post_id"));
        Post post = service.getById(postID);
        Set<Attachment> attachmentSet = post.getAttachments();
        String postText = post.getMessage();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("postText", postText);
        jsonObject.put("attachments", attachmentSet);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);

    }
}
