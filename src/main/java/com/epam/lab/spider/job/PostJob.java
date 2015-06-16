package com.epam.lab.spider.job;


import com.epam.lab.spider.model.db.service.*;
import org.apache.log4j.Logger;
import org.quartz.*;

/**
 * Created by shell on 6/14/2015.
 */
public class PostJob implements Job {
    public static final Logger LOG = Logger.getLogger(PostJob.class);
    NewPostService newPostService = new NewPostService();
    PostService postService = new PostService();
    WallService wallService = new WallService();
    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        DateFormat dateFormat = DateFormat.getDateTimeInstance();
//
////                System.out.println("Executing Job");
//        Date date = new Date(System.currentTimeMillis());
//
//        Date nextDate = new Date(System.currentTimeMillis() + 15000);
//        System.out.println("Post Job start at " + dateFormat.format(date)+" next hard job at "+dateFormat.format(nextDate));
//
//
//        List<NewPost> newPosts = newPostService.getAllUnpostedByDate(nextDate);
//        for(NewPost newPost:newPosts){
//            try{
//                newPost.setPost(postService.getById(newPost.getPost().getId()));
//                Wall wall = wallService.getById(newPost.getWallId());
//
//
//                Owner owner = ownerService.getById(wall.getOwner_id());
//
//                Profile profile = profileService.getById(wall.getProfile_id());
//
//                Vkontakte vk = new Vkontakte(4949213);
//
//
//                // да здраствует безумие!!!!
//                AccessToken accessToken = new AccessToken();
//                accessToken.setAccessToken(profile.getAccessToken());
//                accessToken.setUserId(profile.getVkId());
//                accessToken.setExpirationMoment(profile.getExtTime().getTime());
//                vk.setAccessToken(accessToken);
//                //слава Ктулху!!!
//
//
//                Parameters parameters = new Parameters();
//                parameters.add("owner_id",owner.getVk_id());
//                parameters.add("message",newPost.getPost().getMessage());
//
//                if(false) {
//                    long response = vk.wall().post(parameters);
//                }
//                PostMetadata metadata = new PostMetadata();
//                metadata.setLike(0);
//                metadata.setRepost(0);
//
//                metadataService.insert(metadata);
//
//                newPost.setMetadata(metadata);
//                newPost.setState(NewPost.State.POSTED);
//                newPostService.update(newPost.getId(),newPost);
//
//                LOG.debug("new post succes : ");
//            }catch (NullPointerException|VKException x){
//                LOG.error("Posting has failed. Corrupted new_post #" + newPost.getId());
//                x.printStackTrace();
//            }
//            System.out.println(newPost.getPost().getMessage());
//        }
//
//        Date nextRunDate = new Date();
//
//
//        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
//                .startAt(nextDate)                          // some Date
//                .forJob(jobExecutionContext.getJobDetail()) // identify job with name, group strings
//                .build();
//
//        try {
//            jobExecutionContext.getScheduler().scheduleJob(trigger);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }

    }
}
