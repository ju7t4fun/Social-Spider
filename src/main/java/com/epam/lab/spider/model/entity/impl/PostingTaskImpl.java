package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.Vkontakte;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.*;
import com.epam.lab.spider.persistence.service.WallService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Oleksandra Lobanok
 */
public class PostingTaskImpl implements PostingTask, PersistenceIdentificationChangeable {
    private Integer id;
    private Integer postId;
    private Integer wallId;
    private Date postTime;
    private Date deleteTime;
    private State state = State.CREATED;
    private Boolean deleted = false;
    private Integer userId = 1;
    private Integer vkPostId;
    private String fullId = null;
    private Stats stats;
    private Post post;
    private Owner owner = null;
    private Profile profile = null;

    protected PostingTaskImpl() {
    }

    @Override
    public String getFullId() {
        if (fullId != null)
            return fullId;
        if (vkPostId != null)
            fullId = getOwner().getVkId() + "_" + vkPostId;
        return fullId;
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
    public Integer getPostId() {
        if (post != null) {
            return post.getId();
        }
        return postId;
    }

    @Override
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public Integer getWallId() {
        return wallId;
    }

    @Override
    public void setWallId(Integer wallId) {
        this.wallId = wallId;
    }

    @Override
    public Date getPostTime() {
        return postTime;
    }

    @Override
    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    @Override
    public Date getDeleteTime() {
        return deleteTime;
    }

    @Override
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
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
    public Integer getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Integer getVkPostId() {
        return vkPostId;
    }

    @Override
    public void setVkPostId(Integer vkPostId) {
        this.vkPostId = vkPostId;
    }

    // TODO: EXPAND TO UTIL
    @Override
    public Stats getStats() {
        if (stats == null) {
            try {
                WallService wallService = new WallService();
                Wall wall = wallService.getById(wallId);
                Profile profile = wall.getProfile();
                Vkontakte vk = new Vkontakte(profile.getAppId());
                AccessToken accessToken = new AccessToken();
                accessToken.setAccessToken(profile.getAccessToken());
                accessToken.setUserId(profile.getVkId());
                accessToken.setExpirationMoment(profile.getExtTime().getTime());
                vk.setAccessToken(accessToken);
                List<PostingTask> posts = new ArrayList<>();
                posts.add(this);
                List<PostingTask> postStats = vk.execute().getPostStats(posts);
                stats = postStats.get(0).getStats();
            } catch (VKException e) {
                stats = new Stats() {
                    @Override
                    public int getLikes() {
                        return 0;
                    }

                    @Override
                    public int getRePosts() {
                        return 0;
                    }

                    @Override
                    public int getComments() {
                        return 0;
                    }
                };
            }
        }

        return stats;
    }

    @Override
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Override
    public Post getPost() {
        return post;
    }

    @Override
    public void setPost(Post post) {
        this.post = post;
    }


    @Override
    public com.epam.lab.spider.integration.vk.api.Stats.Reach getPostReach() {
        return VkStatReachUtil.getPostReach(vkPostId, wallId);
    }

    @Override
    public String toString() {
        return "PostingTask{" +
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

    @Override
    public Owner getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public Profile getProfile() {
        return profile;
    }

    @Override
    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    static class VkStatReachUtil {
        public static com.epam.lab.spider.integration.vk.api.Stats.Reach getPostReach(Integer vkPostId, Integer wallId) {
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
                param.add("owner_id", wall.getOwner().getVkId());
                param.add("post_id", vkPostId);
                try {
                    return vk.stats().getPostReach(param);
                } catch (VKException ignored) {
                }
            }
            return new com.epam.lab.spider.integration.vk.api.Stats.Reach() {
                @Override
                public int getReachSubscribers() {
                    return 0;
                }

                @Override
                public int getReachTotal() {
                    return 0;
                }

                @Override
                public int getLinks() {
                    return 0;
                }

                @Override
                public int getToGroup() {
                    return 0;
                }

                @Override
                public int getJoinGroup() {
                    return 0;
                }

                @Override
                public int getReport() {
                    return 0;
                }

                @Override
                public int getHide() {
                    return 0;
                }

                @Override
                public int getUnsubscribe() {
                    return 0;
                }
            };
        }
    }

}
