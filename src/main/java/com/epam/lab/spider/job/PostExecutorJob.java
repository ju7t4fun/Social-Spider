package com.epam.lab.spider.job;

import com.epam.lab.spider.SocialNetworkUtils;
import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Request;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.Vkontakte;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.job.limit.UserLimitProcessor;
import com.epam.lab.spider.job.limit.UserLimitsFactory;
import com.epam.lab.spider.job.util.Locker;
import com.epam.lab.spider.job.util.PostAttachmentUtil;
import com.epam.lab.spider.model.entity.*;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.*;
import com.epam.lab.spider.persistence.service.savable.SavableServiceUtil;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Kovalik
 */
public class PostExecutorJob implements Job {

    public static final Logger LOG = Logger.getLogger(PostExecutorJob.class);
    public static UserLimitProcessor limit = UserLimitsFactory.getUserLimitProcessor();
    PostingTaskService postingTaskService = new PostingTaskService();
    PostService postService = new PostService();
    WallService wallService = new WallService();
    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();
    AttachmentService attachmentService = new AttachmentService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        PostingTask postingTask = null;
        {
            Integer id = dataMap.getInt("new_post_id");
            postingTask = postingTaskService.getById(id);
        }
        if (postingTask == null) {
            LOG.error("Quartz failed. Have not new_post_id in DataMap!");
            return;
        }
        if (postingTask.getState().equals(PostingTaskImpl.State.ERROR)) {
            LOG.error("Before posting has been found error. Posting Blocked!");
            return;
        }

        try {
            Wall wall = wallService.getById(postingTask.getWallId());
            // Додаткова перевірка чи немає локу для цільвого обєкту
            // Якщо виконуєть вивід фатал повідомлення - існують проблеми
            // З архітектурою сервісу
            if (Locker.getInstance().isLock(wall)) {
                LOG.fatal("На заблоковану стіну зафіксована спроба виконати пост. Пост буде заблоковано! Проблема в " +
                        "архітектурі сервісу!");
                postingTask.setState(PostingTaskImpl.State.ERROR);
                postingTaskService.updateStage(postingTask);
                //SavableServiceUtil.safeSave(postingTask);
                return;
            }

            if (!limit.checkPostExecute(postingTask.getUserId())) {
                postingTask.setState(PostingTaskImpl.State.ERROR);
                postingTaskService.updateStage(postingTask);
                return;
            }
            Owner owner = ownerService.getById(wall.getOwnerId());
            Profile profile = profileService.getById(wall.getProfileId());

            postingTask.setPost(postService.getById(postingTask.getPost().getId()));
            try {
                Integer appId = profile.getAppId();
                if (appId == null) {
                    appId = SocialNetworkUtils.getDefaultVkAppsIdAsApps();
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
                for (Attachment attachment : postingTask.getPost().getAttachments()) {
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
                String textMessage = postingTask.getPost().getMessage().replaceAll("%owner%", owner.getDomain());
                textMessage = textMessage.replaceAll("%owner_name%", owner.getName());
                LOG.debug("Attachments: " + attachmentsStringBuilder.toString());
                Parameters parameters = new Parameters();
                parameters.add("owner_id", owner.getVkId());
                parameters.add("attachments", attachmentsStringBuilder.toString());
                parameters.add("message", textMessage);
                parameters.setRequestMethod(Request.Method.POST);
                if (owner.getVkId() < 0) {
                    parameters.add("from_group", 1);
                }
                Long response = vk.wall().post(parameters);

                EventLogger eventLogger = EventLogger.getLogger(postingTask.getUserId());
                if(response!=null){
                    postingTask.setState(PostingTaskImpl.State.POSTED);
                    postingTask.setVkPostId(Integer.parseInt(response.toString()));
                    SavableServiceUtil.safeSave(postingTask);
                    limit.markPostExecute(postingTask.getUserId());
                    String info = "Success to posting wall" + wall.getOwner().getVkId() + "_" + postingTask.getVkPostId();
                    LOG.info(info);
                    if(false)eventLogger.info(info, info);
                }else{
                    String error = "Failed to posting wall" + wall.getOwner().getVkId() + "_" + postingTask.getVkPostId();
                    LOG.error(error);
                    eventLogger.error(error, error);
                }


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
                    case 214:{
                        Locker.getInstance().lock(wall, DataLock.Mode.POST_LIMIT);
                        LOG.error("Posting has failed. Wall is locked. Post Limit is expired!");
                    }
                    default: {
                        LOG.error("Posting has failed. Corrupted new_post #" + postingTask.getId(), x);
                        x.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException x) {
            LOG.error("Posting has failed. Nullable object has founded. Corrupted new_post #" + postingTask.getId());
            x.printStackTrace();
        } catch (Throwable t){
            LOG.error(t);
        }
        postingTask.setState(PostingTaskImpl.State.ERROR);
        postingTaskService.updateStage(postingTask);
    }
}
