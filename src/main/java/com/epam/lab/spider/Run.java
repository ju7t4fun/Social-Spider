package com.epam.lab.spider;


import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Request;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.job.util.PostAttachmentUtil;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.service.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {
    public static final Logger LOG = Logger.getLogger(Run.class);

    public static void main(String[] args) throws VKException {
//        TaskSynchronizedNewDataService taskSynchronizedNewDataService = new TaskSynchronizedNewDataService();
////        taskSynchronizedNewDataService.createTableIfNotExist();
//
//        Vkontakte vk = new Vkontakte(4949213);
//        AccessToken token = new AccessToken();
//        token.setAccessToken("2c19ba70d24d0cfb18b20438ffdba37e038c1366e563f44d508580764dbe33e01a9ecc16e0ea5e0f2c452");
//        token.setUserId(1);
//        token.setExpirationMoment(new Date());
//        vk.setAccessToken(token);
//
//        vk.execute().getPostFromBeginWall(16697463,5, null,null,null);

//        Parameters param = new Parameters();
//        param.add("owner_id","-24056415");
//        param.add("post_id","21770");
//        Stats.Reach reach = vk.stats().getPostReach(param);
//        System.out.println(reach.getReachSubscribers());

//        NewPostService newPostService = new NewPostService();
//        PostService postService = new PostService();
//        WallService wallService = new WallService();
//        OwnerService ownerService = new OwnerService();
//        ProfileService profileService = new ProfileService();
//        AttachmentService attachmentService = new AttachmentService();
//        NewPost newPost = newPostService.getById(134);
//        try {
//            Wall wall = wallService.getById(newPost.getWallId());
//            Owner owner = ownerService.getById(wall.getOwner_id());
//            Profile profile = profileService.getById(wall.getProfile_id());
//
//            newPost.setPost(postService.getById(newPost.getPost().getId()));
//            try {
//                Vkontakte vk = new Vkontakte(4949213);
//
//
//                // Initialization auth_token
//                AccessToken accessToken = new AccessToken();
//                accessToken.setAccessToken(profile.getAccessToken());
//                accessToken.setUserId(profile.getVkId());
//                accessToken.setExpirationMoment(profile.getExtTime().getTime());
//                vk.setAccessToken(accessToken);
//                // !Initialization auth_token
//                List<String> attachmentsList = new ArrayList<>();
//                for (Attachment attachment : newPost.getPost().getAttachments()) {
//                    if (attachment.getType() == Attachment.Type.PHOTO && attachment.getMode() == Attachment.Mode.URL) {
//                        String att = PostAttachmentUtil.uploadPhoto(vk, attachment.getPayload().toString(), owner.getVkId());
//                        if (att != null) attachmentsList.add(att);
//                    }
//                    if (attachment.getType() == Attachment.Type.PHOTO && attachment.getMode() == Attachment.Mode.CODE) {
//                        String att = attachment.getPayload();
//                        if (att != null) attachmentsList.add(att);
//                    }
//                    if (attachment.getType() == Attachment.Type.AUDIO && attachment.getMode() == Attachment.Mode.URL) {
//                        String att = null;
//                        try {
//                            att = PostAttachmentUtil.uploadAudio(vk, attachment.getPayload().toString());
//                        }catch (VKException x){
//                            if (x.getExceptionCode() == VKException.VK_AUDIO_HOLDER_WAIN) {
//                                EventLogger eventLogger = EventLogger.getLogger(wall.getProfile().getUserId());
//                                eventLogger.warn("Audio Cannot Upload", x.getMessage());
//                            }else throw x;
//                        }
//                        if (att != null) attachmentsList.add(att);
//                    }
//                    if (attachment.getType() == Attachment.Type.AUDIO && attachment.getMode() == Attachment.Mode.CODE) {
//                        String att = attachment.getPayload();
//                        if (att != null) attachmentsList.add(att);
//                    }
//                    if (attachment.getType() == Attachment.Type.VIDEO && attachment.getMode() == Attachment.Mode.URL) {
//                        String att;
//
//                        att = PostAttachmentUtil.uploadVideo(vk, attachment.getPayload().toString(), owner.getVkId());
//                        if (att != null) attachmentsList.add(att);
//                    }
//                    if (attachment.getType() == Attachment.Type.VIDEO && attachment.getMode() == Attachment.Mode.CODE) {
//                        String att = attachment.getPayload();
//                        if (att != null) attachmentsList.add(att);
//                    }
//                    if (attachment.getType() == Attachment.Type.DOC && attachment.getMode() == Attachment.Mode.URL) {
//                        LOG.error("METhOD UNSUPPORTED!");
//                    }
//                    if (attachment.getType() == Attachment.Type.DOC && attachment.getMode() == Attachment.Mode.CODE) {
//                        String att = attachment.getPayload();
//                        if (att != null) attachmentsList.add(att);
//                    }
//
//
//                }
//                StringBuilder attachmentsStringBuilder = new StringBuilder();
//                if (!attachmentsList.isEmpty()) attachmentsStringBuilder.append(attachmentsList.get(0));
//                for (int i = 1; i < attachmentsList.size(); i++) {
//                    attachmentsStringBuilder.append(", ").append(attachmentsList.get(i));
//                }
//                LOG.debug("Attachments: " + attachmentsStringBuilder.toString());
//
//                Parameters parameters = new Parameters();
//                parameters.add("owner_id", owner.getVkId());
//                parameters.add("attachments", attachmentsStringBuilder.toString());
//                parameters.add("message", newPost.getPost().getMessage());
//                parameters.setRequestMethod(Request.Method.POST);
//                if (owner.getVkId() < 0) {
//                    parameters.add("from_group", 1);
//                }
//                Long response = null;
//                if (true) {
//                    boolean manyRequest = false;
//
//                    do {
//                        try {
//                            if (manyRequest) {
//                                Thread.sleep(400);
//                                manyRequest = false;
//                            }
//                            response = vk.wall().post(parameters);
//                        } catch (VKException x) {
//                            if (x.getExceptionCode() == VKException.VK_MANY_REQUESTS) manyRequest = true;
//                            else throw x;
//                        }
//                        // фікс кривої архітектури
//                        // перехоплення NullPointerException by UnknownHostException
//                        catch (NullPointerException x){
//                            try {
//                                Thread.sleep(500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } while (manyRequest);
//                }
//
//
//                LOG.debug("new post success : " + owner.getVkId() + "_" + response);
//                return;
//            } catch (VKException x) {
//
//                        LOG.error("Posting has failed. Corrupted new_post #" + newPost.getId(), x);
//                        x.printStackTrace();
//
//            }
//        } catch (NullPointerException x) {
//            LOG.error("Posting has failed. Nullable object has founded. Corrupted new_post #" + newPost.getId());
//            x.printStackTrace();
//        } catch (Throwable t){
//            LOG.error(t);
//        }
    }

}

