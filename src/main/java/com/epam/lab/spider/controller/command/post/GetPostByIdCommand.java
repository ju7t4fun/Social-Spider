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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marian Voronovskyi on 28.06.2015.
 */
public class GetPostByIdCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postID = Integer.parseInt(request.getParameter("post_id"));
        PostService postService = new PostService();
        Post post = null;
        try {
            post = postService.getById(postID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<Attachment> attachmentSet = post.getAttachments();
        String postText = post.getMessage();
        Pattern pattern = Pattern.compile("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)");
        Matcher matcher = pattern.matcher(postText);
        while (matcher.find()) {
            String formated = "<span style=\"color:blue\">" + matcher.group() + "</span>";
            postText = postText.replace(matcher.group(), formated);
        }
        postText = postText.replaceAll("\n","<br><br>");
        JSONArray jsonArray = new JSONArray(attachmentSet);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("postText", postText);
        jsonObject.put("attachments", jsonArray);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println(jsonObject);
        response.getWriter().print(jsonObject);
    }
}
