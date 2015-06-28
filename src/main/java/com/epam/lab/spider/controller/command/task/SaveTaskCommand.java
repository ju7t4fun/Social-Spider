package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Filter;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.*;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
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
import java.util.*;

/**
 * Created by shell on 6/26/2015.
 */
public class SaveTaskCommand implements ActionCommand {
    public  final static Logger LOG = Logger.getLogger(SaveTaskCommand.class);
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private WallService wallService = factory.create(WallService.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object userObject = request.getSession().getAttribute("user");
        User user = null;
        if(userObject!=null && userObject instanceof User)user = (User) userObject;
        if(user==null) {
            response.sendError(401);
            return;
        }
        String jsonData = request.getParameter("data");
        LOG.debug("json:"+jsonData);
        List<String> warningToDisplay = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            Object obj;
            obj = parser.parse(jsonData);
            JSONObject mainOption = (JSONObject) obj;

            JSONArray jsonSources = (JSONArray)mainOption.get("source");
            JSONArray jsonDestinations = (JSONArray)mainOption.get("destination");
            JSONArray jsonContentType = (JSONArray)mainOption.get("content_type");
            JSONObject jsonOptions = (JSONObject)mainOption.get("options");
            JSONObject jsonFilter = (JSONObject)mainOption.get("filter");

            String type =   jsonOptions.get("posting_type").toString();
            String grabbingType =   jsonOptions.get("grabbing_type").toString();
            String repeat =   jsonOptions.get("repeat").toString();
            String repeatCount =   jsonOptions.get("repeat_count").toString();

            String signature      = jsonOptions.get("signature").toString();
            String hashtags =  jsonOptions.get("hashtags").toString();
            String actionsAfterPosting =  jsonOptions.get("actions").toString();
            String startTime =  jsonOptions.get("start_time").toString();
            String workTime =  jsonOptions.get("work_time").toString();


            String intervalMin =   jsonOptions.get("interval_min").toString();
            String intervalMax =   jsonOptions.get("interval_max").toString();
            String postCount =     jsonOptions.get("post_count").toString();
            String postDelayMin =  jsonOptions.get("post_delay_min").toString();
            String postDelayMax =  jsonOptions.get("post_delay_max").toString();
            String grabbingMode =   jsonOptions.get("grabbing_mode").toString();

            String likes    =  jsonFilter.get("likes").toString();
            String reposts  = jsonFilter.get("reposts").toString();
            String comments = jsonFilter.get("comments").toString();
            String min_time =  jsonFilter.get("min_time").toString();
            String max_time = jsonFilter.get("max_time").toString();

            /**
             * id, user_id, filter_id, type, state, deleted, signature, hashtags, content_type, actions_after_posting,
             * start_time_type, work_time_limit, next_task_run, interval_min, interval_max, grabbing_size, post_count,
             * post_delay_min, post_delay_max, grabbing_mode
             */

            Filter filter = new Filter();
            filter.setLikes(Integer.parseInt(likes));
            filter.setReposts(Integer.parseInt(reposts));
            filter.setComments(Integer.parseInt(comments));
            filter.setMinTime(Long.parseLong(min_time));
            filter.setMaxTime(Long.parseLong(max_time));

            Task task = new Task();
            task.setState(Task.State.RUNNING);
            task.setUserId(user.getId());
            task.setFilter(filter);
            task.setType(Task.Type.valueOf(type));
            task.setSignature(signature.trim());

            String[] tags = hashtags.split(",");
            StringBuilder hashTagsStringBuilder = new StringBuilder();
            for(String tag:tags){
                hashTagsStringBuilder.append("#").append(tag.trim()).append(' ');
            }
            task.setHashTags(hashTagsStringBuilder.toString());

            if(jsonContentType.contains("text"))task.getContentType().addType(Task.ContentType.TEXT);
            if(jsonContentType.contains("photo"))task.getContentType().addType(Task.ContentType.PHOTO);
            if(jsonContentType.contains("audio"))task.getContentType().addType(Task.ContentType.AUDIO);
            if(jsonContentType.contains("video"))task.getContentType().addType(Task.ContentType.VIDEO);
            if(jsonContentType.contains("repost"))task.getContentType().addType(Task.ContentType.REPOSTS);
            if(jsonContentType.contains("link"))task.getContentType().addType(Task.ContentType.LINKS);
            if(jsonContentType.contains("hashtag"))task.getContentType().addType(Task.ContentType.HASHTAGS);
            if(jsonContentType.contains("docs"))task.getContentType().addType(Task.ContentType.DOCUMENTS);
            if(jsonContentType.contains("page"))task.getContentType().addType(Task.ContentType.PAGES);


            task.setActionAfterPosting(Task.ActionAfterPosting.valueOf(actionsAfterPosting));
            task.setStartTimeType(Task.StartTimeType.valueOf(startTime));
            task.setWorkTimeLimit(Task.WorkTimeLimit.valueOf(workTime));
            task.setGrabbingType(Task.GrabbingType.valueOf(grabbingType.toUpperCase()));
            task.setNextTaskRunDate(new Date(System.currentTimeMillis()+task.getIntervalMin()));
            //set field at #3
            task.setIntervalMin( Integer.parseInt(intervalMin));
            task.setIntervalMax( Integer.parseInt(intervalMax));
            task.setPostCount(  Integer.parseInt(postCount));
            task.setPostDelayMin(Integer.parseInt(postDelayMin));
            task.setPostDelayMax(Integer.parseInt(postDelayMax));
            task.setGrabbingMode( Task.GrabbingMode.valueOf(grabbingMode.toUpperCase()));


            List<String> wallWarning = new ArrayList<>();
            Set<Integer> sourceWallBlock = new HashSet<>();
            for (int i = 0; i < jsonSources.size(); i++) {
                Integer index = Integer.parseInt(jsonSources.get(i).toString());
                sourceWallBlock.add(index);
                Wall wall = wallService.getByIdAndLimitByUser(index, user.getId());
                if(wall!=null)task.getSource().add(wall);
                else {
                    wallWarning.add("Impossible wall#"+index+" for this user");
                }
            }
            for (int i = 0; i < jsonDestinations.size(); i++) {
                Integer index = Integer.parseInt(jsonDestinations.get(i).toString());
                if(sourceWallBlock.contains(index)){
                    continue;
                }
                Wall wall = wallService.getByIdAndLimitByUser(index, user.getId());
                if(wall!=null)task.getDestination().add(wall);
                else {
                    wallWarning.add("Impossible wall#"+index+" for this user");
                }
            }
            warningToDisplay.addAll(wallWarning);
            SavableServiceUtil.safeSave(task);
            if(task.getId()==null){
                response.sendError(400);
                return;
            }


        } catch (ParseException | RuntimeException e) {
            e.printStackTrace();
            response.sendError(400);
            return;
        }

        if(warningToDisplay.size()==0){
            response.setStatus(201);
            return;
        }else{
            JSONObject resultJson = new JSONObject();
            JSONArray jsonWarning = new JSONArray();
            for(String warning:warningToDisplay){
                jsonWarning.add(warning);
            }
            resultJson.put("warning",jsonWarning);
            String result = resultJson.toString();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            return;
        }
    }
}
