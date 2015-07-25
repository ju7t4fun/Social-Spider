package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.job.exception.FindingEmptyResultException;
import com.epam.lab.spider.job.exception.PostContentException;
import com.epam.lab.spider.job.exception.WallAlreadyStopped;
import com.epam.lab.spider.job.exception.WallStopException;
import com.epam.lab.spider.job.limit.UserLimit;
import com.epam.lab.spider.job.limit.UserLimitsFactory;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.TaskSynchronizedDataService;
import com.epam.lab.spider.model.db.service.TaskSynchronizedNewDataService;
import com.epam.lab.spider.model.db.service.WallService;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.vk.*;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.epam.lab.spider.model.db.service.TaskSynchronizedNewDataService.getSynchronizedDataFactory;

/**
 * Created by hell-engine on 7/24/2015.
 */
public class TaskProcessingUtil {
    private static final Logger LOG = Logger.getLogger(TaskProcessingUtil.class);
    private static WallService wallService = new WallService();
    private static TaskSynchronizedDataService synchronizedService = new TaskSynchronizedDataService();

    private static TaskSynchronizedNewDataService syncNewService = new TaskSynchronizedNewDataService();

    private static UserLimit limit = UserLimitsFactory.getUserLimit();
    public static boolean processingTask(Task task){
        try {
            // якщо таск не активний то не запускаємо
            if (task.getState() != Task.State.RUNNING) return false;
            if (limit.checkTaskExecute(task.getUserId()))
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
            Map<Wall, SynchronizedData> syncMap = new HashMap<>();
            if(task.getType() != Task.Type.FAVORITE) {
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
                    TaskUtil.setNewTaskRunTimeAndUpdate(task);
                    return false;
                }
            }else{
                LOG.info("TASK#" + task.getId() + " has type FAVORITE.  Checking destination not will running.");
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
                TaskUtil.setNewTaskRunTimeAndUpdate(task);
                return false;
            }
            LinkedHashMap<Wall, List<com.epam.lab.spider.model.vk.Post>> postByWallMap = new LinkedHashMap<>();
            // grabbing wall
            for (Wall wall : sourceWalls) {
                Profile profile = wall.getProfile();
                boolean readBlocked = Locker.getInstance().isProfileReadableLock(profile.getId());
                if (!readBlocked) {
                    List<com.epam.lab.spider.model.vk.Post> list;
                    try {
                        list = GrabbingUtils.grabbingWall(wall, task);
                        postByWallMap.put(wall, list);
                    }catch (FindingEmptyResultException x){
                        // TODO: REFACTOR THIS
                        syncMap.put(wall, getSynchronizedDataFactory().createSynchronizedData(task, wall, x.getOffset(), x.getVkId()));
                    }catch (WallAlreadyStopped x){
                        LOG.debug("Wall already stopped.");
                    }catch (WallStopException x){
                        String title = "Task #+"+task.getId()+". Data for one of source wall has been ended.";
                        String message = "Data for wall #"+wall.getId()+" at task #"+task.getId()+" has been ended.";
                        LOG.info(message);
                        EventLogger eventLogger = EventLogger.getLogger(task.getUserId());
                        eventLogger.warn(title, message);
                        syncMap.put(wall, getSynchronizedDataFactory().createSynchronizedData(task, wall, -1, -1));
                        //postByWallMap.put(wall, new ArrayList<com.epam.lab.spider.model.vk.Post>());
                    }
                }
            }
            List<com.epam.lab.spider.model.vk.Post> postToRepost = new ArrayList<>();
            {
                // для режиму вибірки з кожної групи
                // або для нових постів в усіх випадках
                if (task.getGrabbingMode() == Task.GrabbingMode.PER_GROUP || (task.getGrabbingMode() == Task.GrabbingMode.TOTAL && task.getGrabbingType() == Task.GrabbingType.NEW) ) {
                    for (Map.Entry<Wall, List<com.epam.lab.spider.model.vk.Post>> entity : postByWallMap.entrySet()) {
                        Wall wall = entity.getKey();
                        List<com.epam.lab.spider.model.vk.Post> postsPrepareToPosting = entity.getValue();
                        LinkedList<Integer> addedToProcessingBlocks = new LinkedList<>();
                        for (com.epam.lab.spider.model.vk.Post vkPost : postsPrepareToPosting) {
                            try {
                                switch (task.getType()) {
                                    // якщо тип таску пост - додаємо пост до опрацювання та зберігаємо в базу
                                    case COPY: {
                                        Post post = PostProcessingUtil.processingPost(vkPost, task);
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
                                        Post post = PostProcessingUtil.processingPost(vkPost, task);
                                        System.out.println("-------------" + post);
                                        Feed feed = new Feed();
                                        feed.processing(post, task);
                                        break;
                                    }
                                }
                                if(vkPost instanceof PostOffsetDecorator){
                                    PostOffsetDecorator decoratedPost = (PostOffsetDecorator) vkPost;
                                    SynchronizedData sync = getSynchronizedDataFactory().createSynchronizedData(task, wall, decoratedPost);
                                    switch (task.getGrabbingType()) {
                                        case BEGIN:
                                        case END:
                                            syncMap.put(wall,sync);
                                            break;
                                        default:
                                            LOG.warn("SyncData has not can used at unsynchronized method.");
                                    }
                                    LOG.debug("PostOffsetDecorator detected");
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
                } else
                    // тільки для режиму вибірки з кінця з почаку чи рандомні пости
                    if (task.getGrabbingMode() == Task.GrabbingMode.TOTAL) {
                        Random random = new Random();
                        int currentPostCount = 0;
                        while (currentPostCount < task.getPostCount() && !postByWallMap.isEmpty()) {
                            //випадковим чимном вибираємо кошик, з якого буде братись пост
                            int basket = random.nextInt(postByWallMap.size());

                            // перебір мапи
                            int j = 0;
                            Map.Entry<Wall, List<com.epam.lab.spider.model.vk.Post>> currentEntry = null;
                            for (Map.Entry<Wall, List<com.epam.lab.spider.model.vk.Post>> entry : postByWallMap.entrySet())
                                if (j++ == basket) currentEntry = entry;
                            // !перебір мапи
                            Wall wall = currentEntry.getKey();
                            List<com.epam.lab.spider.model.vk.Post> basketOfPreparePost = currentEntry.getValue();
                            // видалення корзини з постами, якщо вона порожня
                            if (basketOfPreparePost.isEmpty()) {
                                postByWallMap.remove(currentEntry.getKey());
                                continue;
                            }
                            int postIndex;
                            boolean isRandomPostIndex = false;
                            // вибір випадкового поста з корзини
                            if(isRandomPostIndex)postIndex = random.nextInt(basketOfPreparePost.size());
                                // вибір першого поста з корзини
                            else postIndex =  0;
                            // витягаємо та видаляємо з корзини вибраний пост
                            com.epam.lab.spider.model.vk.Post vkPost = basketOfPreparePost.remove(postIndex);
                            try {
                                switch (task.getType()) {
                                    // опрацьовуємо пост за правилами заданими в таску
                                    // якщо тип таску пост - додаємо пост до опрацювання та зберігаємо в базу
                                    case COPY: {
                                        Post post = PostProcessingUtil.processingPost(vkPost, task);
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
                                        Post post = PostProcessingUtil.processingPost(vkPost, task);
                                        System.out.println("-------------" + post);
                                        Feed feed = new Feed();
                                        feed.processing(post, task);
                                        break;
                                    }
                                }
                                if(vkPost instanceof PostOffsetDecorator){
                                    PostOffsetDecorator decoratedPost = (PostOffsetDecorator) vkPost;
                                    SynchronizedData sync = getSynchronizedDataFactory().createSynchronizedData(task,wall,decoratedPost);
                                    SynchronizedData oldSync = syncMap.get(wall);
                                    if(oldSync == null ){
                                        syncMap.put(wall,sync);
                                    }else{
                                        switch (task.getGrabbingType()) {
                                            case END:
                                                if(decoratedPost.getOffset()<oldSync.getPostOffset()){
                                                    syncMap.put(wall,sync);
                                                }
                                                break;
                                            case BEGIN:
                                                if(decoratedPost.getOffset()>oldSync.getPostOffset()){
                                                    syncMap.put(wall,sync);
                                                }
                                                break;
                                            default:
                                                LOG.warn("SyncData has not can used at unsynchronized method.");
                                        }
                                    }
                                    LOG.debug("PostOffsetDecorator detected");
                                }
                            } catch (PostContentException x) {
                                LOG.error("Post Content Type Fail. Object: post" + vkPost.getOwnerId() + "_" + vkPost.getId());
                            } catch (NullPointerException x){
                                LOG.error("Post Content Type Fail. NullPointerException.");
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
                    // set user id
                    newPost.setUserId(task.getUserId());
                    newPost.getPost().setUserId(task.getUserId());
                    timeToPost += TaskUtil.getRandomPostDeleay(task) * 1000;
                    newPost.setPostTime(new Date(timeToPost));

                    newPost.setState(NewPost.State.CREATED);
                    newPosts.addFirst(newPost);
                }
                // моментальний ріпостинг
                for (com.epam.lab.spider.model.vk.Post vkPost : postToRepost) {
                    String wallEntityCode = "wall" + vkPost.getOwnerId() + "_" + vkPost.getId();
                    String sign = "";
                    if(task.getSignature()!=null)sign = task.getSignature()+" ";
                    if(task.getHashTags()!=null)sign+= task.getHashTags();
                    RepostUtil.makeRepost(wall.getProfile(), wallEntityCode, wall.getOwner(), sign);
                }

            }
            for (NewPost newPost : newPosts) {
                boolean result = SavableServiceUtil.safeSave(newPost);
                if (!result) LOG.fatal("ТЕРМІНОВО ФІКСИТЬ БАЗУ!");
            }
            for (Map.Entry<Wall, LinkedList<Integer>> entry : blockMap.entrySet()) {
                synchronizedService.markIdLastProcessedPost(task, entry.getKey(), entry.getValue());
            }
            for (Map.Entry<Wall, SynchronizedData> entry : syncMap.entrySet()) {
                syncNewService.save(entry.getValue());
            }
            TaskUtil.setNewTaskRunTimeAndUpdate(task);
            {
                int countGrabMax = task.getPostCount();
                switch (task.getGrabbingMode()) {
                    case PER_GROUP:
                        countGrabMax = activeSourceInTask * task.getPostCount();
                        break;
                    case TOTAL:
                        countGrabMax = task.getPostCount();
                        break;

                }
                int countGrabSuccess = addedToProcessingPosts.size();
                String newString = new SimpleDateFormat("H:mm:ss").format(task.getNextTaskRunDate());
                String title = "Task #" + task.getId() + " have grabbed post " + countGrabSuccess + "/" + countGrabMax + ". ";
                String info = title + " Planned Post Action Count: " + newPosts.size()+". Next run at "+newString;
                if(task.getType() != Task.Type.FAVORITE) {
                    EventLogger eventLogger = EventLogger.getLogger(task.getUserId());
                    eventLogger.info(title, info);
                }
                return true;
            }
            //збираэмо всі необроблені виключення
        }catch (Throwable x){
            x.printStackTrace();
            return false;
        }

    }
}
