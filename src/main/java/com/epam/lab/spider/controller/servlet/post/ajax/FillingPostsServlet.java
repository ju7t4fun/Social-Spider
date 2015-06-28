package com.epam.lab.spider.controller.servlet.post.ajax;

import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.OwnerService;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Орест on 6/27/2015.
 */
public class FillingPostsServlet extends HttpServlet {


    private int INITIAL;
    private int RECORD_SIZE;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("LOOOOOOOOOOOOOOOOOOL");

        JSONObject jsonResult = new JSONObject();
        int listDisplayAmount = 4;
        int start = 0;

        String pageNo = request.getParameter("iDisplayStart");
        String pageSize = request.getParameter("iDisplayLength");


        if (pageNo != null) {
            start = Integer.parseInt(pageNo);
            if (start < 0) {
                start = 0;
            }
        }
        if (pageSize != null) {
            listDisplayAmount = Integer.parseInt(pageSize);
            /*if (listDisplayAmount < 10 || listDisplayAmount > 50) {
                listDisplayAmount = 10;
            }*/
        }



        RECORD_SIZE = listDisplayAmount;
        INITIAL = start;

        try {
            jsonResult = getGroupDetails(request);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);

    }

    public JSONObject getGroupDetails(HttpServletRequest request)
            throws SQLException, ClassNotFoundException {

        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();


        PostService pServ = ServiceFactory.getInstance().create(PostService.class);
        List<Post> listPosts = pServ.getAllNotInNewPost();

        NewPostService npServ = ServiceFactory.getInstance().create(NewPostService.class);
        List<NewPost> listNewposts = npServ.getAll();

        ArrayList<TempComplexObjForTable> allObjects = new ArrayList<>();

        for (int i = 0; i < listPosts.size(); ++i) {
            TempComplexObjForTable obj = new TempComplexObjForTable();
            //obj.setWallId();
            // obj.setPostTime();
            obj.setMessage(listPosts.get(i).getMessage());
            allObjects.add(obj);
        }

        for (int i = 0; i < listNewposts.size(); ++i) {
            TempComplexObjForTable obj = new TempComplexObjForTable();
            obj.setWallId(listNewposts.get(i).getWallId());
            obj.setPostTime(listNewposts.get(i).getPostTime());
            obj.setMessage(npServ.getMessageByID(listNewposts.get(i).getPostId()));
            allObjects.add(obj);
        }



        ArrayList<TempComplexObjForTable> resList = new ArrayList<>();


        //заповнюю ліст, який передаватиму
        System.out.println("Initial :" + INITIAL);
        for (int i = INITIAL; (i < INITIAL+RECORD_SIZE && i < allObjects.size() ); ++i) {
            resList.add(allObjects.get(i));
        }


        if (resList != null) {
            for (int i = 0; i < resList.size(); ++i) {
                JSONArray ja = new JSONArray();
                ja.put(resList.get(i).getMessage());
                ja.put(resList.get(i).getWallId());
                ja.put(resList.get(i).getPostTime());
                ja.put(resList.get(i).getLikes());
                ja.put(resList.get(i).getSpeakers());
                ja.put(resList.get(i).getCommentss());

                array.put(ja);
            }
        }


        try {
            //ITotalRecords - взагалі всі записи до фільтрації
            //ITotalDisplayRecords - всі записи, які підходять під фільт(в цьому сервлеті я нічого не фільтрую),
            // а не лишень ті, котрі я повертаю
            //оскільки я нічого взагалі не фільтрую, то значення одинакові
            result.put("iTotalRecords", allObjects.size());
            result.put("iTotalDisplayRecords", allObjects.size());
            result.put("aaData", array);
        } catch (Exception e) {

        }

        System.out.println(result);
        return result;
    }


}

class TempComplexObjForTable {
    private String message;
    private Date postTime;
    private Integer wallId;
    private Integer likes;
    private Integer speakers;
    private Integer commentss;

    public TempComplexObjForTable() {
        likes = 3;
        speakers = 4;
        commentss = 5;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date deleteTime) {
        this.postTime = deleteTime;
    }

    public Integer getWallId() {
        return wallId;
    }

    public void setWallId(Integer wallId) {
        this.wallId = wallId;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Integer speakers) {
        this.speakers = speakers;
    }

    public Integer getCommentss() {
        return commentss;
    }

    public void setCommentss(Integer commentss) {
        this.commentss = commentss;
    }
}
