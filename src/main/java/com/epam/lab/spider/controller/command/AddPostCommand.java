package com.epam.lab.spider.controller.command;

import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Marian Voronovskyi on 25.06.2015.
 */
public class AddPostCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> urlType = (Map<String, String>) request.getSession().getAttribute("files_url");
        PostService postService = new PostService();
        String message = request.getParameter("message");
        String title = request.getParameter("title");
        String tags = request.getParameter("tags");
        String text = title + "\n" + message + "\n" + tags;
        Post post = new Post();
        Attachment attachment;
        Set<Attachment> attachments = new HashSet<>();

        for (Map.Entry<String, String> entry : urlType.entrySet()) {
            attachment = new Attachment();
            attachment.setPayload(entry.getKey());
            attachment.setType(Attachment.Type.valueOf(entry.getValue()));
            attachment.setDeleted(false);
            attachments.add(attachment);
        }
        post.setMessage(text);
        post.setDeleted(false);
        post.setAttachments(attachments);
        postService.insert(post);

        request.getSession().removeAttribute("files_url");
        request.getRequestDispatcher("jsp/post/addpost.jsp").forward(request, response);
    }
}
