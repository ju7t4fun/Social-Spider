package com.epam.lab.spider.job;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.*;


import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.*;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.vk.*;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.util.*;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by shell on 6/16/2015.
 */
public class TaskJob implements Job {
    public static final Logger LOG = Logger.getLogger(TaskJob.class);
    WallService wallService = new WallService();
    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();

    TaskService taskService = new TaskService();

    FilterService filterService = new FilterService();
    PostService postService = new PostService();

    TaskSynchronizedDataService synchronizedService = new TaskSynchronizedDataService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("TaskJob going grab.");
        List<Task> tasks = taskService.getAll();
        for (Task task : tasks) {
            Filter filter = filterService.getById(task.getFilterId());
            List<Wall> sourceWalls = wallService.getSourceByTaskId(task.getId());
            List<Wall> destinationWalls = wallService.getDestinationByTaskId(task.getId());

            LinkedList<Post> addedToProcessingPosts = new LinkedList<>();
            Map<Wall, List<Integer>> blockMap = new HashMap<>();
            for (Wall wall : sourceWalls) {
                Owner owner = ownerService.getById(wall.getOwner_id());
                Profile profile = profileService.getById(wall.getProfile_id());

                Set<Integer> alreadyAddSet = synchronizedService.getProcessedPost(task, wall, 10);
                try {
                    Integer appId = profile.getAppId();
                    if(appId==null){
                        appId = 4949213;
                    }
                    Vkontakte vk = new Vkontakte(appId);
                    // Initialization auth_token
                    AccessToken accessToken = new AccessToken();
                    accessToken.setAccessToken(profile.getAccessToken());
                    accessToken.setUserId(profile.getVkId());
                    accessToken.setExpirationMoment(profile.getExtTime().getTime());
                    vk.setAccessToken(accessToken);
                    // !Initialization auth_token
                    Parameters parameters = new Parameters();
                    parameters.add("owner_id", owner.getVk_id());
                    parameters.add("filter", "owner");
                    parameters.add("count", 10);


                    List<com.epam.lab.spider.model.vk.Post> posts = vk.wall().get(parameters);
                    LinkedList<Integer> addedToProcessingBlocks = new LinkedList<>();
                    for (com.epam.lab.spider.model.vk.Post vkPost : posts) {
                        boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));


                        if (alreadyProceededPost) {
                            LOG.debug("Post " + owner.getVk_id() + "_" + vkPost.getId() + " already processed.");
                        } else {
                            boolean qualityCondition = true;
                            if(filter.getLikes()!=null)     qualityCondition &= vkPost.getLikes().getCount() >= filter.getLikes().intValue();
                            if(filter.getReposts()!=null)   qualityCondition &= vkPost.getReposts().getCount() >= filter.getLikes().intValue();
                            if(filter.getComments()!=null)  qualityCondition &= vkPost.getComments().getCount() >= filter.getComments().intValue();
                            if (qualityCondition) {
                                Post post = new Post();
                                post.setMessage(vkPost.getText());
                                for(com.epam.lab.spider.model.vk.Attachment vkAttachment:vkPost.getAttachments()) {
                                    if (vkAttachment instanceof Photo) {
                                        Attachment attachment = new Attachment();
                                        attachment.setType(Attachment.Type.PHOTO);
                                        attachment.setPayload(((Photo) vkAttachment).getPhoto604().toString());
                                        post.addAttachment(attachment);
                                    }
                                    if(vkAttachment instanceof Audio){
                                        Attachment attachment = new Attachment();
                                        Audio audio = (Audio) vkAttachment;
                                        String attachString= "audio"+ audio.getOwnerId()+"_"+audio.getId();
                                        attachment.setPayload(attachString);
                                        attachment.setMode(Attachment.Mode.CODE);
                                        attachment.setType(Attachment.Type.AUDIO);
                                        post.addAttachment(attachment);
                                    }
                                }

                                addedToProcessingPosts.addFirst(post);
                                addedToProcessingBlocks.addFirst(vkPost.getId());
                                LOG.debug("Post " + owner.getVk_id() + "_" + vkPost.getId() + " has added to processing.");
                            }else {
                                LOG.debug("Post " + owner.getVk_id() + "_" + vkPost.getId() + " has failed filter.");
                            }
                        }
                    }
                    blockMap.put(wall, addedToProcessingBlocks);
                    LOG.info("TaskJob has succesed grab.");
                } catch (Exception x) {
                    x.printStackTrace();
                }

            }
            LinkedList<NewPost> newPosts = new LinkedList<>();
            long timeToPost = System.currentTimeMillis() + 15 * 1000;
            for (Wall wall : destinationWalls) {

                for (Post post : addedToProcessingPosts) {
                    NewPost newPost = new NewPost();
                    newPost.setPost(post);
                    newPost.setWallId(wall.getId());

                    newPost.setPostTime(new Date(timeToPost));
                    timeToPost += 5000;

                    newPost.setState(NewPost.State.CREATED);


                    newPosts.addFirst(newPost);
                }

            }
            NewPostService newPostService = new NewPostService();
            for (NewPost newPost : newPosts) {
                boolean result = SavableServiceUtil.safeSave(newPost);
                if (!result) LOG.fatal("ТЕРМІНОВО ФІКСИТЬ БАЗУ, БРУДНА ТВАРИНО!");
            }
            for (Map.Entry<Wall, List<Integer>> entry : blockMap.entrySet()) {
                synchronizedService.markIdLastProcessedPost(task, entry.getKey(), entry.getValue());
            }
        }
        Date nextRunDate = new Date(System.currentTimeMillis() + (2 * 60 * 1000));
        nextRunDate = new Date(System.currentTimeMillis() + 10000);
        LOG.info("Next run task job at " + nextRunDate.toString());

        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                .startAt(nextRunDate)                          // some Date
                .forJob(jobExecutionContext.getJobDetail()) // identify job with name, group strings
                .build();
        try {
            jobExecutionContext.getScheduler().scheduleJob(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
