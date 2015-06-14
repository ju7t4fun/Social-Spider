package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.service.ProfileService;
import com.epam.lab.spider.model.service.ServiceFactory;
import com.epam.lab.spider.model.service.TaskService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class User {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date createTime;
    private Role role;
    private Boolean deleted = false;
    private Boolean confirm = false;
    private Set<Profile> profiles = null;
    private Set<Task> tasks = null;

    public Set<Task> getTasks() {
        if (tasks == null) {
            if (id == null) {
                return new HashSet<>();
            }
            ServiceFactory factory = ServiceFactory.getInstance();
            TaskService service = factory.create(TaskService.class);
            tasks = new HashSet<>(service.getByUserId(id));
        }
        return tasks;
    }

    public Set<Profile> getProfiles() {
        if (profiles == null) {
            if (id == null) {
                return new HashSet<>();
            }
            ServiceFactory factory = ServiceFactory.getInstance();
            ProfileService service = factory.create(ProfileService.class);
            profiles = new HashSet<>(service.getByUserId(id));
        }
        return profiles;
    }

    public enum Role {

        ADMIN(1), USER(2);

        private int id;

        Role(int id) {
            this.id = id;
        }

        public static Role getById(int id) {
            Role[] roles = values();
            for (Role role : roles) {
                if (role.id == id)
                    return role;
            }
            return null;
        }

        public int getId() {
            return id;
        }

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createTime='" + createTime + '\'' +
                ", role=" + role +
                ", deleted=" + deleted +
                ", confirm=" + confirm +
                '}';
    }
}
