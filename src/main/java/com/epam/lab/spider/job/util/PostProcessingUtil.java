package com.epam.lab.spider.job.util;

import com.epam.lab.spider.job.exception.PostContentException;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.vk.*;

import java.util.List;

/**
 * Created by hell-engine on 7/11/2015.
 */
public class PostProcessingUtil {
    public static Post processingPost(com.epam.lab.spider.model.vk.Post vkPost,  Task task)throws PostContentException {
        return processingPost(vkPost, task.getContentType(), task.getHashTags(), task.getSignature());
    }

    public static Post processingPost(com.epam.lab.spider.model.vk.Post vkPost,  Task.ContentType contentType) throws PostContentException {
        return processingPost(vkPost, contentType, null,null);
    }
    public static Post processingPost(com.epam.lab.spider.model.vk.Post vkPost,  Task.ContentType contentType, String hashTags, String sign) throws PostContentException {
        Post post = new Post();
        Task.ContentType newPostContentType = new Task.ContentType();
        StringBuilder messageBuilder = new StringBuilder();
        String signature = null;
        if(sign!=null) {
            signature = sign.trim();
        }
        if(contentType.hasTextTitle() && signature != null && !signature.isEmpty() ) {
            messageBuilder.append("[%owner%|").append(signature).append("]").append(" \r\n");
        }else if(contentType.hasSimpleTitle()){
            messageBuilder.append("[%owner%|%owner_name%]").append(" \r\n");
            if(signature != null && !signature.isEmpty()){
                messageBuilder.append(signature).append(" \r\n");
            }
        }else {
            if(signature != null && !signature.isEmpty()){
                messageBuilder.append(signature).append(" \r\n");
            }
        }
        String pureText = vkPost.getText().trim();
        if (contentType.hasText()) {
            if(!pureText.isEmpty()) {
                messageBuilder.append(pureText);
                newPostContentType.setType(Task.ContentType.TEXT);
            }
        }
        if(contentType.hasReposts()){
            List<com.epam.lab.spider.model.vk.Post> copyHistoryList = vkPost.getCopyHistory();
            if(!copyHistoryList.isEmpty()){
                post = processingPost(copyHistoryList.get(0),  contentType);
                String innerMessage = post.getMessage();
                messageBuilder.append(" \r\n").append(innerMessage);
                newPostContentType.setType(Task.ContentType.REPOSTS);

            }
        }
        {
            if(hashTags!=null)messageBuilder.append(" \r\n").append(hashTags);
            String resultReplace = messageBuilder.toString().replaceAll("( \r\n|\r\n)","\n").replaceAll("\n"," \r\n").trim();
            post.setMessage(resultReplace);
        }
        for (com.epam.lab.spider.model.vk.Attachment vkAttachment : vkPost.getAttachments()) {
            if (contentType.hasPhoto() && vkAttachment instanceof Photo) {
                Attachment attachment = new Attachment();
                attachment.setType(Attachment.Type.PHOTO);
                attachment.setPayload(((Photo) vkAttachment).getPhoto604().toString());
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.PHOTO);
            }
            if (contentType.hasAudio() && vkAttachment instanceof Audio) {
                Attachment attachment = new Attachment();
                Audio audio = (Audio) vkAttachment;
                String attachString = "audio" + audio.getOwnerId() + "_" + audio.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.AUDIO);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.AUDIO);
            }
            if (contentType.hasDoc() && vkAttachment instanceof Doc) {
                Attachment attachment = new Attachment();
                Doc doc = (Doc) vkAttachment;
                String attachString = "doc" + doc.getOwnerId() + "_" + doc.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.DOC);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.DOCUMENTS);
            }
            if (contentType.hasVideo() && vkAttachment instanceof Video) {
                Attachment attachment = new Attachment();
                Video video = (Video) vkAttachment;
                String attachString = "video" + video.getOwnerId() + "_" + video.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.VIDEO);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.VIDEO);
            }
            if (contentType.hasDoc() && vkAttachment instanceof Page) {
                Attachment attachment = new Attachment();
                Page page = (Page) vkAttachment;
                String attachString = "page" + page.getOwnerId() + "_" + page.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.OTHER);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.PAGES);
            }
            if (contentType.hasDoc() && vkAttachment instanceof Link) {
                Attachment attachment = new Attachment();
                Link link = (Link) vkAttachment;
                String attachString = "link" + link.getOwnerId() + "_" + link.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.OTHER);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.LINKS);
            }
        }
        if(newPostContentType.getType().intValue()==0){
            throw new PostContentException();
//            return null;
        }else return post;
    }
    public static boolean checkContent(com.epam.lab.spider.model.vk.Post vkPost,  Task.ContentType contentType){
        String pureText = vkPost.getText().trim();
        if (contentType.hasText()) {
            if(!pureText.isEmpty()) {
                return true;
            }
        }
        if(contentType.hasReposts()){
            List<com.epam.lab.spider.model.vk.Post> copyHistoryList = vkPost.getCopyHistory();
            if(!copyHistoryList.isEmpty()){
                return checkContent(copyHistoryList.get(0),contentType);
            }
        }
        for (com.epam.lab.spider.model.vk.Attachment vkAttachment : vkPost.getAttachments()) {
            if (contentType.hasPhoto() && vkAttachment instanceof Photo) {
                return true;
            }
            if (contentType.hasAudio() && vkAttachment instanceof Audio) {
                return true;
            }
            if (contentType.hasDoc() && vkAttachment instanceof Doc) {
                return true;
            }
            if (contentType.hasVideo() && vkAttachment instanceof Video) {
                return true;
            }
            if (contentType.hasLinks() && vkAttachment instanceof Link) {
                return true;
            }
            if (contentType.hasPages() && vkAttachment instanceof Page) {
                return true;
            }
        }
        return false;
    }
}
