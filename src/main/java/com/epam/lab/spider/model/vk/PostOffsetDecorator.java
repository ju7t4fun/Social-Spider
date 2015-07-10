package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;
import com.epam.lab.spider.controller.vk.Value;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by hell-engine on 7/10/2015.
 */
public class PostOffsetDecorator extends Post {
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    private Post post;
    private Integer offset;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public static List<Post> parsePost(Node root) {
        return Post.parsePost(root);
    }

    public int getReplyOwnerId() {
        return post.getReplyOwnerId();
    }

    public void remove(String key) {
        post.remove(key);
    }

    public boolean getCanPin() {
        return post.getCanPin();
    }

    public Post.Comments getComments() {
        return post.getComments();
    }

    public List<Attachment> getAttachments() {
        return post.getAttachments();
    }

    public boolean getIsPinned() {
        return post.getIsPinned();
    }

    public void addAll(Node root, Class<?> model) {
        post.addAll(root, model);
    }

    public boolean getFriendsOnly() {
        return post.getFriendsOnly();
    }

    public Post.PostSource getPostSource() {
        return post.getPostSource();
    }

    public void add(String key, String value) {
        post.add(key, value);
    }

    public Post.Reposts getReposts() {
        return post.getReposts();
    }

    public Post.Likes getLikes() {
        return post.getLikes();
    }

    public Set<String> getKeys() {
        return post.getKeys();
    }

    public int getSignerId() {
        return post.getSignerId();
    }

    public boolean isField(String key) {
        return post.isField(key);
    }

    public String getText() {
        return post.getText();
    }

    public int getFromId() {
        return post.getFromId();
    }

    public int getId() {
        return post.getId();
    }

    public Date getDate() {
        return post.getDate();
    }

    public int getReplyPostId() {
        return post.getReplyPostId();
    }

    public String getPostType() {
        return post.getPostType();
    }

    public List<Post> getCopyHistory() {
        return post.getCopyHistory();
    }

    public static List<Post> parseItem(Node root) {
        return Post.parseItem(root);
    }

    public Value get(String key) {
        return post.get(key);
    }

    public Post.Geo getGeo() {
        return post.getGeo();
    }

    public int getOwnerId() {
        return post.getOwnerId();
    }

    @Override
    public String toString() {
        return post.toString();
    }
}
