package com.epam.lab.spider.controller.servlet.admin.ajax;


/**
 * Created by Орест on 22.06.2015.
 */

import java.io.*;
import java.sql.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import org.json.*;

@SuppressWarnings("serial")
public class JqueryDatatablePluginDemoServlet extends HttpServlet {

    private String GLOBAL_SEARCH_TERM;
    private String COLUMN_NAME;
    private String DIRECTION;
    private int INITIAL;
    private int RECORD_SIZE;
    private String ID_SEARCH_TERM, NAME_SEARCH_TERM,
            SURNAME_SEARCH_TERM, EMAIL_SEARCH_TERM, STATE_SEARCH_TERM;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] columnNames = {"id", "name", "surname", "email", "state"};

        JSONObject jsonResult = new JSONObject();
        int listDisplayAmount = 10;
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
            listDisplayAmount = Integer.parseInt(pageSize);
            if (listDisplayAmount < 10 || listDisplayAmount > 50) {
                listDisplayAmount = 10;
            }
        }
        if (colIndex != null) {
            column = Integer.parseInt(colIndex);
            if (column < 0 || column > 5)
                column = 0;
        }
        if (sortDirection != null) {
            if (!sortDirection.equals("asc"))
                dir = "desc";
        }

        String colName = columnNames[column];
        int totalRecords = getTotalRecordCount();


        RECORD_SIZE = listDisplayAmount;
        GLOBAL_SEARCH_TERM = request.getParameter("sSearch");
        ID_SEARCH_TERM = request.getParameter("sSearch_0");
        NAME_SEARCH_TERM = request.getParameter("sSearch_1");
        SURNAME_SEARCH_TERM = request.getParameter("sSearch_2");
        EMAIL_SEARCH_TERM = request.getParameter("sSearch_3");
        STATE_SEARCH_TERM = request.getParameter("sSearch_4");
        COLUMN_NAME = colName;
        DIRECTION = dir;
        INITIAL = start;

        try {
            jsonResult = getPersonDetails(totalRecords, request);
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

    public JSONObject getPersonDetails(int totalRecords, HttpServletRequest request)
            throws SQLException, ClassNotFoundException {

        int totalAfterSearch = totalRecords;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String searchSQL = "";

        ;
        String sql = "SELECT " + "id, name, surname, email, state FROM user ";

        String globeSearch = " WHERE  id LIKE '%" + GLOBAL_SEARCH_TERM + "%'"
                + " OR name LIKE '%" + GLOBAL_SEARCH_TERM + "%'"
                + " OR surname LIKE '%" + GLOBAL_SEARCH_TERM + "%'"
                + " OR email LIKE '%" + GLOBAL_SEARCH_TERM + "%'"
                + " OR state LIKE  '%" + GLOBAL_SEARCH_TERM + "%'";

        String idSearch = "  WHERE id LIKE " + ID_SEARCH_TERM + "";
        String nameSearch = "  WHERE name like '%" + NAME_SEARCH_TERM + "%'";
        String surnameSearch = "  WHERE place like '%" + SURNAME_SEARCH_TERM + "%'";
        String emailSearch = "  WHERE city like '%" + EMAIL_SEARCH_TERM + "%'";
        String stateSearch = " WHERE  state like '%" + STATE_SEARCH_TERM + "%'";

        if (GLOBAL_SEARCH_TERM != "") {
            searchSQL = globeSearch;
        } else if (ID_SEARCH_TERM != "") {
            searchSQL = idSearch;
        } else if (NAME_SEARCH_TERM != "") {
            searchSQL = nameSearch;
        } else if (SURNAME_SEARCH_TERM != "") {
            searchSQL = surnameSearch;
        } else if (EMAIL_SEARCH_TERM != "") {
            searchSQL = emailSearch;
        } else if (STATE_SEARCH_TERM != "") {
            searchSQL = stateSearch;
        }


        sql += searchSQL;
        sql += " order by " + COLUMN_NAME + " " + DIRECTION;
        sql += " limit " + INITIAL + ", " + RECORD_SIZE;
        System.out.println(sql);


        //for searching


        List<User> resList = ServiceFactory.getInstance().create(UserService.class).getWithQuery(sql);
        if (resList != null) {
            for (int i = 0; i < resList.size(); ++i) {
                JSONArray ja = new JSONArray();
                ja.put(resList.get(i).getId().toString());
                ja.put(resList.get(i).getName());
                ja.put(resList.get(i).getSurname());
                ja.put(resList.get(i).getEmail());


                String urlBanned = "<img src=\"/img/banned.jpg\" " +
                        " style=\"width:37px;height:37px;\" " +
                        " id=\"" + resList.get(i).getEmail() + "\" onclick=\"changeImage(this)\" title=\"banned\" >";

                String urlCreated = "<img src=\"/img/created.jpg\" " +
                        " style=\"width:37px;height:37px;\" " +
                        " id=\"" + resList.get(i).getEmail() + "\" onclick=\"changeImage(this)\" title=\"created\" >";
                String urlActivated = "<img src=\"/img/activated.jpg\" " +
                        " style=\"width:37px;height:37px;\" " +
                        " id=\"" + resList.get(i).getEmail() + "\" onclick=\"changeImage(this)\" title=\"activated\" >";


                if (resList.get(i).getState().toString().equals("ACTIVATED")) {
                    ja.put(urlActivated);
                }
                if (resList.get(i).getState().toString().equals("BANNED")) {
                    ja.put(urlBanned);
                }
                if (resList.get(i).getState().toString().equals("CREATED")) {
                    ja.put(urlCreated);
                }

                array.put(ja);
            }
        }

        String query = "SELECT COUNT(*) FROM user";

        //for pagination
        if (GLOBAL_SEARCH_TERM != "" || ID_SEARCH_TERM != "" || NAME_SEARCH_TERM != "" ||
                SURNAME_SEARCH_TERM != "" || EMAIL_SEARCH_TERM != "" || STATE_SEARCH_TERM != "") {
            query += searchSQL;

            UserService serv = ServiceFactory.getInstance().create(UserService.class);
            totalAfterSearch = serv.getCountWithQuery(query);
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

        String sql = "SELECT COUNT(*) FROM  user";
        UserService serv = ServiceFactory.getInstance().create(UserService.class);
        int totalRecords = serv.getCountWithQuery(sql);
        System.out.println("Total Records : " + totalRecords + " omg");
//        UserService serv = ServiceFactory.getInstance().create(UserService.class);
//        int totalRecords = serv.getAll().size();
        return totalRecords;
    }

}
