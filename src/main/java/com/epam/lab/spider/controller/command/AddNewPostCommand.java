package com.epam.lab.spider.controller.command;

import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.service.NewPostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.epam.lab.spider.model.db.entity.Post;

/**
 * Created by Marian Voronovskyi on 26.06.2015.
 */
public class AddNewPostCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String dateDelete = request.getParameter("date_delete");
        String timeDelete = request.getParameter("time_delete");
        String groups = request.getParameter("groups");

        Date postDate = new Date();
        Date deleteDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            postDate = formatter.parse(date + " " + time);
            deleteDate = formatter.parse(dateDelete + " " + timeDelete);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("=====ADD NEW POST=====");
        System.out.println("DATE: " + date);
        System.out.println("TIME: " + time);
        System.out.println("TIME DELETE: " + timeDelete);
        System.out.println("GROUPS: " + groups);
        try {
            NewPostService newPostService = new NewPostService();
            NewPost newPost = new NewPost();
            newPost.setState(NewPost.State.CREATED);
            newPost.setDeleted(false);
            newPost.setDeleteTime(deleteDate);
            newPost.setPostTime(postDate);
            newPost.setPostId(29);
            newPost.setWallId(1);
            newPostService.insert(newPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
