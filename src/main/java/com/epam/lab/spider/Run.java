package com.epam.lab.spider;

import com.epam.lab.spider.model.entity.NewPost;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.PostMetadata;
import com.epam.lab.spider.model.service.NewPostService;
import com.epam.lab.spider.model.service.PostMetadataService;
import com.epam.lab.spider.model.service.ServiceFactory;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws SQLException {
        ServiceFactory factory = ServiceFactory.getInstance();
        NewPostService postService = factory.create(NewPostService.class);
        Post post = new Post();
        post.setMessage("asadasd");
        NewPost nPost = new NewPost();
        nPost.setPost(post);
        nPost.setWallId(3);
        Date date = new Date();
        System.out.println(date);
        nPost.setPostTime(new Date());
        postService.insert(nPost);

    }

}
