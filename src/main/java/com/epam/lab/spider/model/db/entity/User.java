package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.controller.utils.validation.annotation.NotNull;
import com.epam.lab.spider.controller.utils.validation.annotation.Pattern;
import com.epam.lab.spider.controller.utils.validation.annotation.Size;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.TaskService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class User {

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final TaskService taskService = factory.create(TaskService.class);
    private static final ProfileService profileService = factory.create(ProfileService.class);

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


    public User() {

    }

    public enum Role {
        ADMIN, USER
    }

    public enum State {
        CREATED, ACTIVATED, BANNED
    }

    public Set<Task> getTasks() {
        if (tasks == null) {
            if (id == null)
                tasks = new HashSet<>();
            else
                tasks = new HashSet<>(taskService.getByUserId(id));
        }
        return tasks;
    }

    public boolean addTask(Task task) {
        return getTasks().add(task);
    }


    public boolean removeTask(Task task) {
        return getTasks().remove(task);
    }

    public Set<Profile> getProfiles() {
        if (profiles == null) {
            if (id == null)
                profiles = new HashSet<>();
            else
                profiles = new HashSet<>(profileService.getByUserId(id));
        }
        return profiles;
    }

    public boolean addProfile(Profile profile) {
        return getProfiles().add(profile);
    }

    public boolean removeProfile(Profile profile) {
        return getProfiles().remove(profile);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

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

}
