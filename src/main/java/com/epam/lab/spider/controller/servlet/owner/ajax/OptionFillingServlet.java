package com.epam.lab.spider.controller.servlet.owner.ajax;

import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import com.epam.lab.spider.model.db.service.WallService;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orest Dzyuba on 25.06.2015.
 */
public class OptionFillingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int owner_id = Integer.parseInt(request.getParameter("ownerID"));
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();
        out.print(getJsonObj(owner_id));
    }

    private JSONObject getJsonObj( int owner_id) {

        ProfileService profServ = ServiceFactory.getInstance().create(ProfileService.class);

        List<Profile> selectedProfiles =  profServ.getProfilesInWall(owner_id);
        List<Profile> profiles = profServ.getProfilesNotInWall(owner_id);

        List<Profile> profilesRead = new ArrayList<>();
        List<Profile> profilesWrite = new ArrayList<>();
        List<Profile> selectedProfilesRead = new ArrayList<>();
        List<Profile> selectedProfilesWrite = new ArrayList<>();

        profilesRead.addAll(profiles);
        profilesWrite.addAll(profiles);

        WallService wallServ = ServiceFactory.getInstance().create(WallService.class);
        List<Wall> walls;
         for(int i = 0; i < selectedProfiles.size(); ++i) {
            walls = wallServ.getAllByProfileID(selectedProfiles.get(i).getId());
             boolean toAddToRead = true;
             boolean toAddToWrite = true;
             if (walls!=null) {
                 for(int j = 0; j < walls.size(); ++j) {
                     if (walls.get(j).getPermission() == Wall.Permission.READ ){
                         toAddToRead = false;
                     }
                     if (walls.get(j).getPermission() == Wall.Permission.WRITE ){
                         toAddToWrite = false;
                     }
                 }
             }
             if (toAddToRead) {
                 profilesRead.add(selectedProfiles.get(i));
             }
             if (toAddToWrite) {
                 profilesWrite.add(selectedProfiles.get(i));
             }
        }
        for(int i = 0; i < selectedProfiles.size(); ++i) {
            if ( !profilesRead.contains(selectedProfiles.get(i))) {
                selectedProfilesRead.add(selectedProfiles.get(i));
            }
            if ( !profilesWrite.contains(selectedProfiles.get(i))) {
                selectedProfilesWrite.add(selectedProfiles.get(i));
            }
        }

        JSONObject mainObj = new JSONObject();
        JSONArray ja = new JSONArray();

        try {
            for(int i = 0; i < selectedProfilesRead.size(); ++i) {
                JSONObject jo = new JSONObject();
                jo.put("id", selectedProfilesRead.get(i).getId());
                ja.put(jo);
            }
            mainObj.put("SelectedProfilesRead",ja);

            ja = new JSONArray();
            for(int i = 0; i < selectedProfilesWrite.size(); ++i) {
                JSONObject jo = new JSONObject();
                jo.put("id", selectedProfilesWrite.get(i).getId());
                ja.put(jo);
            }
            mainObj.put("SelectedProfilesWrite",ja);


            ja = new JSONArray();
            for(int i = 0; i < profilesRead.size(); ++i) {
                JSONObject jo = new JSONObject();
                jo.put("id", profilesRead.get(i).getId());
                ja.put(jo);
            }
            mainObj.put("ProfilesRead",ja);

            ja = new JSONArray();
            for(int i = 0; i < profilesWrite.size(); ++i) {
                JSONObject jo = new JSONObject();
                jo.put("id", profilesWrite.get(i).getId());
                ja.put(jo);
            }
            mainObj.put("ProfilesWrite",ja);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(mainObj);
        return  mainObj;
    }
}
