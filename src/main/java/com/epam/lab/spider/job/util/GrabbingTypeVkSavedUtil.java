package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.entity.Filter;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.vk.Post;
import com.epam.lab.spider.model.vk.PostOffsetDecorator;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by hell-engine on 7/5/2015.
 */
public class GrabbingTypeVkSavedUtil {
    public static final Logger LOG = Logger.getLogger(GrabbingTypeServerUtil.class);
    public static List<Post> grabbingRandom(Task.ContentType contentType, Owner owner, Vkontakte vk, Filter filter, Set<Integer> alreadyAddSet, int countOfPosts) throws InterruptedException, VKException {
        List<Post> postsToPosting = new ArrayList<>();
        int grabbingSize = 10;
        Integer grabbingLimitSize = 10;
        Random random = new Random();
        boolean endOfContent = false;
        postGrabbingAndFiltering:
        for(int loopsCount = 0;true;loopsCount++) {
            List<Post> grabbedPosts = null;
            boolean manyRequest = false, badExecution = false;
            do {
                try {
                    if (manyRequest|| badExecution) {
                        Thread.sleep(400);
                        manyRequest = false;
                        badExecution = false;
                    }
                    grabbedPosts = vk.execute().getRandomPostFromWall(owner.getVkId(),grabbingSize,filter,grabbingLimitSize, random.nextInt(255));
                    if(grabbedPosts.size()<grabbingSize){
                        endOfContent = true;
                    }
                } catch (VKException x) {
                    if(x.getExceptionCode()==VKException.VK_MANY_REQUESTS)manyRequest=true;
                    else if(x.getExceptionCode() == VKException.VK_EXECUTE_RUNTIME_ERROR){
                        badExecution = false;
                        // послаблення ліміту
                        grabbingLimitSize = (int)(grabbingLimitSize.doubleValue() * 0.8);
                    }
                    else throw x;
                }
                catch (NullPointerException x){
                    badExecution = true;
                }
            }while (manyRequest || badExecution);

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
        List<Post> grabbedPosts = null;
        int localCount = sortedAlreadyAddSet.isEmpty()?countOfPosts:50;
        boolean badExecution = false;
        boolean unlimitedUpdate = true;
        do {
            try {
                if (badExecution) {
                    Thread.sleep(400);
                    badExecution = false;
                }
                grabbedPosts = vk.execute().getNewPostFromWall(owner.getVkId(), localCount, sortedAlreadyAddSet.last(), null);
            } catch (VKException x) {
                if (x.getExceptionCode() == VKException.VK_EXECUTE_RUNTIME_ERROR) {
                    badExecution = false;
                } else throw x;
            } catch (NullPointerException x) {
                badExecution = true;
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

}
