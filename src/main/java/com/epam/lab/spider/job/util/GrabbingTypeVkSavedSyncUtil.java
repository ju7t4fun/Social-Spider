package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.entity.Filter;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.SynchronizedData;
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
    public static List<Post> grabbingBegin(Owner owner, Vkontakte vk, Filter filter, SynchronizedData sync, Set<Integer> alreadyAddSet, int countOfPosts) throws InterruptedException, VKException {
        List<PostOffsetDecorator> postsPrepareToPosting = new ArrayList<>();
        Integer lastVkId;
        Integer lastOffset;
        if(sync != null) {
            lastVkId = sync.getPostVkId();
            lastOffset = sync.getPostOffset();
        }else {
            lastVkId = null;
            lastOffset = null;
        }
        postGrabbingAndFiltering:
        for(int loopsCount = 0;true;loopsCount++) {
            List<PostOffsetDecorator> postsOnTargetWall;
            boolean  badExecution = false;
            do {
                try {
                    if (badExecution) {
                        Thread.sleep(400);
                        badExecution = false;
                    }
                    postsOnTargetWall = vk.execute().getPostFromBeginWall(owner.getVkId(), null, lastVkId, lastOffset, filter);
                } catch (VKException x) {
                    throw x;
                }
            }while (badExecution);
            boolean synchronization = false;
            if(!postsOnTargetWall.isEmpty()){
                PostOffsetDecorator vkPost = postsOnTargetWall.get(0);
                if(vkPost.getOffset() == lastOffset && vkPost.getId() == lastVkId) synchronization = true;
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
                break postGrabbingAndFiltering;
            }else{
                PostOffsetDecorator lastPost = postsPrepareToPosting.get(postsPrepareToPosting.size() - 1);
                lastVkId = lastPost.getId();
                lastOffset = lastPost.getOffset();
            }
        }
        return ((List<Post>)((List<? extends Post>)postsPrepareToPosting));
    }
    public static List<Post> grabbingEnd(Owner owner, Vkontakte vk, Filter filter, SynchronizedData sync, Set<Integer> alreadyAddSet, int countOfPosts) throws InterruptedException, VKException {
        List<PostOffsetDecorator> postsPrepareToPosting = new ArrayList<>();
        Integer lastVkId;
        Integer lastOffset;
        if(sync != null) {
            lastVkId = sync.getPostVkId();
            lastOffset = sync.getPostOffset();
        }else {
            lastVkId = null;
            lastOffset = null;
        }
        postGrabbingAndFiltering:
        for(int loopsCount = 0;true;loopsCount++) {
            List<PostOffsetDecorator> postsOnTargetWall;
            boolean  badExecution = false;
            do {
                try {
                    if (badExecution) {
                        Thread.sleep(400);
                        badExecution = false;
                    }
                    postsOnTargetWall = vk.execute().getPostFromEndWall(owner.getVkId(), null, lastVkId, lastOffset, filter);
                } catch (VKException x) {
                    throw x;
                }
            }while (badExecution);
            boolean synchronization = false;
            if(!postsOnTargetWall.isEmpty()){
                PostOffsetDecorator vkPost = postsOnTargetWall.get(0);
                if(vkPost.getOffset() == lastOffset && vkPost.getId() == lastVkId) synchronization = true;
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
                LOG.info("Complete grabbing of post at owner#"+owner.getVkId()+" loops count "+loopsCount+"." );
                break postGrabbingAndFiltering;
            }else{
                PostOffsetDecorator lastPost = postsPrepareToPosting.get(postsPrepareToPosting.size() - 1);
                lastVkId = lastPost.getId();
                lastOffset = lastPost.getOffset();
            }
        }
        return ((List<Post>)((List<? extends Post>)postsPrepareToPosting));
    }
}
