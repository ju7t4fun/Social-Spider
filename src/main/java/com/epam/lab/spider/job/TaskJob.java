package com.epam.lab.spider.job;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.job.util.Locker;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.service.*;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.vk.Audio;
import com.epam.lab.spider.model.vk.Doc;
import com.epam.lab.spider.model.vk.Photo;
import com.epam.lab.spider.model.vk.Video;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
        long startTime = System.currentTimeMillis();
        LOG.info(" TaskJob start processing at " + new Date(startTime).toString());
        List<Task> tasks = taskService.getAll();
        taskProcessing:
        for (Task task : tasks) {
            LOG.info("Task#" + task.getId() + " start to work.");
            // кількість активних(незаблокованих) джерел для таску
            // якщо кількість нульова на момент опрацювання стін призначення
            // то опрацювання таску на цьому завершується
            int activeSourceInTask = 0;
            int activeDestinationInTask = 0;
            Filter filter = filterService.getById(task.getFilterId());
            List<Wall> sourceWalls = wallService.getSourceByTaskId(task.getId());
            List<Wall> destinationWalls = wallService.getDestinationByTaskId(task.getId());

            LinkedList<Post> addedToProcessingPosts = new LinkedList<>();
            Map<Wall, List<Integer>> blockMap = new HashMap<>();
            // перевірка кількості незаблокованих стін з незаблокованими профіліми
            for (Wall wall : destinationWalls) {
                // перевірка чи стіна заблокована
                boolean writeBlocked = Locker.getInstance().isLock(wall);
                if (!writeBlocked) activeDestinationInTask++;
            }
            // якщо кількість стін призначення нульова
            // то принипини працювання завдання
            if (activeDestinationInTask == 0) {
                LOG.info("TASK#" + task.getId() + " has hot active destination wall!");
                continue taskProcessing;
            }
            //random N-posts from begin wall
            int countOfPosts = 2;
            int grabbingSize = 10;

            for (Wall wall : sourceWalls) {
                Owner owner = ownerService.getById(wall.getOwner_id());
                Profile profile = profileService.getById(wall.getProfile_id());
                // перевірка чи профіль заблокований на читання
                boolean readBlocked = Locker.getInstance().isProfileReadableLock(profile.getId());
                if (readBlocked) {
                    break;
                } else activeSourceInTask++;
                Set<Integer> alreadyAddSet = synchronizedService.getProcessedPost(task, wall, 10000);
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
                    Parameters parameters;

                    List<com.epam.lab.spider.model.vk.Post> postsPrepareToPosting = new ArrayList<>();

                    postGrabbingAndFiltering:
                    for (int loopsCount = 0, currentPostCount = 0; true; loopsCount++) {

                        parameters = new Parameters();
                        parameters.add("owner_id", owner.getVk_id());
                        parameters.add("filter", "owner");
                        parameters.add("count", grabbingSize);
                        parameters.add("offset", loopsCount * grabbingSize);

                        List<com.epam.lab.spider.model.vk.Post> postsOnTargetWall = null;
                        boolean manyRequest = false;
                        do {
                            try {
                                if (manyRequest) {
                                    Thread.sleep(400);
                                    manyRequest = false;
                                }
                                postsOnTargetWall = vk.wall().get(parameters);
                            } catch (VKException x) {
                                if (x.getExceptionCode() == VKException.VK_MANY_REQUESTS) manyRequest = true;
                                else throw x;
                            }
                        } while (manyRequest);
                        for (com.epam.lab.spider.model.vk.Post vkPost : postsOnTargetWall) {
                            boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));

                            if (alreadyProceededPost) {
                                LOG.debug("Post " + owner.getVk_id() + "_" + vkPost.getId() + " already processed.");
                            } else {
                                boolean qualityCondition = true;
                                if (filter.getLikes() != null)
                                    qualityCondition &= vkPost.getLikes().getCount() >= filter.getLikes().intValue();
                                if (filter.getReposts() != null)
                                    qualityCondition &= vkPost.getReposts().getCount() >= filter.getLikes().intValue();
                                if (filter.getComments() != null)
                                    qualityCondition &= vkPost.getComments().getCount() >= filter.getComments()
                                            .intValue();
                                if (qualityCondition) {
                                    postsPrepareToPosting.add(vkPost);
                                    currentPostCount++;
                                    if (!(currentPostCount < countOfPosts)) break postGrabbingAndFiltering;
                                } else {
                                    LOG.debug("Post " + owner.getVk_id() + "_" + vkPost.getId() + " has failed filter" +
                                            ".");

                                }
                            }
                        }
                    }
                    // Додавання постів в чергу для запощення
                    LinkedList<Integer> addedToProcessingBlocks = new LinkedList<>();
                    for (com.epam.lab.spider.model.vk.Post vkPost : postsPrepareToPosting) {
                        Post post = new Post();
                        post.setMessage(vkPost.getText());
                        for (com.epam.lab.spider.model.vk.Attachment vkAttachment : vkPost.getAttachments()) {
                            if (vkAttachment instanceof Photo) {
                                Attachment attachment = new Attachment();
                                attachment.setType(Attachment.Type.PHOTO);
                                attachment.setPayload(((Photo) vkAttachment).getPhoto604().toString());
                                post.addAttachment(attachment);
                            }
                            if (vkAttachment instanceof Audio) {
                                Attachment attachment = new Attachment();
                                Audio audio = (Audio) vkAttachment;
                                String attachString = "audio" + audio.getOwnerId() + "_" + audio.getId();
                                attachment.setPayload(attachString);
                                attachment.setMode(Attachment.Mode.CODE);
                                attachment.setType(Attachment.Type.AUDIO);
                                post.addAttachment(attachment);
                            }
                            if (vkAttachment instanceof Doc) {
                                Attachment attachment = new Attachment();
                                Doc doc = (Doc) vkAttachment;
                                String attachString = "doc" + doc.getOwnerId() + "_" + doc.getId();
                                attachment.setPayload(attachString);
                                attachment.setMode(Attachment.Mode.CODE);
                                attachment.setType(Attachment.Type.DOC);
                                post.addAttachment(attachment);
                            }
                            if (vkAttachment instanceof Video) {
                                Attachment attachment = new Attachment();
                                Video video = (Video) vkAttachment;
                                String attachString = "video" + video.getOwnerId() + "_" + video.getId();
                                attachment.setPayload(attachString);
                                attachment.setMode(Attachment.Mode.CODE);
                                attachment.setType(Attachment.Type.DOC);
                                post.addAttachment(attachment);
                            }
                        }
                        addedToProcessingPosts.addFirst(post);
                        addedToProcessingBlocks.addFirst(vkPost.getId());
                        LOG.debug("Post " + owner.getVk_id() + "_" + vkPost.getId() + " has added to processing.");
                    }
                    blockMap.put(wall, addedToProcessingBlocks);
                    LOG.info("TaskJob has succesed grab.");
                } catch (RuntimeException x) {
                    x.printStackTrace();
                } catch (VKException e) {
                    if (e.getExceptionCode() == VKException.VK_AUTHORIZATION_FAILED) {
                        Locker.getInstance().lock(profile, DataLock.Mode.AUTH_KEY);
                    }
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            // якщо кількість стін-джерел нульова
            // то принипини працювання завдання
            if (activeSourceInTask == 0) {
                LOG.info("TASK#" + task.getId() + " has hot active source wall!");
                break taskProcessing;
            }

            LinkedList<NewPost> newPosts = new LinkedList<>();
            long timeToPost = System.currentTimeMillis() + 15 * 1000;
            for (Wall wall : destinationWalls) {
                // якщо профіль заблокований для припинити опрацювання стіни для нього
                boolean writeBlocked = Locker.getInstance().isLock(wall);
                if (writeBlocked) break;

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
//            NewPostService newPostService = new NewPostService();
            for (NewPost newPost : newPosts) {
                boolean result = SavableServiceUtil.safeSave(newPost);
                if (!result) LOG.fatal("ТЕРМІНОВО ФІКСИТЬ БАЗУ, БРУДНА ТВАРИНО!");
            }
            for (Map.Entry<Wall, List<Integer>> entry : blockMap.entrySet()) {
                synchronizedService.markIdLastProcessedPost(task, entry.getKey(), entry.getValue());
            }
        }

        long finishTime = System.currentTimeMillis();
        float workTimeInSecond = (finishTime - startTime) / 1000.f;

        NumberFormat formatter = new DecimalFormat("#0.000");
        LOG.info("TaskJob has finish. Work time " + formatter.format(workTimeInSecond) + "s");

        Date nextRunDate = new Date(System.currentTimeMillis() + (2 * 60 * 1000));
        nextRunDate = new Date(System.currentTimeMillis() + 10000);
        LOG.info("Next run 'TaskJob' at " + nextRunDate.toString());

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
