package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.SocialNetworkUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Орест on 6/28/2015.
 */
public class FillPostedPostsCommand implements ActionCommand {

    String GLOBAL_SEARCH_TERM = "";

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
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
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

        if (GLOBAL_SEARCH_TERM != "") {
            searchSQL = globeSearch;
        }

        sql += searchSQL;
        sql += " limit " + start + ", " + listDisplayAmount;
        List<NewPost> resList = ServiceFactory.getInstance().create(NewPostService.class).getAllWithQuery(sql);

        if (resList != null) {
            try {

                if (dir.equals("asc")) {
                    for (int i = 0; i < resList.size(); ++i) {
                        for (int j = i + 1; j < resList.size(); ++j) {
                            if (resList.get(j).getPostTime().getTime() > resList.get(i).getPostTime().getTime()) {
                                NewPost temp = resList.get(i);
                                resList.set(i, resList.get(j));
                                resList.set(j, temp);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < resList.size(); ++i) {
                        for (int j = i + 1; j < resList.size(); ++j) {
                            if (resList.get(j).getPostTime().getTime() < resList.get(i).getPostTime().getTime()) {
                                NewPost temp = resList.get(i);
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
            System.out.println("groupNameToGroup:  " + groupNameToGroup);
            if (groupNameToGroup != null) {

                List<NewPost> newResList = new ArrayList<>();
                for (int i = 0; i < resList.size(); ++i) {
                    try {
                        WallService wallService = ServiceFactory.getInstance().create(WallService.class);
                        String groupName = wallService.getById(resList.get(i).getWallId()).getOwner().getName();
                        if (groupName.equals(groupNameToGroup)) {
                            newResList.add(resList.get(i));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                resList = newResList;
            }

            resList = getStatistics(resList, ((User) request.getSession().getAttribute("user")).getId());

            for (int i = 0; i < resList.size(); ++i) {
                JSONArray ja = new JSONArray();
                NewPost currPost = resList.get(i);
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
                        System.out.println(ex.getMessage());
                        ja.put("Empty Wall!");
                    }
                    ja.put(currPost.getFullId());

                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                        ja.put(dateFormat.format(currPost.getPostTime()));
                    } catch (Exception ex) {
                        ja.put("Unknown Time!");
                    }

                    NewPost.Stats stats = currPost.getStats();
                    String data = stats.getLikes() + "|" + stats.getReposts() + "|" + stats.getComments();

                    ja.put(data);
                    ja.put(currPost.getPostId());
                    ja.put(currPost.getId());
                    array.put(ja);
                }
            }
        }
        String query = "SELECT COUNT(*) FROM new_post WHERE deleted=false AND state='POSTED' ";
        //for pagination
        if (GLOBAL_SEARCH_TERM != "") {
            query += searchSQL;
            NewPostService npostServ = ServiceFactory.getInstance().create(NewPostService.class);
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
        NewPostService serv = ServiceFactory.getInstance().create(NewPostService.class);
        int totalRecords = serv.getCountWithQuery(sql);
        return totalRecords;
    }

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static WallService wallService = factory.create(WallService.class);

    public static List<NewPost> getStatistics(List<NewPost> newPosts, int userId) {
        List<Profile> profiles = factory.create(ProfileService.class).getByUserId(userId);

        Vkontakte vk = new Vkontakte(SocialNetworkUtils.getDefaultVkAppsIdAsApps());
        for (Profile profile : profiles) {
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime());
            vk.setAccessToken(accessToken);
            try {
                return vk.execute().getPostStats(newPosts);
            } catch (VKException e) {
                continue;
            }
        }
        return newPosts;
    }

}
