package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.job.util.TaskInfoUtil;
import com.epam.lab.spider.job.util.TaskUtil;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.TaskService;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author Yura Kovalik
 */
public class StateChangeTaskCommand implements ActionCommand {
    public  final static Logger LOG = Logger.getLogger(SaveTaskCommand.class);
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private TaskService taskService = factory.create(TaskService.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        {
            Object userObject = request.getSession().getAttribute("user");
            if(userObject!=null && userObject instanceof User)user = (User) userObject;
            if(user==null) {
                response.sendError(401);
                return;
            }
        }
        try {
            String toStateString = request.getParameter("toState");
            String taskIdString = request.getParameter("taskId");

            Integer index = Integer.parseInt(taskIdString);
            Task.State state = Task.State.valueOf(toStateString.toUpperCase());

            Task task;
            if (user.getRole() == User.Role.ADMIN ) {
                task = taskService.getByIdNoLimit(index);
            } else {
                task = taskService.getByIdAndLimitByUserId(index, user.getId());
            }

            boolean result;
            if(state == Task.State.RUNNING) {
                result = TaskUtil.changeStageToRunning(task);
                TaskInfoUtil.setRunnableTaskInfo(task);
            }else{
                result = TaskUtil.changeStageToStopped(task);
                TaskInfoUtil.setStoppedTaskInfo(task);
            }
            if (!result) throw new RuntimeException("DB FAIL");
        } catch (RuntimeException x) {
            x.printStackTrace();
            response.setStatus(400);
            JSONObject resultJson = new JSONObject();
            resultJson.put("alert","Switch task state failed!");
            resultJson.put("msg","error");
            String result = resultJson.toString();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            return;
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("alert","Switch task succeed!");
        resultJson.put("msg","success");
        String result = resultJson.toString();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
    }
}
