package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.PostingTaskService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yura Kovalik
 */
public class ShowPostsBoardCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(GetPostedPostCommand.class);

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostingTaskService postingTaskService = factory.create(PostingTaskService.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        try {
            user = (User) request.getSession().getAttribute("user");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(401);
            return;
        }


        List<PostingTask> posts = postingTaskService.getByUserId(user.getId(), 0, 30);


        List<Map<String,String>> pageArray = new ArrayList<>(30);

        if (posts != null && !posts.isEmpty()) {
            for (int i = 0; i < posts.size(); i++) {
                try{
                    PostingTask postingTask = posts.get(i);
                    Map<String, String> objectMap = new HashMap<>();

                    String message = postingTask.getPost().getMessage();
                    message = message.replaceAll("%owner%", postingTask.getOwner().getDomain()).replaceAll("%owner_name%", postingTask.getOwner().getName());
                    objectMap.put("pid", postingTask.getId().toString());
                    objectMap.put("mid", postingTask.getPost().getId().toString());
                    objectMap.put("message", message);

                    Map<Attachment.Type, Integer> attachmentCount = new HashMap<>();
                    if (postingTask.getPost().getAttachments().isEmpty()) {
                        objectMap.put("no_att", "true");
                    }else {
                        for (Attachment attachment : postingTask.getPost().getAttachments()) {
                            int count = 0;
                            if (attachmentCount.containsKey(attachment.getType())) {
                                count = attachmentCount.get(attachment.getType());
                            }
                            count++;
                            attachmentCount.put(attachment.getType(), count);
                        }
                        Integer photo = attachmentCount.get(Attachment.Type.PHOTO);
                        Integer audio = attachmentCount.get(Attachment.Type.AUDIO);
                        Integer video = attachmentCount.get(Attachment.Type.VIDEO);
                        Integer docs = attachmentCount.get(Attachment.Type.DOC);
                        Integer other = attachmentCount.get(Attachment.Type.OTHER);
                        if (photo == null) photo = 0;
                        if (audio == null) audio = 0;
                        if (video == null) video = 0;
                        if (docs == null) docs = 0;
                        if (other == null) other = 0;

                        objectMap.put("photo", photo.toString());
                        objectMap.put("audio", audio.toString());
                        objectMap.put("video", video.toString());
                        objectMap.put("docs", docs.toString());
                        objectMap.put("other", other.toString());
                    }
                    switch (postingTask.getState()) {
                        case CREATED:
                        case RESTORED:
                            objectMap.put("state", "queered");
                            break;
                        case POSTING:
                            objectMap.put("state", "posting");
                            break;
                        case POSTED:
                            objectMap.put("state", "posted");
                            objectMap.put("likes", "0");
                            objectMap.put("shares", "0");
                            objectMap.put("comments", "0");
//                        objectMap.put("likes", "" + postingTask.getStats().getLikes());
//                        objectMap.put("shares", "" + postingTask.getStats().getRePosts());
//                        objectMap.put("comments", "" + postingTask.getStats().getComments());
                            break;
                        case DELETED:
                        case ERROR:
                            objectMap.put("state", "hide");
                            break;
                    }
                    objectMap.put("to_name", postingTask.getOwner().getName());
                    if (postingTask.getState() == PostingTaskImpl.State.POSTED && postingTask.getPostId() != null && postingTask.getPostId() != 0) {
                        objectMap.put("to_link", "http://vk.com/wall" + postingTask.getFullId());
                    } else {
                        objectMap.put("to_link", "http://vk.com/" + postingTask.getOwner().getDomain());
                    }
                    objectMap.put("by_name", postingTask.getProfile().getName());
                    objectMap.put("by_link", "http://vk.com/id" + postingTask.getProfile().getVkId());

                    String unitTime = "" + postingTask.getPostTime().getTime();
                    objectMap.put("unix_time_ms", unitTime);

                    pageArray.add(objectMap);
                }catch (NullPointerException x){
                    LOG.error(x.getLocalizedMessage(),x);
                }
            }
        }
        request.setAttribute("posts",pageArray);

        request.getRequestDispatcher("jsp/post/post-list-wood-mark.jsp").forward(request, response);
    }
}
