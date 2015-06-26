package com.epam.lab.spider.controller.servlet.owner.ajax;

import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.OwnerService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Орест on 23/06/2015.
 */
public class PostBindingFillingTableServlet extends HttpServlet {
    private String GLOBAL_SEARCH_TERM;
    private String COLUMN_NAME;
    private String DIRECTION;
    private int INITIAL;
    private int RECORD_SIZE;
    private String ID_SEARCH_TERM, NAME_SEARCH_TERM;

    public static int TIMES = 0;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(++TIMES);

        String[] columnNames = {"vk_id", "name"};

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
            listDisplayAmount = Integer.parseInt(pageSize);
            /*if (listDisplayAmount < 10 || listDisplayAmount > 50) {
                listDisplayAmount = 10;
            }*/
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
        COLUMN_NAME = colName;
        DIRECTION = dir;
        INITIAL = start;

        try {
            jsonResult = getGroupDetails(totalRecords, request);
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

    public JSONObject getGroupDetails(int totalRecords, HttpServletRequest request)
            throws SQLException, ClassNotFoundException {

        int totalAfterSearch = totalRecords;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String searchSQL = "";

        ;
        String sql = "SELECT " + "vk_id, name FROM owner ";

        String globeSearch = " WHERE  vk_id LIKE '%" + GLOBAL_SEARCH_TERM + "%'"
                + " OR name LIKE '%" + GLOBAL_SEARCH_TERM + "%'";

        String idSearch = "  WHERE vk_id LIKE " + ID_SEARCH_TERM + "";
        String nameSearch = "  WHERE name like '%" + NAME_SEARCH_TERM + "%'";


        if (GLOBAL_SEARCH_TERM != "") {
            searchSQL = globeSearch;
        } else if (ID_SEARCH_TERM != "") {
            searchSQL = idSearch;
        } else if (NAME_SEARCH_TERM != "") {
            searchSQL = nameSearch;
        }

        sql += searchSQL;
        sql += " order by " + COLUMN_NAME + " " + DIRECTION;
        sql += " limit " + INITIAL + ", " + RECORD_SIZE;
        System.out.println(sql);


        //for searching


        List<Owner> resList = ServiceFactory.getInstance().create(OwnerService.class).getWithQuery(sql);
        if (resList != null) {
            for (int i = 0; i < resList.size(); ++i) {
                JSONArray ja = new JSONArray();
                ja.put(resList.get(i).getVk_id().toString());
                ja.put(resList.get(i).getName());

                String col3 =  "<a href=\"javascript:PopUpShow(" + resList.get(i).getVk_id() + ")\">" +
                        "<button style=\"border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:5px;padding:10px;\">" +
                        "Bind Accounts..." +
                        "</button> </a>";


                ja.put(col3);

                String col4 = "<button style=\"border-radius: 4px;border-color:#424D5F;background-color:#424D5F;" +
                        "color:#ffffff;margin:5px;padding:10px;\">" +
                        "Show Statistic" +
                        "</button>";
                ja.put(col4);
                array.put(ja);
            }
        }

        String query = "SELECT COUNT(*) FROM owner";

        //for pagination
        if ( GLOBAL_SEARCH_TERM != "" || ID_SEARCH_TERM != "" || NAME_SEARCH_TERM != "" ) {
            query += searchSQL;

            OwnerService serv = ServiceFactory.getInstance().create(OwnerService.class);
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

        String sql = "SELECT COUNT(*) FROM  owner";
        OwnerService serv = ServiceFactory.getInstance().create(OwnerService.class);
        int totalRecords = serv.getCountWithQuery(sql);
        System.out.println("Total Records : " + totalRecords + " omg");
//        UserService serv = ServiceFactory.getInstance().create(UserService.class);
//        int totalRecords = serv.getAll().size();
        return totalRecords;
    }
}
