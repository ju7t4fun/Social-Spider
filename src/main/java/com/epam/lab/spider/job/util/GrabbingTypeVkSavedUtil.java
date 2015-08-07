package com.epam.lab.spider.job.util;

import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.Vkontakte;
import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.vk.Post;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author Yura Kovalik
 */
public class GrabbingTypeVkSavedUtil {
    public static final Logger LOG = Logger.getLogger(GrabbingTypeServerUtil.class);
    public static List<Post> grabbingRandom(Task.ContentType contentType, Owner owner, Vkontakte vk, Filter filter, Set<Integer> alreadyAddSet, int countOfPosts) throws InterruptedException, VKException {
        List<Post> postsToPosting = new ArrayList<>();
        int grabbingSize = 10;
        Integer grabbingLimitSize = 10;
        Random random = new Random();
        boolean endOfContent = false;
        int currentAttempt = 0;
        int totalAttempt = 3;
        postGrabbingAndFiltering:
        for(int loopsCount = 0;true;loopsCount++) {
            List<Post> grabbedPosts = null;
            boolean badExecution = false;
            do {
                try {
                    if (badExecution) {
                        Thread.sleep(250);
                        badExecution = false;
                    }
                    grabbedPosts = vk.execute().getRandomPostFromWall(owner.getVkId(),grabbingSize,filter,grabbingLimitSize, random.nextInt(255));
                    if(grabbedPosts.size()<grabbingSize){
                        endOfContent = true;
                    }
                } catch (VKException x) {
                    if(x.getExceptionCode() == VKException.VK_EXECUTE_RUNTIME_ERROR){
                        if(currentAttempt<totalAttempt){
                            badExecution = true;
                            currentAttempt++;
                        }else throw x;
                        // послаблення ліміту
                        grabbingLimitSize = (int)(grabbingLimitSize.doubleValue() * 0.8);
                    }
                    else throw x;
                }
            }while (badExecution);

            for (Post vkPost : grabbedPosts) {
                boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));
                if (alreadyProceededPost) {
                    LOG.debug("Post " + owner.getVkId() + "_" + vkPost.getId() + " already processed.");
                } else {
                    boolean hasContent = PostProcessingUtil.checkContent(vkPost, contentType);
                    if (hasContent) postsToPosting.add(vkPost);
                    boolean isNeededSize = postsToPosting.size() >= countOfPosts;
                    if (isNeededSize) break;
                }
            }
            if (postsToPosting.size() < countOfPosts){
                // збільшення кількості для наступного запиту
                grabbingSize = (int)(grabbingSize * 1.5);
            }

            if(endOfContent || loopsCount > 3){
                LOG.debug("End of post at owner#"+owner.getVkId());
                break postGrabbingAndFiltering;
            }
            //grabbingLimitSize = 999;
        }
        return postsToPosting;
    }
    public static List<Post> grabbingNew(Task.ContentType contentType, Owner owner, Vkontakte vk, Set<Integer> alreadyAddSet, int countOfPosts) throws InterruptedException, VKException {
        List<Post> postsToPosting = new ArrayList<>();
        SortedSet<Integer> sortedAlreadyAddSet = new TreeSet<>();
        sortedAlreadyAddSet.addAll(alreadyAddSet);
        Integer lastPostId = null;
        if(!sortedAlreadyAddSet.isEmpty()){
            try {
                lastPostId = sortedAlreadyAddSet.last();
            }catch (NoSuchElementException x){
                LOG.error("Помилка логіки.");
            }
        }
        List<Post> grabbedPosts = null;
        int localCount = sortedAlreadyAddSet.isEmpty()?countOfPosts:50;
        boolean badExecution = false;
        boolean unlimitedUpdate = true;
        int currentAttempt = 0;
        int totalAttempt = 3;
        do {
            try {
                if (badExecution) {
                    Thread.sleep(250);
                    badExecution = false;
                }
                grabbedPosts = vk.execute().getNewPostFromWall(owner.getVkId(), localCount, lastPostId, null);
            } catch (VKException x) {
                x.printStackTrace();
                if (x.getExceptionCode() == VKException.VK_EXECUTE_RUNTIME_ERROR) {
                    if(currentAttempt<totalAttempt){
                        badExecution = true;
                        currentAttempt++;
                    }else throw x;
                } else throw x;
            }
        } while (badExecution);

        for (Post vkPost : grabbedPosts) {
            boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));
            if (alreadyProceededPost) {
                LOG.debug("Post " + owner.getVkId() + "_" + vkPost.getId() + " already processed.");
            } else {
                boolean hasContent = PostProcessingUtil.checkContent(vkPost, contentType);
                if (hasContent) postsToPosting.add(vkPost);
                if(unlimitedUpdate) {
                    boolean isNeededSize = postsToPosting.size() >= countOfPosts;
                    if (isNeededSize) break;
                }
            }
        }
        return postsToPosting;
    }
    public static List<Post> testOverLoop(Vkontakte vk) throws InterruptedException, VKException {
        List<Post> postsToPosting = new ArrayList<>();


        List<Post> grabbedPosts = null;

        boolean badExecution = false;
        int currentAttempt = 0;
        int totalAttempt = 3;
        do {
            try {
                if (badExecution) {
                    Thread.sleep(250);
                    badExecution = false;
                }
                grabbedPosts = vk.execute().testOverLoop();
            } catch (VKException x) {
                if (x.getExceptionCode() == VKException.VK_EXECUTE_RUNTIME_ERROR) {
                    if(currentAttempt<totalAttempt){
                        badExecution = true;
                        currentAttempt++;
                    }else throw x;
                } else throw x;
            }
        } while (badExecution);

        for (Post vkPost : grabbedPosts) {
            postsToPosting.add(vkPost);
        }
        return postsToPosting;
    }

}
