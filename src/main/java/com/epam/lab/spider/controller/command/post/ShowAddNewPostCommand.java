package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.WallService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Marian Voronovskyi
 */
public class ShowAddNewPostCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static WallService service = factory.create(WallService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> urlType = new HashMap<>();
        {
            // Формування дати
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm");
            Date now = new Date();
            String currentDate = date.format(now);
            String currentTime = time.format(now);
            request.setAttribute("date", currentDate);
            request.setAttribute("time", currentTime);

            now = new Date(now.getTime() + 60 * 60 * 1000);
            currentDate = date.format(now);
            currentTime = time.format(now);
            request.setAttribute("del_date", currentDate);
            request.setAttribute("del_time", currentTime);
        }
        {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            List<Wall> writeWalls = service.getWriteByUserId(user.getId());
            List<OwnerWall> ownerWalls = new ArrayList<>();
            for (final Wall wall : writeWalls) {
                ownerWalls.add(new OwnerWall() {
                    @Override
                    public int getWallId() {
                        return wall.getId();
                    }

                    @Override
                    public String getName() {
                        return wall.getOwner().getName();
                    }
                });
            }
            session.setAttribute("owners", ownerWalls);
        }
        request.getSession().setAttribute("files_url", urlType);
        request.getRequestDispatcher("jsp/post/addpost.jsp").forward(request, response);
    }

    public interface OwnerWall {

        int getWallId();
        String getName();

    }
}
