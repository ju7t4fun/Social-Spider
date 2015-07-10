package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.entity.Filter;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.SynchronizedData;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.vk.Post;
import com.epam.lab.spider.model.vk.PostOffsetDecorator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by hell-engine on 7/10/2015.
 */
public class GrabbingTypeVkSavedSyncUtil {
    public static final Logger LOG = Logger.getLogger(GrabbingTypeServerUtil.class);
    public static List<Post> grabbing(Task.GrabbingType type,Owner owner, Vkontakte vk, Filter filter, SynchronizedData sync, Set<Integer> alreadyAddSet, int countOfPosts) throws InterruptedException, VKException {
        List<PostOffsetDecorator> postsPrepareToPosting = new ArrayList<>();
        Integer lastVkId;
        Integer lastOffset;
        if(sync != null) {
            lastVkId = sync.getPostVkId();
            lastOffset = sync.getPostOffset();
        }else {
            lastVkId = 0;
            lastOffset = 0;
        }
        for(int loopsCount = 0;true;loopsCount++) {
            List<PostOffsetDecorator> postsOnTargetWall;
            // get data
            postsOnTargetWall = badRequestProtectGrabbing(type,vk, owner.getVkId(), null, lastVkId, lastOffset, filter);
            boolean synchronization = false;
            if(!postsOnTargetWall.isEmpty()){
                PostOffsetDecorator vkPost = postsOnTargetWall.get(0);
                if(vkPost.getOffset().equals(lastOffset) && vkPost.getId() == lastVkId) synchronization = true;
            }
            if (!synchronization){
                for (PostOffsetDecorator vkPost : postsOnTargetWall) {
                    boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));
                    if (alreadyProceededPost) {
                        LOG.debug("Post " + owner.getVkId() + "_" + vkPost.getId() + " already processed.");
                    } else {
                        postsPrepareToPosting.add(vkPost);
                    }
                }
            }else {
                for (int i = 1; i < postsOnTargetWall.size(); i++) {
                    postsPrepareToPosting.add(postsOnTargetWall.get(i));
                }
            }
            if(postsPrepareToPosting.size()>countOfPosts){
                LOG.info("Complete grabbing of post at owner#" + owner.getVkId() + " loops count " + loopsCount + ".");
                break;
            }else{
                PostOffsetDecorator lastPost = postsPrepareToPosting.get(postsPrepareToPosting.size() - 1);
                lastVkId = lastPost.getId();
                lastOffset = lastPost.getOffset();
            }
        }
        return ((List<Post>)((List<? extends Post>)postsPrepareToPosting));
    }

    private static List<PostOffsetDecorator> badRequestProtectGrabbing(Task.GrabbingType grabbingType, Vkontakte vk, Integer ownerId, Integer count, Integer lastPostId, Integer offset, Filter filter) throws InterruptedException, VKException {
        boolean  badExecution = false;
        int currentAttempt = 0;
        int totalAttempt = 3;
        List<PostOffsetDecorator> postsOnTargetWall = null;
        do {
            try {
                if (badExecution) {
                    Thread.sleep(400);
                    badExecution = false;
                } else
                    postsOnTargetWall = simpleGrabbing(grabbingType, vk, ownerId, count, lastPostId, offset, filter);
            } catch (VKException x) {
                if(x.getExceptionCode() == VKException.VK_EXECUTE_RUNTIME_ERROR){
                    if(currentAttempt<totalAttempt) {
                        badExecution = true;
                        currentAttempt++;
                        LOG.fatal("ТЕРМІНОВО ФІКСИТЬ КОД! Спроба відновлення #"+currentAttempt, x);
                        if(currentAttempt == 1)lastPostId = null;
                        else if(currentAttempt == 2)offset = null;
                        else if(currentAttempt == 3)count = 3;
                    }else {
                        throw x;
                    }
                }
                else throw x;
            }
        }while (badExecution);
        return postsOnTargetWall;
    }
    private static List<PostOffsetDecorator> simpleGrabbing(Task.GrabbingType grabbingType, Vkontakte vk, Integer ownerId, Integer count, Integer lastPostId, Integer offset, Filter filter) throws VKException {
        switch (grabbingType) {
            case BEGIN:
                return vk.execute().getPostFromBeginWall(ownerId, count, lastPostId, offset, filter);
            case END:
                return vk.execute().getPostFromBeginWall(ownerId, count, lastPostId, offset, filter);
            case RANDOM:
            case NEW:
                LOG.fatal("UNSUPPORTED METHOD");
                break;
        }
        return null;
    }
}
