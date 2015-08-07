package com.epam.lab.spider.controller.command.owner;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.Vkontakte;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.persistence.service.ProfileService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.WallService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boyarsky Vitaliy
 */
public class OptionFillingCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(OptionFillingCommand.class);

    private static Vkontakte vk = new Vkontakte();
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService profileService = factory.create(ProfileService.class);
    private static WallService wallService = factory.create(WallService.class);

    private static Map<Integer, String> userNamesPool = new HashMap<>();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        User user = (User) session.getAttribute("user");
        List<Profile> profiles = profileService.getByUserId(user.getId());
        List<Wall> walls = wallService.getByOwnerId(id);
        JSONObject result = new JSONObject();
        result.put("read", filtrationByPermission(walls, profiles, Wall.Permission.READ));
        result.put("write", filtrationByPermission(walls, profiles, Wall.Permission.WRITE));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }

    private JSONArray filtrationByPermission(List<Wall> walls, List<Profile> profiles, Wall.Permission permission) {
        JSONArray array = new JSONArray();
        for (Profile profile : profiles) {
            JSONObject p = new JSONObject();
            p.put("name", getUserNameByVkId(profile.getVkId()));
            p.put("id", profile.getId());
            if (hasWallProfile(walls, profile.getId(), permission))
                p.put("select", true);
            else
                p.put("select", false);
            array.put(p);
        }
        return array;
    }

    private boolean hasWallProfile(List<Wall> walls, int profileId, Wall.Permission permission) {
        for (Wall wall : walls) {
            if (wall.getProfileId() == profileId && wall.getPermission() == permission) {
                return true;
            }
        }
        return false;
    }

    private String getUserNameByVkId(int id) {
        if (userNamesPool.containsKey(id))
            return userNamesPool.get(id);
        Parameters param = new Parameters();
        param.add("user_ids", id);
        try {
            com.epam.lab.spider.model.vk.User user = vk.users().get(param).get(0);
            String name = user.getFirstName() + " " + user.getLastName();
            userNamesPool.put(id, name);
            return name;
        } catch (VKException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return "DELETED";
    }
}
