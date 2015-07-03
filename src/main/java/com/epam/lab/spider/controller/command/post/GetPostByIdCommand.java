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
import java.util.Map;
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
        Set<Attachment> attachmentSet = post.getAttachments();
        System.out.println(attachmentSet);
        String postText = post.getMessage();
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)"); // regex for hastag #tag
        matcher = pattern.matcher(postText);
        while (matcher.find()) {
            String formated = "<span style=\"color:blue\">" + matcher.group() + "</span>";
            postText = postText.replace(matcher.group(), formated);
        }
        postText = postText.replaceAll("\n", "<br>");
        pattern = Pattern.compile("(?i)\\b((?:[a-z][\\w-]+:(?:\\/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}\\/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))"); // url        matcher = pattern.matcher(postText);
        matcher = pattern.matcher(postText);
        while (matcher.find()) {
            String formated = "<a href=\"" + matcher.group() + "\">" + "reference" + "</a>";
            postText = postText.replace(matcher.group(), formated);
        }
        System.out.println(postText);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("postText", postText);
        jsonObject.put("attachments", getUrlForAttachment(attachmentSet));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
    }

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService service = factory.create(ProfileService.class);

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
        List<Profile> profiles = service.getByUserId(1);
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
//                        if (url.contains("http://vk.com/video_ext.php")) {
//                            video.put("type", "vk_video");
//                            video.put("url", url);
//                        }
                        if (url.contains("www.youtube.com")) {
                            video.put("type", "youtube");
                            video.put("url", url.replace("http://www.youtube.com/embed/", ""));
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
