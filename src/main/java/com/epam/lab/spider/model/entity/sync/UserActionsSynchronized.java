package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.UserActions;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class UserActionsSynchronized extends EntitySynchronized<UserActions> implements UserActions {

    public UserActionsSynchronized(UserActions entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public Integer getId() {
        return getEntity().getId();
    }

    public Integer getUserId() {
        return getEntity().getUserId();
    }

    public void setUserId(Integer userId) {
        setUnSynchronized();
        getEntity().setUserId(userId);
    }

    public Integer getTaskExecuteCount() {
        return getEntity().getTaskExecuteCount();
    }

    public void setTaskExecuteCount(Integer taskRun) {
        setUnSynchronized();
        getEntity().setTaskExecuteCount(taskRun);
    }

    public Integer getPostExecuteCount() {
        return getEntity().getPostExecuteCount();
    }

    public void setPostExecuteCount(Integer postCount) {
        setUnSynchronized();
        getEntity().setPostExecuteCount(postCount);
    }

    public Integer getAttachmentExecuteCount() {
        return getEntity().getAttachmentExecuteCount();
    }

    public void setAttachmentExecuteCount(Integer attachmentCount) {
        setUnSynchronized();
        getEntity().setAttachmentExecuteCount(attachmentCount);
    }

    public Integer getAttachmentTraffic() {
        return getEntity().getAttachmentTraffic();
    }

    public void setAttachmentTraffic(Integer attachmentTraffic) {
        setUnSynchronized();
        getEntity().setAttachmentTraffic(attachmentTraffic);
    }
}
