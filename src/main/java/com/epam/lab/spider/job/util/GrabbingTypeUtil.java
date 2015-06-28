package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.entity.Filter;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.vk.Post;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by shell on 6/28/2015.
 */
public class GrabbingTypeUtil {
    public static final Logger LOG = Logger.getLogger(GrabbingTypeUtil.class);
    public static List<Post> grabbingBegin(Owner owner, Vkontakte vk, Filter filter, Set<Integer> alreadyAddSet, int grabbingSize, int countOfPosts) throws InterruptedException, VKException {
        List<Post> postsPrepareToPosting = new ArrayList<>();
        Parameters parameters;
        postGrabbingAndFiltering:
        for(int loopsCount = 0,currentPostCount = 0;true;loopsCount++) {
            parameters = new Parameters();
            parameters.add("owner_id", owner.getVk_id());
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
            }while (manyRequest);
            long unixTime = vk.utils().getServerTime().getTime();
            for (com.epam.lab.spider.model.vk.Post vkPost : postsOnTargetWall) {
                boolean alreadyProceededPost = alreadyAddSet.contains(new Integer(vkPost.getId()));
                if (alreadyProceededPost) {
                    LOG.debug("Post " + owner.getVk_id() + "_" + vkPost.getId() + " already processed.");
                } else {
                    boolean qualityCondition = true;
                    if(filter.getLikes()!=null)     qualityCondition &= vkPost.getLikes().getCount() >= filter.getLikes().intValue();
                    if(filter.getReposts()!=null)   qualityCondition &= vkPost.getReposts().getCount() >= filter.getLikes().intValue();
                    if(filter.getComments()!=null)  qualityCondition &= vkPost.getComments().getCount() >= filter.getComments().intValue();
//                    unixTime vkPost.getDate().getTime();
                    if (qualityCondition) {
                        postsPrepareToPosting.add(vkPost);
                        currentPostCount++;
                        if(!(currentPostCount<countOfPosts))break postGrabbingAndFiltering;
                    }else {
                        LOG.debug("Post " + owner.getVk_id() + "_" + vkPost.getId() + " has failed filter.");
                    }
                }
            }
        }
        return postsPrepareToPosting;
    }

}
