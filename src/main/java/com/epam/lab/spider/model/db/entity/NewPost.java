package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;

import java.util.Date;

/**
 * Created by Sasha on 12.06.2015.
 */
public class NewPost {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService service = factory.create(PostService.class);

    private Integer id;
    private Integer postId;
    private Integer wallId;
    private Date postTime;
    private Date deleteTime;
    private State state = State.CREATED;
    private Boolean deleted = false;
    private Integer userId = 1;
    private Integer vkPostId;

    private Post post;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        if(post!=null){
            return post.getId();
        }
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getWallId() {
        return wallId;
    }

    public void setWallId(Integer wallId) {
        this.wallId = wallId;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVkPostId() {
        return vkPostId;
    }

    public void setVkPostId(Integer vkPostId) {
        this.vkPostId = vkPostId;
    }

    public Post getPost() {
        if (post == null) {
            if (postId == null)
                post = new Post();
            else
                post = service.getById(postId);
        }
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Stats getStats() {
        if (vkPostId != null) {
            WallService wallService = new WallService();
            Wall wall = wallService.getById(wallId);
            Profile profile = wall.getProfile();
            Vkontakte vk = new Vkontakte(profile.getAppId());
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime().getTime());
            vk.setAccessToken(accessToken);
            Parameters param = new Parameters();
            param.add("posts", "" + wall.getOwner().getVk_id() + "_" + this.vkPostId);
            try {
                final com.epam.lab.spider.model.vk.Post post = vk.wall().getById(param).get(0);
                return new Stats() {
                    @Override
                    public int getLikes() {
                        return post.getLikes().getCount();
                    }

                    @Override
                    public int getReposts() {
                        return post.getReposts().getCount();
                    }

                    @Override
                    public int getComments() {
                        return post.getComments().getCount();
                    }
                };
            } catch (VKException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public com.epam.lab.spider.controller.vk.api.Stats.Reach getPostReach() {
        if (vkPostId != null) {
            WallService wallService = new WallService();
            Wall wall = wallService.getById(wallId);
            Profile profile = wall.getProfile();
            Vkontakte vk = new Vkontakte(profile.getAppId());
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime().getTime());
            vk.setAccessToken(accessToken);
            Parameters param = new Parameters();
            param.add("owner_id", wall.getOwner().getVk_id());
            param.add("post_id", wallId);
            try {
                return vk.stats().getPostReach(param);
            } catch (VKException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "NewPost{" +
                "id=" + id +
                ", postId=" + postId +
                ", wallId=" + wallId +
                ", postTime=" + postTime +
                ", deleteTime=" + deleteTime +
                ", state=" + state +
                ", deleted=" + deleted +
                ", userId=" + userId +
                ", vkPostId=" + vkPostId +
                ", post=" + post +
                '}';
    }

    public enum State {
        CREATED, POSTING, POSTED, DELETED, ERROR, RESTORED
    }

    public interface Stats {

        int getLikes();

        int getReposts();

        int getComments();

    }
}
