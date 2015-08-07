package com.epam.lab.spider.controller.command.admin.users;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.UserService;
import org.apache.log4j.Logger;
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
 * @author Dzyuba Orest
 */
public class UserFillingTableCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(UserFillingTableCommand.class);

    private String GLOBAL_SEARCH_TERM;
    private String COLUMN_NAME;
    private String DIRECTION;
    private int INITIAL;
    private int RECORD_SIZE;
    private String ID_SEARCH_TERM, NAME_SEARCH_TERM,
            SURNAME_SEARCH_TERM, EMAIL_SEARCH_TERM, STATE_SEARCH_TERM;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] columnNames = {"id", "name", "surname", "email", "state", "role"};

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
        } catch (ClassNotFoundException | SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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

        String sql = "SELECT " + "id, name, surname, email, state, role FROM user ";

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

        if (!GLOBAL_SEARCH_TERM.isEmpty()) {
            searchSQL = globeSearch;
        } else if (!ID_SEARCH_TERM.isEmpty()) {
            searchSQL = idSearch;
        } else if (!NAME_SEARCH_TERM.isEmpty()) {
            searchSQL = nameSearch;
        } else if (!SURNAME_SEARCH_TERM.isEmpty()) {
            searchSQL = surnameSearch;
        } else if (!EMAIL_SEARCH_TERM.isEmpty()) {
            searchSQL = emailSearch;
        } else if (!STATE_SEARCH_TERM.isEmpty()) {
            searchSQL = stateSearch;
        }

        sql += searchSQL;
        sql += " order by " + COLUMN_NAME + " " + DIRECTION;
        sql += " limit " + INITIAL + ", " + RECORD_SIZE;

        List<User> resList = ServiceFactory.getInstance().create(UserService.class).getWithQuery(sql);
        if (resList != null) {
            for (int i = 0; i < resList.size(); ++i) {
                JSONArray ja = new JSONArray();
                ja.put(resList.get(i).getId().toString());
                ja.put(resList.get(i).getName());
                ja.put(resList.get(i).getSurname());
                ja.put(resList.get(i).getEmail());
                ja.put(resList.get(i).getState().toString());
                ja.put(resList.get(i).getRole());
                array.put(ja);
            }
        }
        String query = "SELECT COUNT(*) FROM user";

        if (!GLOBAL_SEARCH_TERM.isEmpty() || !ID_SEARCH_TERM.isEmpty() || !NAME_SEARCH_TERM.isEmpty() ||
                !SURNAME_SEARCH_TERM.isEmpty() || !EMAIL_SEARCH_TERM.isEmpty() || STATE_SEARCH_TERM.isEmpty()) {
            query += searchSQL;
            UserService userService = ServiceFactory.getInstance().create(UserService.class);
            totalAfterSearch = userService.getCountWithQuery(query);
        }
        try {
            result.put("iTotalRecords", totalRecords);
            result.put("iTotalDisplayRecords", totalAfterSearch);
            result.put("aaData", array);
        } catch (Exception ignored) {
        }
        return result;
    }

    public int getTotalRecordCount() {
        String sql = "SELECT COUNT(*) FROM  user";
        UserService userService = ServiceFactory.getInstance().create(UserService.class);
        return userService.getCountWithQuery(sql);
    }

}
