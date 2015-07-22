package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.post.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Орест on 23/06/2015.
 */
public class PostServlet extends HttpServlet {

    private static ActionFactory factory = new PostActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class PostActionFactory extends ActionFactory {
        public PostActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowPostsBoardCommand());
            commands.put("get_board", new GetPostsBoardCommand());
//            commands.put("showAll", new ShowAllPostsCommand());
            commands.put("add", new ShowAddNewPostCommand());
            commands.put("addPost", new AddPostCommand());
            commands.put("getCreatedPost", new GetCreatedPostCommand());
            commands.put("remove", new RemovePostCommand());
            commands.put("fillpostedposts", new FillPostedPostsCommand());
            commands.put("fillqueuededposts", new FillQueuededPostsCommand());
            commands.put("created", new ShowCreatedPostCommand());
            commands.put("posted", new ShowPostedPostCommand());
            commands.put("queued", new ShowQueuedPostCommand());
            commands.put("deleteNewPost", new DeleteNewPostCommand());
            commands.put("getPostById", new GetPostByIdCommand());
            commands.put("savePostFromFeed", new SavePostFromFeedCommand());
            commands.put("publishPostId", new PublishPostByIdCommand());
            commands.put("getPostByIdWithoutHtml", new GetPostWithoutHtmlCommand());
            commands.put("editpost", new EditPostCommand());
            commands.put("deleteattach", new DeleteAttachmentCommand());
            commands.put("postStats", new GetStatPostIdCommand());
            commands.put("getPosted", new GetPostedPostCommand());
            commands.put("getQueueded", new GetQueuededPostCommand());
            commands.put("getPostedDate", new GetPostedDateCommand());
            commands.put("changeTime", new ChangeTimePostedCommand());
        }
    }
}
