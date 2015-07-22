package com.epam.lab.spider.job.limit;

import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.entity.UserActions;
import com.epam.lab.spider.model.db.service.UserActionsService;

/**
 * Created by hell-engine on 7/22/2015.
 */
public class SimpleUserLimit implements UserLimit {
    private static  UserActionsService actionsService = new UserActionsService();
    private static UserLimitUtil userLimitUtil = new HardCodedUserLimitUtilImpl();
    @Override
    public void markTaskExecute(User user) {
        UserActions actions = actionsService.getByUserId(user.getId());
        actions.setTaskExecuteCount(actions.getTaskExecuteCount() + 1);
        actionsService.save(actions);
    }

    @Override
    public void markPostExecute(User user) {
        UserActions actions = actionsService.getByUserId(user.getId());
        actions.setPostExecuteCount(actions.getPostExecuteCount() + 1);
        actionsService.save(actions);
    }

    @Override
    public void markAttachmentExecute(User user) {
        UserActions actions = actionsService.getByUserId(user.getId());
        actions.setAttachmentExecuteCount(actions.getAttachmentExecuteCount() + 1);
        actionsService.save(actions);
    }

    @Override
    public boolean checkTaskExecute(User user) {
        UserActions actions = actionsService.getByUserId(user.getId());
        return actions.getTaskExecuteCount() < userLimitUtil.getTaskExecuteLimit(user) ;
    }

    @Override
    public boolean checkPostExecute(User user) {
        UserActions actions = actionsService.getByUserId(user.getId());
        return actions.getPostExecuteCount() < userLimitUtil.getPostExecuteLimit(user) ;
    }

    @Override
    public boolean checkAttachmentExecute(User user) {
        UserActions actions = actionsService.getByUserId(user.getId());
        return actions.getAttachmentExecuteCount() < userLimitUtil.getAttachmentExecuteLimit(user) ;
    }

    @Override
    public void markAttachmentTraffic(User user, int traffic) {
        UserActions actions = actionsService.getByUserId(user.getId());
        actions.setAttachmentTraffic(actions.getAttachmentTraffic() + traffic);
        actionsService.save(actions);
    }

    @Override
    public boolean checkAttachmentTraffic(User user) {
        UserActions actions = actionsService.getByUserId(user.getId());
        return actions.getAttachmentTraffic() < userLimitUtil.getAttachmentTrafficLimit(user) ;
    }

    @Override
    public boolean checkAttachmentTraffic(User user, int withNextTraffic) {
        UserActions actions = actionsService.getByUserId(user.getId());
        return actions.getAttachmentTraffic() + withNextTraffic < userLimitUtil.getAttachmentTrafficLimit(user) ;
    }
}
