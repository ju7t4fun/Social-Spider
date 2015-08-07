package com.epam.lab.spider.job.util;

import com.epam.lab.spider.job.exception.PostContentException;
import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.model.vk.*;

import java.util.List;

/**
 * @author Yura Kovalik
 */
public class PostProcessingUtil {
    public static AbstractEntityFactory entityFactory = BasicEntityFactory.getSynchronized();

    public static Post processingPost(com.epam.lab.spider.model.vk.Post vkPost,  Task task)throws PostContentException {
        return processingPost(vkPost, task.getContentType(), task.getHashTags(), task.getSignature());
    }

    public static Post processingPost(com.epam.lab.spider.model.vk.Post vkPost,  Task.ContentType contentType) throws PostContentException {
        return processingPost(vkPost, contentType, null,null);
    }
    public static Post processingPost(com.epam.lab.spider.model.vk.Post vkPost,  Task.ContentType contentType, String hashTags, String sign) throws PostContentException {
        Post post = BasicEntityFactory.getSynchronized().createPost();
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
        if (contentType.hasRePosts()) {
            List<com.epam.lab.spider.model.vk.Post> copyHistoryList = vkPost.getCopyHistory();
            if(!copyHistoryList.isEmpty()){
                post = processingPost(copyHistoryList.get(0),  contentType);
                String innerMessage = post.getMessage();
                messageBuilder.append(" \r\n").append(innerMessage);
                newPostContentType.setType(Task.ContentType.RE_POSTS);

            }
        }
        {
            if(hashTags!=null)messageBuilder.append(" \r\n").append(hashTags);
            String resultReplace = messageBuilder.toString().replaceAll("( \r\n|\r\n)","\n").replaceAll("\n"," \r\n").trim();
            post.setMessage(resultReplace);
        }
        for (com.epam.lab.spider.model.vk.Attachment vkAttachment : vkPost.getAttachments()) {
            if (contentType.hasPhoto() && vkAttachment instanceof Photo) {
                Attachment attachment = entityFactory.createAttachment();
                attachment.setType(Attachment.Type.PHOTO);
                attachment.setPayload(((Photo) vkAttachment).getPhoto604().toString());
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.PHOTO);
            }
            if (contentType.hasAudio() && vkAttachment instanceof Audio) {
                Attachment attachment = entityFactory.createAttachment();
                Audio audio = (Audio) vkAttachment;
                String attachString = "audio" + audio.getOwnerId() + "_" + audio.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.AUDIO);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.AUDIO);
            }
            if (contentType.hasDoc() && vkAttachment instanceof Doc) {
                Attachment attachment = entityFactory.createAttachment();
                Doc doc = (Doc) vkAttachment;
                String attachString = "doc" + doc.getOwnerId() + "_" + doc.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.DOC);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.DOCUMENTS);
            }
            if (contentType.hasVideo() && vkAttachment instanceof Video) {
                Attachment attachment = entityFactory.createAttachment();
                Video video = (Video) vkAttachment;
                String attachString = "video" + video.getOwnerId() + "_" + video.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.VIDEO);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.VIDEO);
            }
            if (contentType.hasDoc() && vkAttachment instanceof Page) {
                Attachment attachment = entityFactory.createAttachment();
                Page page = (Page) vkAttachment;
                String attachString = "page" + page.getOwnerId() + "_" + page.getId();
                attachment.setPayload(attachString);
                attachment.setMode(Attachment.Mode.CODE);
                attachment.setType(Attachment.Type.OTHER);
                post.addAttachment(attachment);
                newPostContentType.setType(Task.ContentType.PAGES);
            }
            if (contentType.hasDoc() && vkAttachment instanceof Link) {
                Attachment attachment = entityFactory.createAttachment();
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
        if (contentType.hasRePosts()) {
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
