package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.controller.utils.validation.annotation.*;
import com.epam.lab.spider.model.service.ProfileService;
import com.epam.lab.spider.model.service.ServiceFactory;
import com.epam.lab.spider.model.service.TaskService;

import java.util.Date;
import java.util.List;



/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class User {

    private Integer id;
    @NotNull(message = "Name can not be null")
    @Size(min = 4, max = 16)
    private String name;
    private String surname;
    @NotNull(message = "Email can not be null")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,4}$",message = "Fail Email format", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    private String password;
    private Date createTime;
    private Role role;
    private Boolean deleted = false;
    private Boolean confirm = false;
    private List<Profile> profiles = null;
    private List<Task> tasks = null;

    public List<Task> getTasks() {
        if (tasks == null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            TaskService service = factory.create(TaskService.class);
            tasks = service.getByUserId(id);
        }
        return tasks;
    }

    public List<Profile> getProfiles() {
        if (profiles == null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            ProfileService service = factory.create(ProfileService.class);
            profiles = service.getByUserId(id);
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
