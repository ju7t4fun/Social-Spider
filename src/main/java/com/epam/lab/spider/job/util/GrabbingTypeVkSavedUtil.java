package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.entity.Filter;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.vk.Post;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by hell-engine on 7/5/2015.
 */
public class GrabbingTypeVkSavedUtil {
    public static final Logger LOG = Logger.getLogger(GrabbingTypeServerUtil.class);
    public static List<Post> grabbingRandom(Owner owner, Vkontakte vk, Filter filter, Set<Integer> alreadyAddSet, int countOfPosts) throws InterruptedException, VKException {
        List<Post> postsPrepareToPosting = new ArrayList<>();
        int grabbingSize = 10;
        Integer grabbingLimitSize = 10;
        Random random = new Random();
        boolean endOfContent = false;
        postGrabbingAndFiltering:
        for(int loopsCount = 0,currentPostCount = 0;true;loopsCount++) {
            List<Post> postsOnTargetWall = null;
            boolean manyRequest = false, badExecution = false;
            do {
                try {
                    if (manyRequest|| badExecution) {
                        Thread.sleep(400);
                        manyRequest = false;
                        badExecution = false;
                    }
                    postsOnTargetWall = vk.execute().getRandomPostFromWall(owner.getVkId(),grabbingSize,filter,grabbingLimitSize, random.nextInt(255));
                    if(postsOnTargetWall.size()<grabbingSize){
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
                // фікс архітектури
                // перехоплення NullPointerException by UnknownHostException
                catch (NullPointerException x){
                    badExecution = true;
                }
            }while (manyRequest || badExecution);

            for (com.epam.lab.spider.model.vk.Post vkPost : postsOnTargetWall) {
                boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));
                if (alreadyProceededPost) {
                    LOG.debug("Post " + owner.getVkId() + "_" + vkPost.getId() + " already processed.");
                } else {
                    postsPrepareToPosting.add(vkPost);
                    currentPostCount++;
                    if (currentPostCount >= countOfPosts) break postGrabbingAndFiltering;
                }
            }
            if (currentPostCount < countOfPosts){
                // збільшення кількості для наступного запиту
                grabbingSize = (int)(grabbingSize * 1.5);
            }

            if(endOfContent || loopsCount > 3){
                LOG.debug("End of post at owner#"+owner.getVkId());
                break postGrabbingAndFiltering;
            }
            //grabbingLimitSize = 999;
        }
        return postsPrepareToPosting;
    }
    public static List<Post> grabbingNew(Owner owner, Vkontakte vk, Set<Integer> alreadyAddSet, int countOfPosts) throws InterruptedException, VKException {
        List<Post> postsPrepareToPosting = new ArrayList<>();
        SortedSet<Integer> sortedAlreadyAddSet = new TreeSet<>();
        sortedAlreadyAddSet.addAll(alreadyAddSet);
        List<Post> postsOnTargetWall = null;
        int localCount = sortedAlreadyAddSet.isEmpty()?countOfPosts:50;
        boolean badExecution = false;
        do {
            try {
                if (badExecution) {
                    Thread.sleep(400);
                    badExecution = false;
                }
                postsOnTargetWall = vk.execute().getNewPostFromWall(owner.getVkId(), localCount, sortedAlreadyAddSet.last(), null);
            } catch (VKException x) {
                if (x.getExceptionCode() == VKException.VK_EXECUTE_RUNTIME_ERROR) {
                    badExecution = false;
                } else throw x;
            } catch (NullPointerException x) {
                badExecution = true;
            }
        } while (badExecution);

        for (com.epam.lab.spider.model.vk.Post vkPost : postsOnTargetWall) {
            boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));
            if (alreadyProceededPost) {
                LOG.debug("Post wall" + owner.getVkId() + "_" + vkPost.getId() + " already processed.");
            } else {
                postsPrepareToPosting.add(vkPost);
            }
        }
        return postsPrepareToPosting;
    }

}
