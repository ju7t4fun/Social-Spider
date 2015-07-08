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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Marian Voronovskyi on 06.07.2015.
 */
public class EditPostCommand implements ActionCommand {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService service = factory.create(PostService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postID = Integer.parseInt(request.getParameter("post_id"));
        Map<String, String> urlType = (Map<String, String>) request.getSession().getAttribute("files_url");
        Attachment attachment;
        Set<Attachment> attachments = new HashSet<>();
        for (Map.Entry<String, String> entry : urlType.entrySet()) {
            attachment = new Attachment();
            attachment.setPayload("http://localhost:8080" + entry.getKey());
            attachment.setType(Attachment.Type.valueOf(entry.getValue()));
            attachment.setDeleted(false);
            attachment.setPostId(postID);
            attachments.add(attachment);
        }
        String postText = request.getParameter("text");
        JSONObject jsonObject = new JSONObject();

        Post post = service.getById(postID);
        post.setMessage(postText);
        post.getAttachments().addAll(attachments);
        System.out.println(post);
        if (service.update(postID, post)) {
            jsonObject.put("status", "success");
            jsonObject.put("message", "Post has been successfully edited!");
        } else {
            jsonObject.put("status", "error");
            jsonObject.put("message", "Error!");
        }
        request.getSession().removeAttribute("files_url");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
    }
}
