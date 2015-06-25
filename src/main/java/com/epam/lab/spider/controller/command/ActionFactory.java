package com.epam.lab.spider.controller.command;

import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 08.06.2015.
 */
public abstract class ActionFactory {

    private static final Logger LOG = Logger.getLogger(ActionFactory.class);
    protected Map<String, ActionCommand> commands = null;

    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }
        ActionCommand command = commands.get(action);
        if (LOG.isDebugEnabled()) {
            Map<String, String> param = new HashMap<>();
            for (Object key : request.getParameterMap().keySet()) {
                param.put(key.toString(), "\"" + UTF8.encoding(request.getParameter(key.toString())) + "\"");
            }
            LOG.debug("Обработка запроса \"" + request.getRequestURI() + "\" {method=" + request.getMethod() + "," +
                    " param=" + param + "}");
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Выполнение команды \"" + command.getClass().getCanonicalName().substring(39) + "\"");
        }

        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            EventLogger logger = EventLogger.getLogger(user.getId());
            logger.info("Выполнение команды", command.getClass().getCanonicalName().substring(39));
        }

        command.execute(request, response);
    }

}
