package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.entity.Filter;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.vk.Post;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by shell on 6/28/2015.
 */
public class GrabbingTypeServerUtil {
    public static final Logger LOG = Logger.getLogger(GrabbingTypeServerUtil.class);

    public static boolean checkFilterPass(Post vkPost, Filter filter, Long timeOnServer) {
        if(filter==null) return true;
        boolean qualityCondition = true;
        if(filter.getLikes()!=null)     qualityCondition &= vkPost.getLikes().getCount() >= filter.getLikes().intValue();
        if(filter.getReposts()!=null)   qualityCondition &= vkPost.getReposts().getCount() >= filter.getLikes().intValue();
        if(filter.getComments()!=null)  qualityCondition &= vkPost.getComments().getCount() >= filter.getComments().intValue();
        return  qualityCondition;
    }

    public static List<Post> grabbingBegin(Owner owner, Vkontakte vk, Filter filter, Set<Integer> alreadyAddSet, int countOfPosts, int grabbingSize) throws InterruptedException, VKException {
        long unixTime = vk.utils().getServerTime().getTime();
        List<Post> postsPrepareToPosting = new ArrayList<>();
        Parameters parameters;
        postGrabbingAndFiltering:
        for(int loopsCount = 0,currentPostCount = 0;true;loopsCount++) {
            parameters = new Parameters();
            parameters.add("owner_id", owner.getVkId());
            parameters.add("filter", "owner");
            parameters.add("count", grabbingSize);
            parameters.add("offset", loopsCount*grabbingSize);
            List<Post> postsOnTargetWall = null;
            boolean manyRequest = false;
            do {
                try {
                    if (manyRequest) {
                        Thread.sleep(400);
                        manyRequest = false;
                    }
                    postsOnTargetWall = vk.wall().get(parameters);
                } catch (VKException x) {
                    if(x.getExceptionCode()==VKException.VK_MANY_REQUESTS)manyRequest=true;
                    else throw x;
                }
                // фікс кривої архітектури
                // перехоплення NullPointerException by UnknownHostException
                catch (NullPointerException x){
                    Thread.sleep(500);
                }
            }while (manyRequest);
            Thread.sleep(300);

            for (com.epam.lab.spider.model.vk.Post vkPost : postsOnTargetWall) {
                boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));
                if (alreadyProceededPost) {
                    LOG.debug("Post " + owner.getVkId() + "_" + vkPost.getId() + " already processed.");
                } else {
                    boolean filterPass = checkFilterPass(vkPost, filter, unixTime);
                    if (filterPass) {
                        postsPrepareToPosting.add(vkPost);
                        currentPostCount++;
                        if(currentPostCount>=countOfPosts)break postGrabbingAndFiltering;
                    }else {
                        LOG.trace("Post " + owner.getVkId() + "_" + vkPost.getId() + " has failed filter.");
                    }
                }
            }
            if(postsOnTargetWall.size()<grabbingSize){
                LOG.debug("End of post at owner#"+owner.getVkId());
                break postGrabbingAndFiltering;
            }
        }
        return postsPrepareToPosting;
    }
    public static List<Post> grabbingEnd(Owner owner, Vkontakte vk, Filter filter, Set<Integer> alreadyAddSet, int countOfPosts, int grabbingSize) throws InterruptedException, VKException {
        long unixTime = vk.utils().getServerTime().getTime();
       // TODO: BIND LOCAL GRABBING SIZE AND GRABBING SIZE
        int localGrabbingSize = 90;
        boolean endOfContent = false;
        List<Post> postsPrepareToPosting = new ArrayList<>();
        Parameters parameters = null;
        Integer sourceWallPostCount = null;
        Integer offsetOnWall = null;
        postGrabbingAndFiltering:
        for(int currentPostCount = 0;true;offsetOnWall=-localGrabbingSize) {
            List<Post> postsOnTargetWall = null;
            boolean manyRequest = false, badExecution = false;
            do {
                try {
                    if (manyRequest|| badExecution) {
                        Thread.sleep(400);
                        manyRequest = false;
                        badExecution = false;
                    }
                    if(sourceWallPostCount == null){
                        sourceWallPostCount = vk.wall().getCount(owner.getVkId());
                        if(sourceWallPostCount == 0){
                            LOG.debug("Empty owner#"+owner.getVkId());
                            break postGrabbingAndFiltering;
                        }
                    }
                    if(sourceWallPostCount != null && offsetOnWall == null){
                        if(sourceWallPostCount > localGrabbingSize){
                            offsetOnWall = sourceWallPostCount - localGrabbingSize;
                        }else{
                            offsetOnWall = 0;
                            localGrabbingSize = sourceWallPostCount;
                        }
                    }
                    if(sourceWallPostCount != null && offsetOnWall != null && offsetOnWall <0){
                        int diff = offsetOnWall + localGrabbingSize;
                        offsetOnWall = 0;
                        localGrabbingSize = diff;
                        endOfContent = true;
                    }
                    if(sourceWallPostCount != null && offsetOnWall != null && offsetOnWall >=0){
                        parameters = new Parameters();
                        parameters.add("owner_id", owner.getVkId());
                        parameters.add("filter", "owner");
                        parameters.add("count", localGrabbingSize);
                        parameters.add("offset", offsetOnWall);
                    }
                    postsOnTargetWall = vk.wall().get(parameters);
                } catch (VKException x) {
                    if(x.getExceptionCode()==VKException.VK_MANY_REQUESTS)manyRequest=true;
                    else throw x;
                }
                // фікс архітектури
                // перехоплення NullPointerException by UnknownHostException
                catch (NullPointerException x){
                    badExecution = true;
                }
            }while (manyRequest || badExecution);


            for (int i = postsOnTargetWall.size(); i > 0; i--) {
                com.epam.lab.spider.model.vk.Post vkPost = postsOnTargetWall.get(i-1);
                boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));
                if (alreadyProceededPost) {
                    LOG.debug("Post " + owner.getVkId() + "_" + vkPost.getId() + " already processed.");
                } else {
                    boolean filterPass;
                    filterPass = checkFilterPass(vkPost, filter, unixTime);
                    if (filterPass){
                        postsPrepareToPosting.add(vkPost);
                        currentPostCount++;
                        if(currentPostCount>=countOfPosts)break postGrabbingAndFiltering;
                    }else {
                        LOG.debug("Post " + owner.getVkId() + "_" + vkPost.getId() + " has failed filter.");
                    }
                }
            }
            if(endOfContent){
                LOG.debug("End of post at owner#"+owner.getVkId());
                break postGrabbingAndFiltering;
            }
        }
        return postsPrepareToPosting;
    }
    /**
     * Увага дана реалізація може стягнути максимум 80% постів
     * Дане обмеження встановлене з метою швидкодії по методу паретто
     */
    public static List<Post> grabbingRandom(Owner owner, Vkontakte vk, Filter filter, Set<Integer> alreadyAddSet, int countOfPosts, int grabbingSize, Set<Integer> randomSaved) throws InterruptedException, VKException {
        long unixTime = vk.utils().getServerTime().getTime();
        int parrettoPostCount = 0;
        Set<Integer> alreadyRandom;
        if(randomSaved != null){
            alreadyRandom = randomSaved;
        }else {
            alreadyRandom = new HashSet<>();
        }
//        boolean endOfContent = false;
        List<Post> postsPrepareToPosting = new ArrayList<>();
        Parameters parameters = null;
        Integer sourceWallPostCount = null;
        Integer offsetOnWall = null;
        postGrabbingAndFiltering:
        for(int currentPostCount = 0;true;) {
            List<Post> postsOnTargetWall = null;
            boolean manyRequest = false, badExecution = false, prevSuccessGrabbing = true;
            do {
                try {
                    if (manyRequest|| badExecution) {
                        Thread.sleep(400);
                        manyRequest = false;
                        badExecution = false;
                    }
                    if(sourceWallPostCount == null){
                        sourceWallPostCount = vk.wall().getCount(owner.getVkId());
                        parrettoPostCount = (int) (sourceWallPostCount*0.8);
                        if(sourceWallPostCount == 0){
                            LOG.debug("Empty owner#"+owner.getVkId());
                            break postGrabbingAndFiltering;
                        }
                    }
                    if(sourceWallPostCount != null && prevSuccessGrabbing){
                        Random random = new Random();
                        boolean searching = true;
                        do{
                            Integer candidate = random.nextInt(sourceWallPostCount);
                            if(!alreadyRandom.contains(candidate)){
                                alreadyRandom.add(candidate);
                                searching = false;
                                offsetOnWall = candidate;
                            }else if (alreadyRandom.size() >= parrettoPostCount) {
                                LOG.debug("Random method post limit at owner#"+owner.getVkId());
                                break postGrabbingAndFiltering;
                            }
                        }while (searching);
                        prevSuccessGrabbing = false;
                    }

                    if(sourceWallPostCount != null && offsetOnWall != null){
                        parameters = new Parameters();
                        parameters.add("owner_id", owner.getVkId());
                        parameters.add("filter", "owner");
                        parameters.add("count", 1);
                        parameters.add("offset", offsetOnWall);
                    }
                    postsOnTargetWall = vk.wall().get(parameters);
                    prevSuccessGrabbing = true;
                } catch (VKException x) {
                    if(x.getExceptionCode()==VKException.VK_MANY_REQUESTS)manyRequest=true;
                    else throw x;
                }
                // фікс архітектури
                // перехоплення NullPointerException by UnknownHostException
                catch (NullPointerException x){
                    badExecution = true;
                }
            }while (manyRequest || badExecution);

            for (int i = postsOnTargetWall.size(); i > 0; i--) {
                com.epam.lab.spider.model.vk.Post vkPost = postsOnTargetWall.get(i-1);
                boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));
                if (alreadyProceededPost) {
                    LOG.debug("Post " + owner.getVkId() + "_" + vkPost.getId() + " already processed.");
                } else {
                    boolean filterPass = checkFilterPass(vkPost, filter, unixTime);
                    if (filterPass){
                        postsPrepareToPosting.add(vkPost);
                        currentPostCount++;
                        if(currentPostCount>=countOfPosts)break postGrabbingAndFiltering;
                    }else {
                        LOG.debug("Post " + owner.getVkId() + "_" + vkPost.getId() + " has failed filter.");
                    }
                }
            }

//            if(endOfContent){
//                LOG.debug("End of post at owner#"+owner.getVkId());
//                break postGrabbingAndFiltering;
//            }
        }
        return postsPrepareToPosting;
    }

}
