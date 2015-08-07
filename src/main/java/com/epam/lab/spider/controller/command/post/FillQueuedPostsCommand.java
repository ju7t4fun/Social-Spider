package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.persistence.service.PostingTaskService;
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
import java.util.*;

/**
 * @author Dzyuba Orest
 */
public class FillQueuedPostsCommand implements ActionCommand {

    private static final Logger LOG = Logger.getLogger(FillQueuedPostsCommand.class);
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

        if (sortDirection != null) {
            if (!sortDirection.equals("asc"))
                sortDirection = "desc";
        } else {
            sortDirection = "asc";
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

    public JSONObject getPersonDetails(int start, int listDisplayAmount, String sortDirection, int totalRecords, HttpServletRequest request)
            throws SQLException, ClassNotFoundException {

        int totalAfterSearch = totalRecords;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String searchSQL = "";
        String sql = "SELECT new_post.vk_post_id AS vk_post_id,  new_post.post_id AS post_id , " +
                " new_post.id AS  id, " +
                " new_post.wall_id AS wall_id , " +
                " new_post.post_time AS post_time, " +
                " new_post.state AS state " +
                " FROM  post JOIN new_post ON " +
                " new_post.post_id=post.id  " +
                " AND new_post.deleted=false " +
                "AND ( new_post.state='CREATED' OR new_post.state='ERROR' )";

        String globeSearch = " AND  post.message LIKE '%" + GLOBAL_SEARCH_TERM + "%' ";

        if (!GLOBAL_SEARCH_TERM.isEmpty()) {
            searchSQL = globeSearch;
        }

        sql += searchSQL;
        sql += " limit " + start + ", " + listDisplayAmount;
        List<PostingTask> resList = ServiceFactory.getInstance().create(PostingTaskService.class).getAllWithQuery(sql);


        if (resList != null) {

            for (int i = 0; i < resList.size(); ++i) {
                LOG.debug(resList.get(i).getPostTime());
            }

            try {

                if (sortDirection.equals("asc")) {
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
            for (int i = 0; i < resList.size(); ++i) {
                JSONArray ja = new JSONArray();
                PostingTask currPost = resList.get(i);
                if (currPost != null) {
                    //message

                    try {
                        String msg;
                        if (currPost.getPost() == null) {
                            ja.put("No Post");
                        } else {
                            if (currPost.getPost().getMessage().length() > 20) {
                                msg = currPost.getPost().getMessage().substring(0, 19);
                            } else {
                                msg = currPost.getPost().getMessage();
                            }
                            ja.put(msg);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ja.put("No MESSAGE!!!");
                    }

                    //group name
                    try {
                        WallService wallService = ServiceFactory.getInstance().create(WallService.class);
                        String groupName = wallService.getById(currPost.getWallId()).getOwner().getName();
                        ja.put(groupName);
                    } catch (Exception ex) {
                        LOG.debug(ex.getMessage());
                        ja.put("Empty Wall!");
                        ex.printStackTrace();
                    }

                    try {
                        Set<Attachment> attachments = currPost.getPost().getAttachments();
                        if (attachments != null) {
                            if (attachments.size() > 0) {
                                Map<Attachment.Type, Integer> attachmentCount = new HashMap<>();
                                for (Attachment attachment : attachments) {
                                    int count = 0;
                                    if (attachmentCount.containsKey(attachment.getType())) {
                                        count = attachmentCount.get(attachment.getType());
                                    }
                                    count++;
                                    attachmentCount.put(attachment.getType(), count);
                                }
                                String group = null;
                                for (Attachment.Type type : attachmentCount.keySet()) {
                                    group = group == null ? "" + type + "|" + attachmentCount.get(type) : group + "!" + type +
                                            "|" + attachmentCount.get(type);
                                }
                                ja.put(group);
                            }
                        } else {
                            ja.put("");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ja.put("");
                    }


                    try {
                        ja.put(currPost.getPostTime());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ja.put("Unknown DateTime :(");
                    }

                    ja.put(currPost.getPostId());

                    ja.put(currPost.getId());
                    array.put(ja);
                }
            }
        }
        String query = "SELECT COUNT(*) FROM new_post WHERE deleted=false AND ( state='CREATED' OR state='ERROR' ) ";
        //for pagination
        if (GLOBAL_SEARCH_TERM.isEmpty()) {
            query += searchSQL;
            PostingTaskService npostServ = ServiceFactory.getInstance().create(PostingTaskService.class);
            totalAfterSearch = npostServ.getCountWithQuery(query);
        }
        try {
            result.put("iTotalRecords", totalRecords);
            result.put("iTotalDisplayRecords", totalAfterSearch);
            result.put("aaData", array);
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return result;
    }

    public int getTotalRecordCount() {
        String sql = "SELECT COUNT(*) FROM new_post WHERE deleted=false AND ( state='CREATED' OR state='ERROR' ) ";
        PostingTaskService serv = ServiceFactory.getInstance().create(PostingTaskService.class);
        return serv.getCountWithQuery(sql);
    }
}