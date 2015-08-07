package com.epam.lab.spider.job.limit.impl;


import com.epam.lab.spider.controller.websocket.LimitsInfo;
import com.epam.lab.spider.job.limit.UserLimitProcessor;
import com.epam.lab.spider.job.limit.UserLimits;
import com.epam.lab.spider.model.entity.UserActions;
import com.epam.lab.spider.persistence.service.UserActionsService;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yura Kovalik
 */
public class SimpleUserLimitProcessor implements UserLimitProcessor {
    private static  UserActionsService actionsService = new UserActionsService();
    private static UserLimits userLimits = new HardCodedUserLimits();

    private static Map<Integer,SoftReference<UserActions>> cacheMap =  new ConcurrentHashMap();

    public SimpleUserLimitProcessor() {
    }

    private static UserActions getUserActions(Integer userId){
        SoftReference<UserActions> ref;
        UserActions actions;
        ref = cacheMap.get(userId);
        if(ref!=null) {
            actions = ref.get();
            if(actions != null)return actions;
        }
        return actionsService.getByUserId(userId);
    }

    @Override
    public void markTaskExecute(Integer userId) {
        UserActions actions = getUserActions(userId);
        int value, percent;
        actions.setTaskExecuteCount(value = actions.getTaskExecuteCount() + 1);
        value = userLimits.getTaskExecuteLimit(userId) - value;
        percent = (int) (value * 100. / userLimits.getTaskExecuteLimit(userId));
        LimitsInfo.sendTaskLimitInfo(userId,value,percent);
        actionsService.save(actions);
    }

    @Override
    public void markPostExecute(Integer userId) {
        UserActions actions = getUserActions(userId);
        int value, percent;
        actions.setPostExecuteCount(value = actions.getPostExecuteCount() + 1);
        value = userLimits.getPostExecuteLimit(userId) - value;
        percent = (int) (value * 100. / userLimits.getPostExecuteLimit(userId));
        actionsService.save(actions);
        LimitsInfo.sendPostLimitInfo(userId, value, percent);
    }

    @Override
    public void markAttachmentExecute(Integer userId) {
        UserActions actions = getUserActions(userId);
        actions.setAttachmentExecuteCount(actions.getAttachmentExecuteCount() + 1);
        actionsService.save(actions);
    }

    @Override
    public boolean checkTaskExecute(Integer userId) {
        UserActions actions = getUserActions(userId);
        return actions.getTaskExecuteCount() < userLimits.getTaskExecuteLimit(userId);
    }

    @Override
    public boolean checkPostExecute(Integer userId) {
        UserActions actions = getUserActions(userId);
        return actions.getPostExecuteCount() < userLimits.getPostExecuteLimit(userId);
    }

    @Override
    public boolean checkAttachmentExecute(Integer userId) {
        UserActions actions = getUserActions(userId);
        return actions.getAttachmentExecuteCount() < userLimits.getAttachmentExecuteLimit(userId);
    }

    @Override
    public int getRemainderTaskExecute(Integer userId) {
        UserActions actions = getUserActions(userId);
        int remainder = userLimits.getTaskExecuteLimit(userId) - actions.getTaskExecuteCount();
        return remainder>0?remainder:0;
    }

    @Override
    public int getRemainderPostExecute(Integer userId) {
        UserActions actions = getUserActions(userId);
        int remainder = userLimits.getPostExecuteLimit(userId) - actions.getPostExecuteCount();
        return remainder>0?remainder:0;
    }

    @Override
    public int getRemainderAttachmentExecute(Integer userId) {
        UserActions actions = getUserActions(userId);
        int remainder = userLimits.getAttachmentExecuteLimit(userId) - actions.getAttachmentExecuteCount();
        return remainder>0?remainder:0;
    }

    @Override
    public int getRemainderTaskExecuteInPercent(Integer userId) {
        return (int) ((getRemainderTaskExecute(userId) * 100.) / userLimits.getTaskExecuteLimit(userId));
    }

    @Override
    public int getRemainderPostExecuteInPercent(Integer userId) {
        return (int) ((getRemainderPostExecute(userId) * 100.) / userLimits.getPostExecuteLimit(userId));
    }

    @Override
    public int getRemainderAttachmentExecuteInPercent(Integer userId) {
        return (int) ((getRemainderAttachmentExecute(userId) * 100.) / (userLimits.getAttachmentExecuteLimit(userId) * 100));
    }

    @Override
    public void markAttachmentTraffic(Integer userId, int traffic) {
        UserActions actions = getUserActions(userId);
        actions.setAttachmentTraffic(actions.getAttachmentTraffic() + traffic);
        actionsService.save(actions);
    }

    @Override
    public boolean checkAttachmentTraffic(Integer userId) {
        UserActions actions = getUserActions(userId);
        return actions.getAttachmentTraffic() < userLimits.getAttachmentTrafficLimit(userId);
    }

    @Override
    public boolean checkAttachmentTraffic(Integer userId, int withNextTraffic) {
        UserActions actions = getUserActions(userId);
        return (actions.getAttachmentTraffic() + withNextTraffic) < userLimits.getAttachmentTrafficLimit(userId);
    }

    @Override
    public int getRemainderAttachmentTraffic(Integer userId) {
        UserActions actions = getUserActions(userId);
        int remainder = userLimits.getAttachmentTrafficLimit(userId) - actions.getAttachmentTraffic();
        return remainder>0?remainder:0;
    }

    @Override
    public int getRemainderAttachmentTraffic(Integer userId, int withNextTraffic) {
        UserActions actions = getUserActions(userId);
        int remainder = userLimits.getAttachmentTrafficLimit(userId) - (actions.getAttachmentTraffic() + withNextTraffic);
        return remainder>0?remainder:0;
    }

    @Override
    public int getRemainderAttachmentTrafficInPercent(Integer userId) {
        return (int) ((getRemainderAttachmentTraffic(userId) * 100.) / (userLimits.getAttachmentTrafficLimit(userId) * 100));
    }

    @Override
    public int getRemainderAttachmentTrafficInPercent(Integer userId, int withNextTraffic) {
        return (int) ((getRemainderAttachmentTraffic(userId, withNextTraffic) * 100.) / (userLimits.getAttachmentTrafficLimit(userId) * 100));
    }
}
