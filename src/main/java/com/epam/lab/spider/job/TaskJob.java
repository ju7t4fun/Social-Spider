package com.epam.lab.spider.job;

import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.job.exception.PostContentException;
import com.epam.lab.spider.job.util.*;
import com.epam.lab.spider.model.db.entity.*;


import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.*;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.vk.*;
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
    TaskService taskService = new TaskService();
    TaskSynchronizedDataService synchronizedService = new TaskSynchronizedDataService();

    public Post processingPost(com.epam.lab.spider.model.vk.Post vkPost,  Task task)throws PostContentException {
        return processingPost(vkPost, task.getContentType(), task.getHashTags());
    }

    public Post processingPost(com.epam.lab.spider.model.vk.Post vkPost,  Task.ContentType contentType) throws PostContentException {
        return processingPost(vkPost, contentType, null);
    }
    public Post processingPost(com.epam.lab.spider.model.vk.Post vkPost,  Task.ContentType contentType, String hashTags) throws PostContentException {
        Post post = new Post();
        Task.ContentType newPostContentType = new Task.ContentType();
        StringBuilder messageBuilder = new StringBuilder();
        String pureText = vkPost.getText().trim();
        //TODO: REMOVE ALL HASH TAGS FROM SOURCE TEXT
        if(!contentType.hasHashtags()){

        }
        if (contentType.hasText()) {
            if(!pureText.isEmpty()) {
                messageBuilder.append(pureText).append("\n");
                newPostContentType.setType(Task.ContentType.TEXT);
            }
        }
        if(contentType.hasReposts()){
            List<com.epam.lab.spider.model.vk.Post> copyHistoryList = vkPost.getCopyHistory();
            if(!copyHistoryList.isEmpty()){
                post = processingPost(copyHistoryList.get(0),  contentType);
                String innerMessage = post.getMessage();
                messageBuilder.append(innerMessage).append("\n");
                newPostContentType.setType(Task.ContentType.REPOSTS);
            }
        }
        {
            if(hashTags!=null)messageBuilder.append(hashTags);
            post.setMessage(messageBuilder.toString().trim());
        }
        for (com.epam.lab.spider.model.vk.Attachment vkAttachment : vkPost.getAttachments()) {
            if (contentType.hasPhoto() && vkAttachment instanceof Photo) {
                Attachment attachment = new Attachment();
                attachment.setType(Attachment.Type.PHOTO);
                attachment.setPayload(((Photo) vkAttachment).getPhoto604().toString());
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.PHOTO);
            }
            if (contentType.hasAudio() && vkAttachment instanceof Audio) {
                Attachment attachment = new Attachment();
                Audio audio = (Audio) vkAttachment;
                String attachString = "audio" + audio.getOwnerId() + "_" + audio.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.AUDIO);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.AUDIO);
            }
            if (contentType.hasDoc() && vkAttachment instanceof Doc) {
                Attachment attachment = new Attachment();
                Doc doc = (Doc) vkAttachment;
                String attachString = "doc" + doc.getOwnerId() + "_" + doc.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.DOC);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.DOCUMENTS);
            }
            if (contentType.hasVideo() && vkAttachment instanceof Video) {
                Attachment attachment = new Attachment();
                Video video = (Video) vkAttachment;
                String attachString = "video" + video.getOwnerId() + "_" + video.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.VIDEO);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.VIDEO);
            }
        }
        if(newPostContentType.getType().intValue()==0){
            throw new PostContentException();
//            return null;
        }else return post;
    }

    public List<com.epam.lab.spider.model.vk.Post> grabbingWall(Wall wall, Task task) {
        List<com.epam.lab.spider.model.vk.Post> postsPrepareToPosting = new ArrayList<>();
        Owner owner = wall.getOwner();
        Profile profile = wall.getProfile();
        Filter filter = task.getFilter();
        Set<Integer> alreadyAddSet = synchronizedService.getProcessedPost(task, wall, 10000);
        Integer grabbingSize = task.getGrabbingSize();
        int countOfPosts = task.getPostCount();
        try {
            Integer appId = profile.getAppId();
            Vkontakte vk = new Vkontakte(appId);
            // Initialization auth_token
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime().getTime());
            vk.setAccessToken(accessToken);
            // !Initialization auth_token

            // Work Body
            switch (task.getGrabbingType()) {

                case BEGIN:
                    postsPrepareToPosting = GrabbingTypeServerUtil.grabbingBegin(owner, vk, filter, alreadyAddSet,
                            countOfPosts, grabbingSize);
                    break;
                case RANDOM:
                    postsPrepareToPosting = GrabbingTypeVkSavedUtil.grabbingRandom(owner, vk, filter, alreadyAddSet,
                            countOfPosts, grabbingSize);
                    LOG.info("New grabbingSize:" + grabbingSize);
                    break;
                case END:
                    postsPrepareToPosting = GrabbingTypeServerUtil.grabbingEnd(owner, vk, filter, alreadyAddSet,
                            countOfPosts, grabbingSize);
                    break;
                case NEW:
                    postsPrepareToPosting = GrabbingTypeServerUtil.grabbingEnd(owner, vk, null, alreadyAddSet,
                            countOfPosts, grabbingSize);
                    break;
            }

            // !Work Body

            LOG.info("TaskJob has successes grab wall#" + wall.getId());
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
        return postsPrepareToPosting;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        LOG.info(" TaskJob start processing at " + new Date(startTime).toString());
        Date nextRunDate = new Date(System.currentTimeMillis() + (60 * 1000));
        List<Task> tasks = taskService.getRunnableByNextRunDate(nextRunDate);
        taskProcessing:
        for (Task task : tasks) {
            // якщо таск не активний то не запускаємо
            if (task.getState() != Task.State.RUNNING) continue taskProcessing;
            LOG.info("Task#" + task.getId() + " start to work.");
            // кількість активних(незаблокованих) джерел для таску
            // якщо кількість нульова на момент опрацювання стін призначення
            // то опрацювання таску на цьому завершується
            int activeSourceInTask = 0;
            int activeDestinationInTask = 0;
            List<Wall> sourceWalls = wallService.getSourceByTaskId(task.getId());
            List<Wall> destinationWalls = wallService.getDestinationByTaskId(task.getId());

            LinkedList<Post> addedToProcessingPosts = new LinkedList<>();
            Map<Wall, LinkedList<Integer>> blockMap = new HashMap<>();
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
            // перевірка кількості незаблокованих джерел з незаблокованими профілями
            for (Wall wall : sourceWalls) {
                Profile profile = wall.getProfile();
                // перевірка чи профіль заблокований на читання
                boolean readBlocked = Locker.getInstance().isProfileReadableLock(profile.getId());
                if (!readBlocked) activeSourceInTask++;
            }
            // якщо кількість стін-джерел нульова
            // то принипини працювання завдання
            if (activeSourceInTask == 0) {
                LOG.info("TASK#" + task.getId() + " has hot active source wall!");
                continue taskProcessing;
            }


            LinkedHashMap<Wall, List<com.epam.lab.spider.model.vk.Post>> postByWallMap = new LinkedHashMap<>();

            //random N-posts from begin wall
            for (Wall wall : sourceWalls) {
                Profile profile = wall.getProfile();
                boolean readBlocked = Locker.getInstance().isProfileReadableLock(profile.getId());
                if (!readBlocked) activeSourceInTask++;
                List<com.epam.lab.spider.model.vk.Post> list;
                list = this.grabbingWall(wall, task);
                postByWallMap.put(wall, list);
            }
            List<com.epam.lab.spider.model.vk.Post> postToRepost = new ArrayList<>();
            {
                if (task.getGrabbingMode() == Task.GrabbingMode.TOTAL) {
                    for (Map.Entry<Wall, List<com.epam.lab.spider.model.vk.Post>> entity : postByWallMap.entrySet()) {
                        List<com.epam.lab.spider.model.vk.Post> postsPrepareToPosting = entity.getValue();
                        LinkedList<Integer> addedToProcessingBlocks = new LinkedList<>();
                        for (com.epam.lab.spider.model.vk.Post vkPost : postsPrepareToPosting) {
                            try {
                                switch (task.getType()) {
                                    // якщо тип таску пост - додаємо пост до опрацювання та зберігаємо в базу
                                    case COPY: {
                                        Post post = processingPost(vkPost, task);
                                        addedToProcessingPosts.addFirst(post);
                                        break;
                                    }
                                    // якщо тип - репост - додаємо пост в чергу до опрацювання та ріпостимо його
                                    // якщо таск завершиться аварійно - можлива втрата репосту даного поста
                                    case REPOST: {
                                        postToRepost.add(vkPost);
                                        break;
                                    }
                                    case FAVORITE: {
                                        Post post = processingPost(vkPost, task);
                                        System.out.println("-------------" + post);
                                        Feed feed = new Feed();
                                        feed.processing(post, task);
                                        break;
                                    }
                                }
                                LOG.debug("Post wall" + vkPost.getOwnerId() + "_" + vkPost.getId() + " has added to " +
                                        "processing.");
                            } catch (PostContentException x) {
                                LOG.error("Post Content Type Fail. Object: post" + vkPost.getOwnerId() + "_" + vkPost.getId());
                            }
                            // заблоковуємо пост
                            // виконується якщо пост додато до потингу
                            // або для поста сталась помилка контенту
                            addedToProcessingBlocks.addFirst(vkPost.getId());
                        }
                        blockMap.put(entity.getKey(), addedToProcessingBlocks);
                    }
                } else if (task.getGrabbingMode() == Task.GrabbingMode.PER_GROUP) {
                    Random random = new Random();
                    int currentPostCount = 0;
                    while (currentPostCount < task.getPostCount() && !postByWallMap.isEmpty()) {
                        int basket = random.nextInt(postByWallMap.size());

                        // перебір мапи
                        int j = 0;
                        Map.Entry<Wall, List<com.epam.lab.spider.model.vk.Post>> currentEntry = null;
                        for (Map.Entry<Wall, List<com.epam.lab.spider.model.vk.Post>> entry : postByWallMap.entrySet())
                            if (j++ == basket) currentEntry = entry;
                        // !перебір мапи
                        List<com.epam.lab.spider.model.vk.Post> basketOfPreparePost = currentEntry.getValue();
                        // видалення корзини з постами, якщо вона порожня
                        if (basketOfPreparePost.isEmpty()) {
                            postByWallMap.remove(currentEntry.getKey());
                            continue;
                        }
                        // вибір випадкового поста з корзини
                        int postIndex = random.nextInt(basketOfPreparePost.size());
                        // витягаємо та видаляємо з корзини вибраний пост
                        com.epam.lab.spider.model.vk.Post vkPost = basketOfPreparePost.remove(postIndex);
                        try {
                            switch (task.getType()) {
                                // опрацьовуємо пост за правилами заданими в таску
                                // якщо тип таску пост - додаємо пост до опрацювання та зберігаємо в базу
                                case COPY: {
                                    Post post = processingPost(vkPost, task);
                                    addedToProcessingPosts.addFirst(post);
                                    break;
                                }
                                // якщо тип - репост - додаємо пост в чергу до опрацювання та ріпостимо його
                                // якщо таск завершиться аварійно - можлива втрата репосту даного поста
                                case REPOST: {
                                    postToRepost.add(vkPost);
                                    break;
                                }
                                case FAVORITE: {
                                    Post post = processingPost(vkPost, task);
                                    System.out.println("-------------" + post);
                                    Feed feed = new Feed();
                                    feed.processing(post, task);
                                    break;
                                }
                            }
                        } catch (PostContentException x) {
                            LOG.error("Post Content Type Fail. Object: post"+ vkPost.getOwnerId() + "_" + vkPost.getId());
                        }
                        // заблоковуємо пост
                        // виконується якщо пост додато до потингу
                        // або для поста сталась помилка контенту
                        LinkedList<Integer> addedToProcessingBlocks = blockMap.get(currentEntry.getKey());
                        if (addedToProcessingBlocks == null) {
                            addedToProcessingBlocks = new LinkedList<>();
                            blockMap.put(currentEntry.getKey(), addedToProcessingBlocks);
                        }
                        addedToProcessingBlocks.addFirst(vkPost.getId());
                    }

                }
            }


            LinkedList<NewPost> newPosts = new LinkedList<>();

            long timeToPost = task.getNextTaskRunDate().getTime();
            for (Wall wall : destinationWalls) {
                // якщо профіль заблокований для запису припинити опрацювання стіни для нього
                boolean writeBlocked = Locker.getInstance().isLock(wall);
                if (writeBlocked) break;
                // відкладений постинг
                for (Post post : addedToProcessingPosts) {
                    NewPost newPost = new NewPost();
                    newPost.setPost(post);
                    newPost.setWallId(wall.getId());

                    timeToPost += TaskUtil.getRandomPostDeleay(task) * 1000;
                    newPost.setPostTime(new Date(timeToPost));

                    newPost.setState(NewPost.State.CREATED);
                    newPosts.addFirst(newPost);
                }
                // моментальний ріпостинг
                for (com.epam.lab.spider.model.vk.Post vkPost : postToRepost) {
                    String wallEntityCode = "wall" + vkPost.getOwnerId() + "_" + vkPost.getId();
                    RepostUtil.makeRepost(wall.getProfile(), wallEntityCode, wall.getOwner());
                }

            }
            for (NewPost newPost : newPosts) {
                boolean result = SavableServiceUtil.safeSave(newPost);
                if (!result) LOG.fatal("ТЕРМІНОВО ФІКСИТЬ БАЗУ!");
            }
            for (Map.Entry<Wall, LinkedList<Integer>> entry : blockMap.entrySet()) {
                synchronizedService.markIdLastProcessedPost(task, entry.getKey(), entry.getValue());
            }
            TaskUtil.setNewTaskRunTime(task);
            {
                int countGrabMax = task.getPostCount();
                switch (task.getGrabbingMode()) {
                    case TOTAL:
                        countGrabMax = activeSourceInTask * task.getPostCount();
                        break;
                    case PER_GROUP:
                        countGrabMax = task.getPostCount();
                        break;
                }
                int countGrabSuccess = addedToProcessingPosts.size();
                String title = "Task #" + task.getId() + " have grabbed post " + countGrabSuccess + "/" + countGrabMax+".";
                String info = title+" Planned Post Action Count: "+newPosts.size();
                EventLogger eventLogger = EventLogger.getLogger(task.getUserId());
                eventLogger.info(title, info);
            }
        }

        long finishTime = System.currentTimeMillis();
        float workTimeInSecond = (finishTime - startTime) / 1000.f;

        NumberFormat formatter = new DecimalFormat("#0.000");
        LOG.info("TaskJob has finish. Work time " + formatter.format(workTimeInSecond) + "s");
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
