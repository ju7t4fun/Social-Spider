package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.SocialNetworkUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.vk.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Boyarsky Vitaliy on 07.07.2015.
 */
public class AddManuallyAccountCommand implements ActionCommand {

    private static final Integer APP_ID = SocialNetworkUtils.getDefaultVkAppsIdAsApps();
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService service = factory.create(ProfileService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        com.epam.lab.spider.model.db.entity.User user = (com.epam.lab.spider.model.db.entity.User) session
                .getAttribute("user");
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        JSONObject json = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Парсим параметри
        AccessToken token = new AccessToken();
        try {
            String[] requestParam = request.getParameter("href").split("#")[1].split("&");
            Map<String, String> params = new HashMap<>();
            for (String param : requestParam) {
                String[] map = param.split("=");
                params.put(map[0], map[1]);
            }
            token.setAccessToken(params.get("access_token"));
            token.setExpirationMoment(Long.parseLong(params.get("expires_in")));
            token.setUserId(Integer.parseInt(params.get("user_id")));
        } catch (Exception e) {
            json.put("status", "warning");
            json.put("msg", "Incorrect query!");
            response.getWriter().print(json.toString());
            return;
        }

        // Створюємо профіль
        Profile profile = new Profile();
        profile.setVkId(token.getUserId());
        profile.setAccessToken(token.getAccessToken());
        profile.setExtTime(token.getExpirationMoment());
        profile.setUserId(user.getId());
        try {
            Vkontakte vk = new Vkontakte(APP_ID);
            Parameters param = new Parameters();
            param.add("user_ids", token.getUserId());
            User u = vk.users().get(param).get(0);
            profile.setName(u.getFirstName() + " " + u.getLastName());
        } catch (VKException e) {
            profile.setName("NO NAME");
        }
        profile.setAppId(APP_ID);


        // Перевіряємо чи такий профіль існує в базі
        Profile oldProfile = service.getByVkId(token.getUserId());
        if (oldProfile == null) {
            if (service.insert(profile)) {
                json.put("status", "success");
                json.put("msg", "Successfully created!");
            } else {
                json.put("status", "error");
                json.put("msg", "Error has occured!");
            }
        } else {
            if (service.update(oldProfile.getId(), profile)) {
                json.put("status", "success");
                json.put("msg", "Successfulle updated!");
            } else {
                json.put("status", "error");
                json.put("msg", "Error has occured!");
            }
        }
        response.getWriter().print(json.toString());
    }

}
