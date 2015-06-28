package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.NewPost;


import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import com.epam.lab.spider.model.db.service.WallService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

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
        int column = 0;
        String dir = "asc";
        String pageNo = request.getParameter("iDisplayStart");
        String pageSize = request.getParameter("iDisplayLength");
        String colIndex = request.getParameter("iSortCol_0");
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
        if (colIndex != null) {
            column = Integer.parseInt(colIndex);
            if (column < 3 || column > 5)
                column = 3;
        }
        if (sortDirection != null) {
            if (!sortDirection.equals("asc"))
                dir = "desc";
        }

        int totalRecords = getTotalRecordCount();


        GLOBAL_SEARCH_TERM = request.getParameter("sSearch");

        try {
            jsonResult = getPersonDetails(start, listDisplayAmount, column, dir, totalRecords, request);
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

    public JSONObject getPersonDetails(int start, int listDisplayAmount, int column, String dir, int totalRecords, HttpServletRequest request)
            throws SQLException, ClassNotFoundException {

        int totalAfterSearch = totalRecords;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String searchSQL = "";
        String sql = "SELECT  new_post.post_id AS post_id , " +
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


        try {

            if (dir.equals("asc")) {
                for (int i = 0; i < resList.size(); ++i) {
                    for (int j = i + 1; j < resList.size(); ++j) {
                        if (getNumber(column, resList.get(j)) > getNumber(column, resList.get(i))) {
                            NewPost temp = resList.get(i);
                            resList.set(i, resList.get(j));
                            resList.set(j, temp);
                        }
                    }
                }
            } else {
                for (int i = 0; i < resList.size(); ++i) {
                    for (int j = i + 1; j < resList.size(); ++j) {
                        if (getNumber(column, resList.get(j)) < getNumber(column, resList.get(i))) {
                            NewPost temp = resList.get(i);
                            resList.set(i, resList.get(j));
                            resList.set(j, temp);
                        }
                    }
                }
            }
        }
     catch (Exception ex) {
            ex.printStackTrace();
    }

        if (resList != null) {
            for (int i = 0; i < resList.size(); ++i) {
                JSONArray ja = new JSONArray();
                NewPost currPost = resList.get(i);
                //message
                String msg;
                if (currPost.getPost().getMessage().length() > 20) {
                    msg = currPost.getPost().getMessage().substring(0, 19);
                } else {
                    msg = currPost.getPost().getMessage();
                }
                //group name
                ja.put(msg);
                try {
                    WallService wallService = ServiceFactory.getInstance().create(WallService.class);
                    String groupName = wallService.getById(currPost.getWallId()).getOwner().getName();
                    ja.put(groupName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                    ja.put("Empty Wall!");
                }
                //post time
                ja.put(currPost.getPostTime());
                //likes
                ja.put(i + 1);
                //speakers
                ja.put(i + 2);
                //comments
                ja.put(i + 3);
                //post_id
                ja.put(currPost.getPostId());
                array.put(ja);
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

    private int getNumber(int colIndex, NewPost nPost) {
        try {
            if (colIndex == 3) {
                return nPost.getStats().getLikes();
            } else if (colIndex == 4) {
                return nPost.getStats().getReposts();
            } else {
                return nPost.getStats().getComments();
            }
        } catch (Exception ex) {

        /* ex.printStackTrace(); return  0;*/
        return nPost.getPost().getMessage().length();
        }
    }
}
