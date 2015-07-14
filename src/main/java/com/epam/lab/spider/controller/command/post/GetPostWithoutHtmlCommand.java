package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Marian Voronovskyi on 06.07.2015.
 */
public class GetPostWithoutHtmlCommand implements ActionCommand {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService service = factory.create(PostService.class);
    private static ProfileService profileService = factory.create(ProfileService.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postID = Integer.parseInt(request.getParameter("post_id"));
        Map<String, String> urlType = new HashMap<>();
        request.getSession().setAttribute("files_url", urlType);
        Post post = service.getById(postID);
        Set<Attachment> attachmentSet = post.getAttachments();
        String postText = post.getMessage();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("postText", postText);
        jsonObject.put("attachID", attachmentSet);
        jsonObject.put("attachments", getUrlForAttachment(attachmentSet));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
    }

    public static JSONArray getUrlForAttachment(Set<Attachment> attachments) {
        JSONArray attachmentJson = new JSONArray();
        List<String> videoId = new ArrayList<>();
        List<String> audioId = new ArrayList<>();
        for (Attachment attachment : attachments) {
            switch (attachment.getType()) {
                case VIDEO:
                    if (attachment.getMode() == Attachment.Mode.CODE)
                        videoId.add(attachment.getPayload());
                    else {
                        JSONObject video = new JSONObject();
                        video.put("type", "video");
                        video.put("url", attachment.getPayload());
                        attachmentJson.put(video);
                    }
                    break;
                case AUDIO:
                    if (attachment.getMode() == Attachment.Mode.CODE)
                        audioId.add(attachment.getPayload());
                    else {
                        JSONObject audio = new JSONObject();
                        audio.put("type", "audio");
                        audio.put("url", attachment.getPayload());
                        attachmentJson.put(audio);
                    }
                    break;
                case PHOTO: {
                    JSONObject photo = new JSONObject();
                    photo.put("type", "photo");
                    photo.put("url", attachment.getPayload());
                    attachmentJson.put(photo);
                }
            }
        }
        Vkontakte vk = new Vkontakte(4949213);
        List<Profile> profiles = profileService.getByUserId(1);
        for (Profile profile : profiles) {
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime());
            vk.setAccessToken(accessToken);
            try {
                // Опрацювання відео
                if (videoId.size() > 0) {
                    Map<String, String> videoPlayer = vk.execute().getVideoPlayer(videoId);
                    for (String id : videoPlayer.keySet()) {
                        JSONObject video = new JSONObject();
                        String url = videoPlayer.get(id);
                        if (url.contains("http://vk.com/video_ext.php")) {
                            video.put("type", "vk_video");
                            video.put("url", url);
                            attachmentJson.put(video);
                        }
                        if (url.contains("www.youtube.com")) {
                            video.put("type", "youtube");
                            video.put("url", url);
                            attachmentJson.put(video);
                        }
                    }
                }
                // Опрацювання аудіо
                if (audioId.size() > 0) {
                    Map<String, String> audioUrl = vk.execute().getAudioUrl(audioId);
                    for (String id : audioUrl.keySet()) {
                        JSONObject audio = new JSONObject();
                        audio.put("type", "audio");
                        audio.put("url", audioUrl.get(id));
                        attachmentJson.put(audio);
                    }
                }
            } catch (VKException e) {
                System.out.print(e.getMessage());
            }
        }
        return attachmentJson;
    }
}
