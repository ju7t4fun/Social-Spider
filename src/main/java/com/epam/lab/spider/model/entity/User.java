package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

import java.util.Date;
import java.util.Set;

/**
 * @author Yura Kovalik
 */
public interface User extends PersistenceIdentifiable {
    Set<Task> getTasks();

    void setTasks(Set<Task> tasks);

    boolean addTask(Task task);

    boolean removeTask(Task task);

    Set<Profile> getProfiles();

    void setProfiles(Set<Profile> profiles);

    boolean addProfile(Profile profile);

    boolean removeProfile(Profile profile);

    Integer getId();


    String getName();

    void setName(String name);

    String getSurname();

    void setSurname(String surname);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    Date getCreateTime();

    void setCreateTime(Date createTime);

    Role getRole();

    void setRole(Role role);

    State getState();

    void setState(State state);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    String getAvatarURL();

    void setAvatarURL(String avatarURL);

    int getRemainderTaskExecute();

    int getRemainderPostExecute();

    int getRemainderTaskExecuteInPercent();

    int getRemainderPostExecuteInPercent();

    enum Role {
        ADMIN, USER
    }

    enum State {
        CREATED, ACTIVATED, BANNED
    }
}
