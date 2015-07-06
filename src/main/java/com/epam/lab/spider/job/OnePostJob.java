package com.epam.lab.spider.job;

import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Request;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.job.util.Locker;
import com.epam.lab.spider.job.util.PostAttachmentUtil;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.service.*;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shell on 6/16/2015.
 */
public class OnePostJob implements Job {

    public static final Logger LOG = Logger.getLogger(OnePostJob.class);
    NewPostService newPostService = new NewPostService();
    PostService postService = new PostService();
    WallService wallService = new WallService();
    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();
    AttachmentService attachmentService = new AttachmentService();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        NewPost newPost = null;
        {
            Integer id = dataMap.getInt("new_post_id");
            newPost = newPostService.getById(id);
        }
        if (newPost == null) {
            LOG.error("Quartz failed. Have not new_post_id in DataMap!");
            return;
        }
        if (newPost.getState().equals(NewPost.State.ERROR)) {
            LOG.error("Before posting has been found error. Posting Blocked!");
            return;
        }

        try {
            Wall wall = wallService.getById(newPost.getWallId());
            // Додаткова перевірка чи немає локу для цільвого обєкту
            // Якщо виконуєть вивід фатал повідомлення - існують проблеми
            // З архітектурою сервісу
            if (Locker.getInstance().isLock(wall)) {
                LOG.fatal("На заблоковану стіну зафіксована спроба виконати пост. Пост буде заблоковано! Проблема в " +
                        "архітектурі сервісу!");
                newPost.setState(NewPost.State.ERROR);
                SavableServiceUtil.safeSave(newPost);
                return;
            }
            Owner owner = ownerService.getById(wall.getOwner_id());
            Profile profile = profileService.getById(wall.getProfile_id());

            newPost.setPost(postService.getById(newPost.getPost().getId()));
            try {
                Integer appId = profile.getAppId();
                if (appId == null) {
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
                List<String> attachmentsList = new ArrayList<>();
                for (Attachment attachment : newPost.getPost().getAttachments()) {
                    if (attachment.getType() == Attachment.Type.PHOTO && attachment.getMode() == Attachment.Mode.URL) {
                        String att = PostAttachmentUtil.uploadPhoto(vk, attachment.getPayload().toString(), owner.getVkId());
                        if (att != null) attachmentsList.add(att);
                    }
                    if (attachment.getType() == Attachment.Type.PHOTO && attachment.getMode() == Attachment.Mode.CODE) {
                        String att = attachment.getPayload();
                        if (att != null) attachmentsList.add(att);
                    }
                    if (attachment.getType() == Attachment.Type.AUDIO && attachment.getMode() == Attachment.Mode.URL) {
                        String att = null;
                        try {
                            att = PostAttachmentUtil.uploadAudio(vk, attachment.getPayload().toString());
                        }catch (VKException x){
                            if (x.getExceptionCode() == VKException.VK_AUDIO_HOLDER_WAIN) {
                                EventLogger eventLogger = EventLogger.getLogger(wall.getProfile().getUserId());
                                // TODO переписати так, щоб в тест повідомлення записувалось до якого поста не вдалось завантажити аудіо
                                eventLogger.warn("Audio Cannot Upload", x.getMessage());
                            }else throw x;
                        }
                        if (att != null) attachmentsList.add(att);
                    }
                    if (attachment.getType() == Attachment.Type.AUDIO && attachment.getMode() == Attachment.Mode.CODE) {
                        String att = attachment.getPayload();
                        if (att != null) attachmentsList.add(att);
                    }
                    if (attachment.getType() == Attachment.Type.VIDEO && attachment.getMode() == Attachment.Mode.URL) {
                        String att;

                        att = PostAttachmentUtil.uploadVideo(vk, attachment.getPayload().toString(), owner.getVkId());
                        if (att != null) attachmentsList.add(att);
                    }
                    if (attachment.getType() == Attachment.Type.VIDEO && attachment.getMode() == Attachment.Mode.CODE) {
                        String att = attachment.getPayload();
                        if (att != null) attachmentsList.add(att);
                    }
                    if (attachment.getType() == Attachment.Type.DOC && attachment.getMode() == Attachment.Mode.URL) {
                        LOG.error("METhOD UNSUPPORTED!");
                    }
                    if (attachment.getType() == Attachment.Type.DOC && attachment.getMode() == Attachment.Mode.CODE) {
                        String att = attachment.getPayload();
                        if (att != null) attachmentsList.add(att);
                    }


                }
                StringBuilder attachmentsStringBuilder = new StringBuilder();
                if (!attachmentsList.isEmpty()) attachmentsStringBuilder.append(attachmentsList.get(0));
                for (int i = 1; i < attachmentsList.size(); i++) {
                    attachmentsStringBuilder.append(", ").append(attachmentsList.get(i));
                }
                LOG.debug("Attachments: " + attachmentsStringBuilder.toString());

                Parameters parameters = new Parameters();
                parameters.add("owner_id", owner.getVkId());
                parameters.add("attachments", attachmentsStringBuilder.toString());
                parameters.add("message", newPost.getPost().getMessage());
                parameters.setRequestMethod(Request.Method.POST);
                if (owner.getVkId() < 0) {
                    parameters.add("from_group", 1);
                }
                Long response = null;
                if (true) {
                    boolean manyRequest = false;

                    do {
                        try {
                            if (manyRequest) {
                                Thread.sleep(400);
                                manyRequest = false;
                            }
                            response = vk.wall().post(parameters);
                        } catch (VKException x) {
                            if (x.getExceptionCode() == VKException.VK_MANY_REQUESTS) manyRequest = true;
                            else throw x;
                        }
                        // фікс кривої архітектури
                        // перехоплення NullPointerException by UnknownHostException
                        catch (NullPointerException x){
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (manyRequest);
                }

                newPost.setState(NewPost.State.POSTED);
                newPost.setVkPostId(Integer.parseInt(response.toString()));
                SavableServiceUtil.safeSave(newPost);

                LOG.debug("new post success : " + owner.getVkId() + "_" + response);
                return;
            } catch (VKException x) {
                switch (x.getExceptionCode()) {
                    case VKException.VK_CAPTCHA_NEEDED: {
                        Locker.getInstance().lock(profile, DataLock.Mode.CAPTCHA);
                        LOG.error("Posting has failed. Profile is locked. Captcha input need!");
                    }
                    break;
                    case VKException.VK_AUTHORIZATION_FAILED: {
                        Locker.getInstance().lock(profile, DataLock.Mode.AUTH_KEY);
                        LOG.error("Posting has failed. Profile is locked. Authorized to vk needed! ");
                    }
                    break;
                    case VKException.VK_ACCESS_DENIED: {
                        Locker.getInstance().lock(wall, DataLock.Mode.ACCESS_DENY);
                        LOG.error("Posting has failed. Wall is locked.");
                    }
                    break;
                    default: {
                        LOG.error("Posting has failed. Corrupted new_post #" + newPost.getId(), x);
                        x.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException x) {
            LOG.error("Posting has failed. Nullable object has founded. Corrupted new_post #" + newPost.getId());
            x.printStackTrace();
        } catch (Throwable t){
            LOG.error(t);
        }
        newPost.setState(NewPost.State.ERROR);
        newPostService.updateStage(newPost);
    }
}
