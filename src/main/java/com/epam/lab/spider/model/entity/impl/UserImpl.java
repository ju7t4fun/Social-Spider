package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.controller.utils.validation.annotation.NotNull;
import com.epam.lab.spider.controller.utils.validation.annotation.Pattern;
import com.epam.lab.spider.controller.utils.validation.annotation.Size;
import com.epam.lab.spider.job.limit.UserLimitProcessor;
import com.epam.lab.spider.job.limit.UserLimitsFactory;
import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.User;

import java.util.Date;
import java.util.Set;


/**
 * @author Boyarsky Vitaliy
 */
public class UserImpl implements Comparable<User>, User, PersistenceIdentificationChangeable {

    public static UserLimitProcessor limit = UserLimitsFactory.getUserLimitProcessor();
    private Integer id;
    @NotNull(message = "Name can not be null")
    @Size(min = 4, max = 16)
    private String name;
    private String surname;
    @NotNull(message = "Email can not be null")
    @Size(max = 255)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$", message = "Fail Email format")
    private String email;
    private String password;
    private Date createTime;
    private Role role = Role.USER;
    private State state = State.CREATED;
    private Boolean deleted = false;
    private String avatarURL;
    private Set<Profile> profiles = null;
    private Set<Task> tasks = null;

    @Override
    public int compareTo(User o) {
        return o.getId().compareTo(id);
    }

    @Override
    public Set<Task> getTasks() {
        return tasks;
    }

    @Override
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean addTask(Task task) {
        return getTasks().add(task);
    }

    @Override
    public boolean removeTask(Task task) {
        return getTasks().remove(task);
    }

    @Override
    public Set<Profile> getProfiles() {
        return profiles;
    }

    @Override
    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public boolean addProfile(Profile profile) {
        return getProfiles().add(profile);
    }

    @Override
    public boolean removeProfile(Profile profile) {
        return getProfiles().remove(profile);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String getAvatarURL() {
        return avatarURL;
    }

    @Override
    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", role=" + role +
                ", state=" + state +
                ", deleted=" + deleted +
                ", profiles=" + profiles +
                ", tasks=" + tasks +
                ", avatarURL=" + avatarURL +
                '}';
    }

    @Override
    public int getRemainderTaskExecute() {
        return limit.getRemainderTaskExecute(this.getId());
    }

    @Override
    public int getRemainderPostExecute() {
        return limit.getRemainderPostExecute(this.getId());
    }

    @Override
    public int getRemainderTaskExecuteInPercent() {
        return limit.getRemainderTaskExecuteInPercent(this.getId());
    }

    @Override
    public int getRemainderPostExecuteInPercent() {
        return limit.getRemainderPostExecuteInPercent(this.getId());
    }
}
