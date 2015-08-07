package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.SocialNetworkCredentialsUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.Vkontakte;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.PostService;
import com.epam.lab.spider.persistence.service.ProfileService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marian Voronovskyi
 */
public class GetPostByIdCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(GetPostByIdCommand.class);
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
        Vkontakte vk = new Vkontakte(SocialNetworkCredentialsUtils.getDefaultVkAppsIdAsApps());
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
                        if (url.contains("http://vk.com/video_ext.php")) {
                            video.put("type", "vk_video");
                            video.put("url", url);
                            attachmentJson.put(video);
                        }
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
                LOG.debug(e.getMessage());
            }
        }
        return attachmentJson;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        int postID = Integer.parseInt(request.getParameter("post_id"));
        PostService postService = new PostService();
        Post post = postService.getById(postID);
        User user = (User) request.getSession().getAttribute("user");
        Set<Attachment> attachmentSet = post.getAttachments();
        LOG.debug(attachmentSet);
        String postText = post.getMessage();
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile("(?i)\\b((?:[a-z][\\w-]+:(?:\\/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}\\/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))"); // url        matcher = pattern.matcher(postText);
        matcher = pattern.matcher(postText);
        Set<String> setUrls = new HashSet<>();
        while (matcher.find()) {
            String url = matcher.group();
            setUrls.add(url);
        }

        for (String url : setUrls) {
            String formatted = "<a target=\"_blank\" href=\"" + (url.contains("http://") ? url : "http://" + url) + "\"> link </a>";
            postText = postText.replace(url, formatted);
        }

        pattern = Pattern.compile("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)"); // regex for hastag #tag
        matcher = pattern.matcher(postText);
        while (matcher.find()) {
            String formatted = "<span style=\"color: blue\">" + matcher.group() + "</span>";
            postText = postText.replace(matcher.group(), formatted);
        }
        postText = postText.replaceAll("\n", "<br>");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("postText", postText);
        jsonObject.put("attachments", getUrlForAttachment(attachmentSet));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
    }
}
