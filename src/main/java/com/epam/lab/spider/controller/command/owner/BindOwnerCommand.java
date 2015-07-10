package com.epam.lab.spider.controller.command.owner;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;
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
 * Created by Boyarsky Vitaliy on 30.06.2015.
 */
public class BindOwnerCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static WallService service = factory.create(WallService.class);

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
        System.out.println(json);
        int id = Integer.parseInt(request.getParameter("id"));
        List<Wall> walls = service.getByOwnerId(id);
        JSONArray array = new JSONArray();
        JSONObject obj;

        // Видаляємо
        List<Integer> wallIds = getDeletedWall(walls, json.getJSONArray("read"), Wall.Permission.READ);
        wallIds.addAll(getDeletedWall(walls, json.getJSONArray("write"), Wall.Permission.WRITE));
        for (Integer wallId : wallIds) {
            if (service.delete(wallId)) {
                obj = new JSONObject();
                obj.put("status", "success");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.delete.binding.account.success")));
            } else {
                obj = new JSONObject();
                obj.put("status", "error");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.delete.binding.account.error")));
            }
            array.put(obj);
        }
        // Створюємо
        List<Integer> profileIds = getCreatedWall(walls, json.getJSONArray("read"), Wall.Permission.READ);
        Wall wall;
        for (Integer profileId : profileIds) {
            wall = new Wall();
            wall.setOwner_id(id);
            wall.setProfile_id(profileId);
            wall.setPermission(Wall.Permission.READ);
            wall.setDeleted(false);
            if (service.insert(wall)) {
                obj = new JSONObject();
                obj.put("status", "success");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.bind.account.success")));
            } else {
                obj = new JSONObject();
                obj.put("status", "error");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.bind.account.error")));
            }
            array.put(obj);
        }
        profileIds = getCreatedWall(walls, json.getJSONArray("write"), Wall.Permission.WRITE);
        for (Integer profileId : profileIds) {
            wall = new Wall();
            wall.setOwner_id(id);
            wall.setProfile_id(profileId);
            wall.setPermission(Wall.Permission.WRITE);
            wall.setDeleted(false);
            if (service.insert(wall)) {
                obj = new JSONObject();
                obj.put("status", "success");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.bind.account.success")));
            } else {
                obj = new JSONObject();
                obj.put("status", "error");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.bind.account.error")));
            }
            array.put(obj);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(array.toString());
    }

    private List<Integer> getCreatedWall(List<Wall> walls, JSONArray array, Wall.Permission permission) {
        List<Integer> created = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            if (!hasIdInWallList(walls, array.getInt(i), permission))
                created.add(array.getInt(i));
        }
        return created;
    }

    private boolean hasIdInWallList(List<Wall> walls, Integer id, Wall.Permission permission) {
        for (Wall wall : walls) {
            if (wall.getPermission() == permission && wall.getProfile_id() == id)
                return true;
        }
        return false;
    }

    private List<Integer> getDeletedWall(List<Wall> walls, JSONArray array, Wall.Permission permission) {
        List<Integer> deleted = new ArrayList<>();
        for (Wall wall : walls) {
            if (wall.getPermission() == permission && !hasJSONArrayId(array, wall.getProfile_id())) {
                deleted.add(wall.getId());
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
