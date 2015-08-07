package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.SocialNetworkCredentialsUtils;
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
import com.epam.lab.spider.persistence.service.WallService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzyuba Orest
 */
public class FillPostedPostsCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(EditPostCommand.class);
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static WallService wallService = factory.create(WallService.class);
    String GLOBAL_SEARCH_TERM = "";

    public static List<PostingTask> getStatistics(List<PostingTask> postingTasks, int userId) {
        List<Profile> profiles = factory.create(ProfileService.class).getByUserId(userId);

        Vkontakte vk = new Vkontakte(SocialNetworkCredentialsUtils.getDefaultVkAppsIdAsApps());
        for (Profile profile : profiles) {
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime());
            vk.setAccessToken(accessToken);
            try {
                return vk.execute().getPostStats(postingTasks);
            } catch (VKException e) {
                continue;
            }
        }
        return postingTasks;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject jsonResult = new JSONObject();
        int listDisplayAmount = 5;
        int start = 0;
        String pageNo = request.getParameter("iDisplayStart");
        String pageSize = request.getParameter("iDisplayLength");
        String sortDirection = request.getParameter("sSortDir_0");

        if (pageNo != null) {
            start = Integer.parseInt(pageNo);
            if (start < 0) {
                start = 0;
            }
        }

        if (pageSize != null) {
            try {
                listDisplayAmount = Integer.parseInt(pageSize);
            } catch (Exception e) {
                listDisplayAmount = 5;
            }
            if (listDisplayAmount < 3 || listDisplayAmount > 50) {
                listDisplayAmount = 10;
            }
        }

        if (sortDirection != null) {
            if (!sortDirection.equals("asc"))
                sortDirection = "desc";
        } else {
            sortDirection = "asc";
        }

        int totalRecords = getTotalRecordCount();

        GLOBAL_SEARCH_TERM = request.getParameter("sSearch");

        try {
            jsonResult = getPersonDetails(start, listDisplayAmount, sortDirection, totalRecords, request);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getLocalizedMessage(), e);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
    }

    public JSONObject getPersonDetails(int start, int listDisplayAmount, String dir, int totalRecords,
                                       HttpServletRequest request)
            throws SQLException, ClassNotFoundException {

        int totalAfterSearch = totalRecords;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String searchSQL = "";
        String sql = "SELECT  new_post.vk_post_id AS vk_post_id, new_post.post_id AS post_id , " +
                " new_post.id AS  id, " +
                " new_post.wall_id AS wall_id , " +
                " new_post.post_time AS post_time, " +
                " new_post.state AS state " +
                " FROM  post JOIN new_post ON " +
                " new_post.post_id=post.id  " +
                " AND new_post.deleted=false " +
                "AND new_post.state='POSTED' ";
        String globeSearch = " AND  post.message LIKE '%" + GLOBAL_SEARCH_TERM + "%' ";

        if (!GLOBAL_SEARCH_TERM.isEmpty()) {
            searchSQL = globeSearch;
        }

        sql += searchSQL;
        sql += " limit " + start + ", " + listDisplayAmount;
        List<PostingTask> resList = ServiceFactory.getInstance().create(PostingTaskService.class).getAllWithQuery(sql);

        if (resList != null) {
            try {

                if (dir.equals("asc")) {
                    for (int i = 0; i < resList.size(); ++i) {
                        for (int j = i + 1; j < resList.size(); ++j) {
                            if (resList.get(j).getPostTime().getTime() > resList.get(i).getPostTime().getTime()) {
                                PostingTask temp = resList.get(i);
                                resList.set(i, resList.get(j));
                                resList.set(j, temp);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < resList.size(); ++i) {
                        for (int j = i + 1; j < resList.size(); ++j) {
                            if (resList.get(j).getPostTime().getTime() < resList.get(i).getPostTime().getTime()) {
                                PostingTask temp = resList.get(i);
                                resList.set(i, resList.get(j));
                                resList.set(j, temp);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            String groupNameToGroup = request.getParameter("groupNameToGroup");
            LOG.debug("groupNameToGroup:  " + groupNameToGroup);
            if (groupNameToGroup != null) {

                List<PostingTask> newResList = new ArrayList<>();
                for (PostingTask aResList : resList) {
                    try {
                        WallService wallService = ServiceFactory.getInstance().create(WallService.class);
                        String groupName = wallService.getById(aResList.getWallId()).getOwner().getName();
                        if (groupName.equals(groupNameToGroup)) {
                            newResList.add(aResList);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                resList = newResList;
            }

            resList = getStatistics(resList, ((User) request.getSession().getAttribute("user")).getId());

            for (PostingTask aResList : resList) {
                JSONArray ja = new JSONArray();
                PostingTask currPost = aResList;
                if (currPost != null) {

                    //message
                    try {
                        String msg;
                        if (currPost.getPost().getMessage().length() > 45) {
                            msg = currPost.getPost().getMessage().substring(0, 42) + "...";
                        } else {
                            msg = currPost.getPost().getMessage();
                        }
                        ja.put(msg);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ja.put("No MESSAGE!");
                    }

                    //group name
                    try {
                        WallService wallService = ServiceFactory.getInstance().create(WallService.class);
                        String groupName = wallService.getById(currPost.getWallId()).getOwner().getName();
                        ja.put(groupName);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        LOG.debug(ex.getMessage());
                        ja.put("Empty Wall!");
                    }
                    ja.put(currPost.getFullId());

                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                        ja.put(dateFormat.format(currPost.getPostTime()));
                    } catch (Exception ex) {
                        ja.put("Unknown Time!");
                    }

                    PostingTaskImpl.Stats stats = currPost.getStats();
                    String data = stats.getLikes() + "|" + stats.getRePosts() + "|" + stats.getComments();

                    ja.put(data);
                    ja.put(currPost.getPostId());
                    ja.put(currPost.getId());
                    array.put(ja);
                }
            }
        }
        String query = "SELECT COUNT(*) FROM new_post WHERE deleted=false AND state='POSTED' ";
        //for pagination
        if (!GLOBAL_SEARCH_TERM.isEmpty()) {
            query += searchSQL;
            PostingTaskService npostServ = ServiceFactory.getInstance().create(PostingTaskService.class);
            totalAfterSearch = npostServ.getCountWithQuery(query);
        }
        try {
            result.put("iTotalRecords", totalRecords);
            result.put("iTotalDisplayRecords", totalAfterSearch);
            result.put("aaData", array);
        } catch (Exception e) {

        }
        return result;
    }

    public int getTotalRecordCount() {
        String sql = "SELECT COUNT(*) FROM new_post WHERE deleted=false AND state='POSTED' ";
        PostingTaskService serv = ServiceFactory.getInstance().create(PostingTaskService.class);
        return serv.getCountWithQuery(sql);
    }

}
