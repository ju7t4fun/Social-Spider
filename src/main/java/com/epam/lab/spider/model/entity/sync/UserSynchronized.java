package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.User;

import java.util.Date;
import java.util.Set;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class UserSynchronized extends EntitySynchronized<User> implements User {
    public UserSynchronized(User entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public Set<Task> getTasks() {
        return getEntity().getTasks();
    }

    public void setTasks(Set<Task> tasks) {
        setUnSynchronized();
        getEntity().setTasks(tasks);
    }

    public boolean addTask(Task task) {
        return getEntity().addTask(task);
    }

    public boolean removeTask(Task task) {
        return getEntity().removeTask(task);
    }

    public Set<Profile> getProfiles() {
        return getEntity().getProfiles();
    }

    public void setProfiles(Set<Profile> profiles) {
        setUnSynchronized();
        getEntity().setProfiles(profiles);
    }

    public boolean addProfile(Profile profile) {
        return getEntity().addProfile(profile);
    }

    public boolean removeProfile(Profile profile) {
        return getEntity().removeProfile(profile);
    }

    public Integer getId() {
        return getEntity().getId();
    }

    public String getName() {
        return getEntity().getName();
    }

    public void setName(String name) {
        setUnSynchronized();
        getEntity().setName(name);
    }

    public String getSurname() {
        return getEntity().getSurname();
    }

    public void setSurname(String surname) {
        setUnSynchronized();
        getEntity().setSurname(surname);
    }

    public String getEmail() {
        return getEntity().getEmail();
    }

    public void setEmail(String email) {
        setUnSynchronized();
        getEntity().setEmail(email);
    }

    public String getPassword() {
        return getEntity().getPassword();
    }

    public void setPassword(String password) {
        setUnSynchronized();
        getEntity().setPassword(password);
    }

    public Date getCreateTime() {
        return getEntity().getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        setUnSynchronized();
        getEntity().setCreateTime(createTime);
    }

    public Role getRole() {
        return getEntity().getRole();
    }

    public void setRole(Role role) {
        setUnSynchronized();
        getEntity().setRole(role);
    }

    public State getState() {
        return getEntity().getState();
    }

    public void setState(State state) {
        setUnSynchronized();
        getEntity().setState(state);
    }

    public Boolean getDeleted() {
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        setUnSynchronized();
        getEntity().setDeleted(deleted);
    }

    public String getAvatarURL() {
        return getEntity().getAvatarURL();
    }

    public void setAvatarURL(String avatarURL) {
        setUnSynchronized();
        getEntity().setAvatarURL(avatarURL);
    }

    public int getRemainderTaskExecute() {
        return getEntity().getRemainderTaskExecute();
    }

    public int getRemainderPostExecute() {
        return getEntity().getRemainderPostExecute();
    }

    public int getRemainderTaskExecuteInPercent() {
        return getEntity().getRemainderTaskExecuteInPercent();
    }

    public int getRemainderPostExecuteInPercent() {
        return getEntity().getRemainderPostExecuteInPercent();
    }
}
