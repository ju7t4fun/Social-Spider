package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.SocialNetworkUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.Vkontakte;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.PostingTaskService;
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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public class GetPostedPostCommand implements ActionCommand {

    private static final Logger LOG = Logger.getLogger(GetPostedPostCommand.class);

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostingTaskService service = factory.create(PostingTaskService.class);

    public static List<PostingTask> loadingStatistics(List<PostingTask> postingTasks, int userId) {
        if (postingTasks.size() > 0) {
            List<Profile> profiles = factory.create(ProfileService.class).getByUserId(userId);
            Vkontakte vk = new Vkontakte(SocialNetworkUtils.getDefaultVkAppsIdAsApps());
            for (Profile profile : profiles) {
                AccessToken accessToken = new AccessToken();
                accessToken.setAccessToken(profile.getAccessToken());
                accessToken.setUserId(profile.getVkId());
                accessToken.setExpirationMoment(profile.getExtTime());
                vk.setAccessToken(accessToken);
                try {
                    return vk.execute().getPostStats(postingTasks);
                } catch (VKException e) {
                }
            }
        }
        return postingTasks;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int offset = Integer.parseInt(request.getParameter("iDisplayStart"));
        int limit = Integer.parseInt(request.getParameter("iDisplayLength"));

        Integer wallId = null;
        try {
            wallId = service.getById(Integer.parseInt(request.getParameter("postId"))).getWallId();
        } catch (NumberFormatException ignored) {
        }

        List<PostingTask> posts = service.getByUserIdWithParameters(user.getId(), offset, limit, "POSTED",
                request.getParameter("sSearch"), request.getParameter("sSortDir_0"), wallId);
        int postCount = service.getCountAllByUserIdWithParameters(user.getId(), "POSTED", request.getParameter
                ("sSearch"), wallId);

        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();

        posts = loadingStatistics(posts, user.getId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        for (PostingTask post : posts) {
            try {
                JSONArray row = new JSONArray();
                row.put(post.getPost().getMessage().length() > 45 ? post.getPost().getMessage().substring(0, 42) +
                        "..." : post.getPost().getMessage());
                row.put(post.getOwner().getName());
                row.put(post.getFullId());
                row.put(dateFormat.format(post.getPostTime()));
                PostingTaskImpl.Stats stats = post.getStats();
                String data = stats.getLikes() + "|" + stats.getRePosts() + "|" + stats.getComments();
                row.put(data);
                row.put(post.getPostId());
                row.put(post.getId());
                array.put(row);
            } catch (Exception e) {
                LOG.warn("Post id = " + post.getPostId() + " deleted.");
                service.delete(post.getId());
            }
        }
        result.put("iTotalDisplayRecords", postCount);
        result.put("aaData", array);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }
}
