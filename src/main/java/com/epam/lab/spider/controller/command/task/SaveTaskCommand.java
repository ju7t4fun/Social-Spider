package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.ReplaceHtmlTags;
import com.epam.lab.spider.job.util.TaskUtil;
import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.TaskService;
import com.epam.lab.spider.persistence.service.WallService;
import com.epam.lab.spider.persistence.service.savable.SavableServiceUtil;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yura Kovalik
 */
public class SaveTaskCommand implements ActionCommand {
    public final static Logger LOG = Logger.getLogger(SaveTaskCommand.class);
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static TaskService taskService = factory.create(TaskService.class);
    private static boolean demoRestriction = true;
    private WallService wallService = factory.create(WallService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object userObject = request.getSession().getAttribute("user");
        User user = null;
        if (userObject != null && userObject instanceof User) user = (User) userObject;
        if (user == null) {
            response.sendError(401);
            return;
        }
        Integer newId;
        String jsonData = request.getParameter("data");
        LOG.debug("json:" + jsonData);
        List<String> warningToDisplay = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            Object obj;
            obj = parser.parse(jsonData);
            JSONObject mainOption = (JSONObject) obj;

            Integer taskIndex = 0;
            try {
                String taskIdString = mainOption.get("taskId").toString();
                taskIndex = Integer.parseInt(taskIdString);

            }catch (RuntimeException x){

            }
            JSONArray jsonSources = (JSONArray) mainOption.get("source");
            JSONArray jsonDestinations = (JSONArray) mainOption.get("destination");
            JSONArray jsonContentType = (JSONArray) mainOption.get("content_type");
            JSONObject jsonOptions = (JSONObject) mainOption.get("options");
            JSONObject jsonFilter = (JSONObject) mainOption.get("filter");

            String type = jsonOptions.get("posting_type").toString();
            String grabbingType = jsonOptions.get("grabbing_type").toString();
            String repeat = jsonOptions.get("repeat").toString();
            String repeatCount = jsonOptions.get("repeat_count").toString();

            String signature = jsonOptions.get("signature").toString();
            String hashtags = jsonOptions.get("hashtags").toString();
            String actionsAfterPosting = jsonOptions.get("actions").toString();
            String startTime = jsonOptions.get("start_time").toString();
            String workTime = jsonOptions.get("work_time").toString();

            signature = ReplaceHtmlTags.reaplaceAll(signature);
            hashtags = ReplaceHtmlTags.reaplaceAll(hashtags);

            String intervalMin = jsonOptions.get("interval_min").toString();
            String intervalMax = jsonOptions.get("interval_max").toString();
            String postCount = jsonOptions.get("post_count").toString();
            String postDelayMin = jsonOptions.get("post_delay_min").toString();
            String postDelayMax = jsonOptions.get("post_delay_max").toString();
            String grabbingMode = jsonOptions.get("grabbing_mode").toString();

            String likes = jsonFilter.get("likes").toString();
            String reposts = jsonFilter.get("reposts").toString();
            String comments = jsonFilter.get("comments").toString();
            String min_time = jsonFilter.get("min_time").toString();
            String max_time = jsonFilter.get("max_time").toString();

            /**
             * id, user_id, filter_id, type, state, deleted, signature, hashtags, content_type, actions_after_posting,
             * start_time_type, work_time_limit, next_task_run, interval_min, interval_max, grabbing_size, post_count,
             * post_delay_min, post_delay_max, grabbing_mode
             */
            Task task;
            if(taskIndex!= 0){
                if (user.getRole() == User.Role.ADMIN) {
                    task = taskService.getByIdNoLimit(taskIndex);
                } else {
                    task = taskService.getByIdAndLimitByUserId(taskIndex, user.getId());
                }
                if(task == null)throw new RuntimeException("User can not get access to foreign this task!");
            }else{
                task = BasicEntityFactory.getSynchronized().createTask();
                task.setState(Task.State.STOPPED);
            }

            Filter filter = task.getFilter();
            if(filter == null) {
                filter = BasicEntityFactory.getSynchronized().createFilter();
            }
            filter.setLikes(Integer.parseInt(likes));
            filter.setRePosts(Integer.parseInt(reposts));
            filter.setComments(Integer.parseInt(comments));
            filter.setMinTime(Long.parseLong(min_time));
            filter.setMaxTime(Long.parseLong(max_time));
            if (filter.getLikes() < 0 && filter.getRePosts() < 0 && filter.getComments() < 0) {
                throw new RuntimeException("Minus Filter value not available!");
            }

            task.setUserId(user.getId());
            task.setFilter(filter);
            task.setType(Task.Type.valueOf(type));
            task.setSignature(signature.trim());

            String[] tags = hashtags.split(",");
            StringBuilder hashTagsStringBuilder = new StringBuilder();
            for (String tag : tags) {
                hashTagsStringBuilder.append("#").append(tag.trim()).append(' ');
            }
            task.setHashTags(hashTagsStringBuilder.toString());

            if (jsonContentType.contains("text")) task.getContentType().addType(Task.ContentType.TEXT);
            if (jsonContentType.contains("photo")) task.getContentType().addType(Task.ContentType.PHOTO);
            if (jsonContentType.contains("audio")) task.getContentType().addType(Task.ContentType.AUDIO);
            if (jsonContentType.contains("video")) task.getContentType().addType(Task.ContentType.VIDEO);
            if (jsonContentType.contains("repost")) task.getContentType().addType(Task.ContentType.RE_POSTS);
            if (jsonContentType.contains("link")) task.getContentType().addType(Task.ContentType.LINKS);
            if (jsonContentType.contains("hashtag")) task.getContentType().addType(Task.ContentType.HASH_TAGS);
            if (jsonContentType.contains("docs")) task.getContentType().addType(Task.ContentType.DOCUMENTS);
            if (jsonContentType.contains("page")) task.getContentType().addType(Task.ContentType.PAGES);
            if (jsonContentType.contains("title")) task.getContentType().addType(Task.ContentType.SIMPLE_TITLE);
            if (jsonContentType.contains("tex_title")) task.getContentType().addType(Task.ContentType.TEXT_TITLE);
            // моде сет
            task.setActionAfterPosting(Task.ActionAfterPosting.valueOf(actionsAfterPosting));
            task.setStartTimeType(Task.StartTimeType.valueOf(startTime));
            task.setWorkTimeLimit(Task.WorkTimeLimit.valueOf(workTime));
            task.setGrabbingType(Task.GrabbingType.valueOf(grabbingType.toUpperCase()));
            task.setGrabbingMode(Task.GrabbingMode.valueOf(grabbingMode.toUpperCase()));
            task.setRepeat(Task.Repeat.valueOf(repeat.toUpperCase()));

            //set field at #3
            task.setGrabbingSize(50);
            task.setPostCount(Integer.parseInt(postCount));
            task.setRepeatCount(Integer.parseInt(repeatCount));
            task.setIntervalMin(Integer.parseInt(intervalMin));
            task.setIntervalMax(Integer.parseInt(intervalMax));
            task.setPostDelayMin(Integer.parseInt(postDelayMin));
            task.setPostDelayMax(Integer.parseInt(postDelayMax));

            if(task.getPostCount()<1 && task.getPostCount() > 10 ){
                throw new RuntimeException("Invalid post count!");
            }
            if (demoRestriction && task.getPostCount() > 2) {
                throw new RuntimeException("Invalid [DEMO-MODE] post count!");
            }
            if(task.getRepeatCount() < 0){
                throw new RuntimeException("Invalid repeat count!");
            }

            if(task.getIntervalMin() < 1 && task.getIntervalMax() < 1  ){
                throw new RuntimeException("Invalid interval count!");
            }
            if (demoRestriction) {
                if(task.getIntervalMin()<3 && task.getIntervalMin()>10 &&
                        task.getIntervalMax()<10 && task.getIntervalMax()>30 ) {
                    throw new RuntimeException("Invalid [DEMO-MODE] interval count!");
                }
            }
            if(task.getPostDelayMin() < 0 && task.getPostDelayMax() < 1 ){
                throw new RuntimeException("Invalid delay count!");
            }
            if (demoRestriction) {
                if(task.getPostDelayMin()<30 && task.getPostDelayMin()>50 &&
                        task.getPostDelayMax()<60 && task.getPostDelayMax()>100 ) {
                    throw new RuntimeException("Invalid [DEMO-MODE] delay count!");
                }
            }

            if(task.getIntervalMin() > task.getIntervalMax()  ){
                Integer min = task.getIntervalMin();
                Integer max = task.getIntervalMax();
                task.setIntervalMax(min);
                task.setIntervalMin(max);
            }

            if(task.getPostDelayMin() > task.getPostDelayMax() ){
                Integer min = task.getPostDelayMin();
                Integer max = task.getPostDelayMax();
                task.setPostDelayMax(min);
                task.setPostDelayMin(max);
            }


            TaskUtil.setNewTaskRunTime(task);

            task.getSource().clear();
            task.getDestination().clear();

            List<String> wallWarning = new ArrayList<>();
            Set<Integer> sourceWallBlock = new HashSet<>();
            for (int i = 0; i < jsonSources.size(); i++) {
                Integer index = Integer.parseInt(jsonSources.get(i).toString());
                sourceWallBlock.add(index);
                Wall wall = wallService.getByIdAndLimitByUser(index, user.getId());
                if (wall != null) task.getSource().add(wall);
                else {
                    wallWarning.add("Impossible wall#" + index + " for this user");
                }
            }
            if (demoRestriction && task.getSource().size() > 3) {
                throw new RuntimeException("Invalid [DEMO-MODE] source wall count!");
            }
            for (int i = 0; i < jsonDestinations.size(); i++) {
                Integer index = Integer.parseInt(jsonDestinations.get(i).toString());
                if (sourceWallBlock.contains(index)) {
                    continue;
                }
                Wall wall = wallService.getByIdAndLimitByUser(index, user.getId());
                if (wall != null) task.getDestination().add(wall);
                else {
                    wallWarning.add("Impossible wall#" + index + " for this user");
                }
            }
            if (demoRestriction && task.getDestination().size() > 3) {
                throw new RuntimeException("Invalid [DEMO-MODE] destination wall count!");
            }
            warningToDisplay.addAll(wallWarning);
            SavableServiceUtil.safeSave(task);
            if (task.getId() == null) {
                response.sendError(400);
                return;
            }else {
                newId = task.getId();
            }


        } catch (ParseException | RuntimeException e) {
            LOG.error(e.getLocalizedMessage(), e);
            response.sendError(400);
            return;
        }

        if (warningToDisplay.size() == 0) {
            JSONObject resultJson = new JSONObject();
            resultJson.put("newId", newId);
            String result = resultJson.toString();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
        } else {
            JSONObject resultJson = new JSONObject();
            JSONArray jsonWarning = new JSONArray();
            for (String warning : warningToDisplay) {
                jsonWarning.add(warning);
            }
            resultJson.put("newId", newId);
            resultJson.put("warning", jsonWarning);
            String result = resultJson.toString();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
        }
    }
}
