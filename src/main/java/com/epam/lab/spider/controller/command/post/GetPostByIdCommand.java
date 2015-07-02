package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.vk.Video;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        Post post = postService.getById(postID);
        User user = (User) request.getSession().getAttribute("user");
        Set<Attachment> attachmentSet = getUrlForAttachment(user.getId(), post.getAttachments());
        String postText = post.getMessage();
        Pattern pattern = Pattern.compile("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)");
        Matcher matcher = pattern.matcher(postText);
        while (matcher.find()) {
            String formated = "<span style=\"color:blue\">" + matcher.group() + "</span>";
            postText = postText.replace(matcher.group(), formated);
        }
        postText = postText.replaceAll("\n", "<br>");
        JSONArray jsonArray = new JSONArray(attachmentSet);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("postText", postText);
        jsonObject.put("attachments", jsonArray);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println(jsonObject);
        response.getWriter().print(jsonObject);
    }

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService service = factory.create(ProfileService.class);

    private Set<Attachment> getUrlForAttachment(int userId, Set<Attachment> attachments) {
        List<String> videoId = new ArrayList<>();
        List<String> audioId = new ArrayList<>();
        for (Attachment attachment : attachments) {
            switch (attachment.getType()) {
                case VIDEO:
                    videoId.add(attachment.getPayload());
                    break;
                case AUDIO:
                    audioId.add(attachment.getPayload());
                    break;
            }
        }
        Vkontakte vk = new Vkontakte(4949213);
        List<Profile> profiles = service.getByUserId(userId);
        for (Profile profile : profiles) {
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime());
            vk.setAccessToken(accessToken);
            if (videoId.size() > 0) {
                Parameters param = new Parameters();
                String videos = null;
                for (String id : videoId) {
                    videos = videos == null ? "" + id : videos + "," + id;
                }
                param.add("videos", videos);
                try {
                    List<Video> videoList = vk.video().get(param);
                    System.out.println(videoList);
                } catch (VKException e) {
                    e.printStackTrace();
                }
            }
        }
        return attachments;
    }
}
