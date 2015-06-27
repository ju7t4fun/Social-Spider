package com.epam.lab.spider.controller.servlet.owner.ajax;

import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.OwnerService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Орест on 6/26/2015.
 */
public class UpdateWallServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception ex) {
        }

        String s = jb.toString();
        JSONObject obj = new JSONObject(s);


        int ownerVkId = obj.getInt("ownerVkId");

        WallService wallService = ServiceFactory.getInstance().create(WallService.class);
        OwnerService oServ = ServiceFactory.getInstance().create(OwnerService.class);
        Owner ow = oServ.getByVkId(ownerVkId);
        int owner_id = ow.getId();


        System.out.println("res : " + obj.getInt("ownerVkId"));

        List<Integer> newSelectedRead = new ArrayList<>();
        List<Integer> newSelectedWrite = new ArrayList<>();

        List<Wall> oldSelectedRead = wallService.getAllByOwnerIdAndPermission(owner_id, Wall.Permission.READ);
        if (oldSelectedRead == null) {
            oldSelectedRead = new ArrayList<>();
        }
        List<Wall> oldSelectedWrite = wallService.getAllByOwnerIdAndPermission(owner_id, Wall.Permission.WRITE);
        if (oldSelectedWrite == null) {
            oldSelectedWrite = new ArrayList<>();
        }


        JSONArray arr = (JSONArray) obj.get("Read");
        if (arr != null) {
            for (int i = 0; i < arr.length(); ++i) {
                newSelectedRead.add(Integer.parseInt(arr.getString(i)));
            }

        }
        arr = (JSONArray) obj.get("Write");
        if (arr != null) {
            for (int i = 0; i < arr.length(); ++i) {
                newSelectedWrite.add(Integer.parseInt(arr.getString(i)));
            }

        }


        //for read
        for (int i = 0; i < newSelectedRead.size(); ++i) {

            if (!containsProfileId(oldSelectedRead, newSelectedRead.get(i))) {
                Wall wall = new Wall();
                wall.setId(243);
                wall.setDeleted(false);
                wall.setOwner_id(owner_id);
                wall.setPermission(Wall.Permission.READ);
                wall.setProfile_id(newSelectedRead.get(i));
                if (wallService.checkExist(wall)) {
                    wallService.updateOnActive(wall.getOwner_id(), wall.getProfile_id(), wall.getPermission());
                } else {

                    wallService.insert(wall);
                }
            }
        }
        for (int i = 0; i < oldSelectedRead.size(); ++i) {
            if (!newSelectedRead.contains(oldSelectedRead.get(i).getProfile_id())) {
                wallService.deleteByOwnerAndProfileIDAndPermission(owner_id,
                        oldSelectedRead.get(i).getProfile_id(), oldSelectedRead.get(i).getId(), oldSelectedRead.get
                                (i).getPermission());
            }
        }

        //for write

        for (int i = 0; i < newSelectedWrite.size(); ++i) {

            if (!containsProfileId(oldSelectedWrite, newSelectedWrite.get(i))) {
                Wall wall = new Wall();
                wall.setId(243);
                wall.setDeleted(false);
                wall.setOwner_id(owner_id);
                wall.setPermission(Wall.Permission.WRITE);
                wall.setProfile_id(newSelectedWrite.get(i));
                if (wallService.checkExist(wall)) {
                    wallService.updateOnActive(wall.getOwner_id(), wall.getProfile_id(), wall.getPermission());
                } else {
                    wallService.insert(wall);
                }
            }
        }
        for (int i = 0; i < oldSelectedWrite.size(); ++i) {

            if (!newSelectedWrite.contains(oldSelectedWrite.get(i).getProfile_id())) {

                wallService.deleteByOwnerAndProfileIDAndPermission(owner_id,
                        oldSelectedWrite.get(i).getProfile_id(), oldSelectedWrite.get(i).getId(), oldSelectedWrite
                                .get(i).getPermission());
            }
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }

    private boolean containsProfileId(List<Wall> walls, int profileID) {

        for (int i = 0; i < walls.size(); ++i) {
            if (walls.get(i).getProfile_id() == profileID) {
                return true;
            }
        }
        return false;
    }
}
