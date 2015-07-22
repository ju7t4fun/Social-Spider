package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hell-engine on 7/18/2015.
 */
public class GetPostsBoardCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(GetPostedPostCommand.class);

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static NewPostService newPostService = factory.create(NewPostService.class);
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
        Integer offset;
        try {
            String offsetString = request.getParameter("offset");
            offset = Integer.parseInt(offsetString);
        }catch (NumberFormatException x){
            offset = 0;
        }catch (RuntimeException x){
            offset = 0;
        }

        List<NewPost> posts = newPostService.getByUserId(user.getId(), offset, 30);


//        List<Map<String,String>> pageArray = new ArrayList<>(30);
        JSONArray pageArray = new JSONArray();

        if (posts != null && !posts.isEmpty()) {
            for (int i = 0; i < posts.size(); i++) {
                NewPost newPost = posts.get(i);
//                Map<String, String> objectMap = new HashMap<>();
                JSONObject objectMap = new JSONObject();
                String message = newPost.getPost().getMessage();
                message = message.replaceAll("%owner%",newPost.getOwner().getDomain()).replaceAll("%owner_name%",newPost.getOwner().getName());
                objectMap.put("pid", newPost.getId().toString());
                objectMap.put("mid", newPost.getPost().getId().toString());
                objectMap.put("message", message);

                Map<Attachment.Type, Integer> attachmentCount = new HashMap<>();
                if(newPost.getPost().getAttachments().isEmpty()){
                    objectMap.put("no_att", "true");
                }else {
                    for (Attachment attachment : newPost.getPost().getAttachments()) {
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
                switch (newPost.getState()) {
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
//                        objectMap.put("likes", "" + newPost.getStats().getLikes());
//                        objectMap.put("shares", "" + newPost.getStats().getReposts());
//                        objectMap.put("comments", "" + newPost.getStats().getComments());
                        break;
                    case DELETED:
                    case ERROR:
                        objectMap.put("state", "hide");
                        break;
                }
                objectMap.put("to_name", newPost.getOwner().getName());
                if (newPost.getState() == NewPost.State.POSTED && newPost.getPostId() != null && newPost.getPostId() != 0) {
                    objectMap.put("to_link", "http://vk.com/wall" + newPost.getFullId());
                } else {
                    objectMap.put("to_link", "http://vk.com/" + newPost.getOwner().getDomain());
                }
                objectMap.put("by_name", newPost.getProfile().getName());
                objectMap.put("by_link", "http://vk.com/id" + newPost.getProfile().getVkId());

                String unitTime = "" + newPost.getPostTime().getTime();
                objectMap.put("unix_time_ms", unitTime);

                pageArray.add(objectMap);
            }
        }


        JSONObject resultFormat = new JSONObject();
        resultFormat.put("tiles",pageArray);


        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(resultFormat);
        out.flush();
        return;
    }
}
