package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.db.entity.CategoryHasTask;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.TaskService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Орест on 7/10/2015.
 */
public class BindCommand implements ActionCommand {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static TaskService taskService = factory.create(TaskService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        // Парсимо JSON
        StringBuffer sb = new StringBuffer();
        try {
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject(sb.toString());

        int id = Integer.parseInt(request.getParameter("id"));
        List<CategoryHasTask> chtS = taskService.getAllCHTByTaskId(id);
        JSONArray array = new JSONArray();
        JSONObject obj;

        // Видаляємо
        List<Integer> catIds = getDeletedCHT(chtS, json.getJSONArray("categories"));
        for (Integer catId : catIds) {
            if (taskService.deleteFromCHT(catId, id)) {
                obj = new JSONObject();
                obj.put("status", "success");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.delete.binding.category.success")));
            } else {
                obj = new JSONObject();
                obj.put("status", "error");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.delete.binding.category.error")));
            }
            array.put(obj);
        }
        // Створюємо
        catIds = getCreatedCHT(chtS, json.getJSONArray("categories"));
        Wall wall;
        for (Integer catId : catIds) {

            if (taskService.insertIntoCHT(catId, id)) {
                obj = new JSONObject();
                obj.put("status", "success");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.bind.category.success")));
            } else {
                obj = new JSONObject();
                obj.put("status", "error");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.bind.category.error")));
            }
            array.put(obj);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(array.toString());
    }

    private List<Integer> getCreatedCHT(List<CategoryHasTask> chtS, JSONArray array) {
        List<Integer> created = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            if (!hasCatIdInCHTList(chtS, array.getInt(i)))
                created.add(array.getInt(i));
        }
        return created;
    }

    private boolean hasCatIdInCHTList(List<CategoryHasTask> cthS, Integer id) {
        for (CategoryHasTask cth : cthS) {
            if ( cth.getCategoryId() == id)
                return true;
        }
        return false;
    }
    private List<Integer> getDeletedCHT(List<CategoryHasTask> chtS, JSONArray array) {
        List<Integer> deleted = new ArrayList<>();
        for (CategoryHasTask cht : chtS) {
            if ( !hasJSONArrayId(array,cht.getCategoryId() )) {
                deleted.add(cht.getCategoryId());
            }
        }
        return deleted;
    }

    private boolean hasJSONArrayId(JSONArray array, int id) {
        for (int i = 0; i < array.length(); i++) {
            if (array.getInt(i) == id)
                return true;
        }
        return false;
    }
}
