package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.utils.Receiver;
import com.epam.lab.spider.controller.utils.Sender;
import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.service.CategoryService;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 02.07.2015.
 */
public class Feed {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService postService = factory.create(PostService.class);
    private static CategoryService categoryService = factory.create(CategoryService.class);
    private static Receiver feedReceiver = null;

    public boolean processing(Post post, Task task) {
        List<Category> categories = categoryService.getByTaskId(task.getId());
        post.setUserId(-1);
        if (postService.insert(post)) {
            postService.bindWithCategories(post.getId(), categories);
            if (feedReceiver != null)
                for (Category category : categories) {
                    feedReceiver.send(category.getId(), "new|" + post.getId() + "|" + category.getName());
                }
        }
        return false;
    }

    public static class FeedSender implements Sender {

        @Override
        public void accept(Receiver receiver) {
            feedReceiver = receiver;
        }

        @Override
        public void history(int clientId) {

        }
    }

}
