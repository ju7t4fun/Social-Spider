package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Widget {

    public static class Page extends Model {

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String URL = "url";
        public static final String LIKES_COUNT = "likes.count";
        public static final String COMMENTS_COUNT = "comments.count";
        public static final String DATE = "date";
        public static final String PAGE_ID = "page_id";

        private Photo photo = null;

        public Page() {
            super();
        }

        public Page(Node root) {
            super(root, Page.class);
            if (root.hasChild("photo"))
                photo = new Photo(root.child("photo").get(0));
            else
                photo = new Photo();
        }

        public static List<Page> parsePage(Node root) {
            List<Page> pages = new ArrayList<Page>();
            List<Node> nodes = root.child("page");
            for (Node node : nodes)
                pages.add(new Page(node));
            return pages;
        }

        public Photo getPhoto() {
            return photo;
        }

        public int getId() {
            return get(ID).toInt();
        }

        public String getTitle() {
            return get(TITLE).toString();
        }

        public String getDescription() {
            return get(DESCRIPTION).toString();
        }

        public java.net.URL getUrl() {
            return get(URL).toURL();
        }

        public int getLikesCount() {
            return get(LIKES_COUNT).toInt();
        }

        public int getCommentsCount() {
            return get(COMMENTS_COUNT).toInt();
        }

        public Date getDate() {
            return get(DATE).toDate();
        }

        public int getPageId() {
            return get(PAGE_ID).toInt();
        }

    }

    public static class Post extends Model {

        public static final String ID = "id";
        public static final String FROM_ID = "from_id";
        public static final String TO_ID = "to_id";
        public static final String DATE = "date";
        public static final String POST_TYPE = "post_type";
        public static final String TEXT = "text";
        public static final String COMMENTS_COUNT = "comments.count";
        public static final String COMMENTS_CAN_POST = "comments.can_post";
        public static final String LIKES_COUNT = "likes.count";
        public static final String LIKES_USER_LIKES = "likes.user_likes";
        public static final String LIKES_CAN_LIKE = "likes.can_like";
        public static final String LIKES_CAN_PUBLISH = "likes.can_publish";
        public static final String REPOSTS_COUNT = "reposts.count";
        public static final String REPOSTS_USER_REPOSTED = "reposts.user_reposted";

        private List<Link> postSource;

        public Post() {
            super();
        }

        public Post(Node root) {
            super(root, Post.class);
            postSource = Link.parseLink(root.child("post_source").get(0));
        }

        public static List<Post> parsePost(Node root) {
            List<Post> posts = new ArrayList<Post>();
            List<Node> nodes = root.child("post");
            for (Node node : nodes)
                posts.add(new Post(node));
            return posts;
        }

        public int getId() {
            return get(ID).toInt();
        }

        public int getFromId() {
            return get(FROM_ID).toInt();
        }

        public int getToId() {
            return get(TO_ID).toInt();
        }

        public Date getDate() {
            return get(DATE).toDate();
        }

        public String getPostType() {
            return get(POST_TYPE).toString();
        }

        public String getText() {
            return get(TEXT).toString();
        }

        public com.epam.lab.spider.model.vk.Post.Comments getComments() {
            return new com.epam.lab.spider.model.vk.Post.Comments() {
                @Override
                public int getCount() {
                    return get(COMMENTS_COUNT).toInt();
                }

                @Override
                public boolean getCanPost() {
                    return get(COMMENTS_CAN_POST).toBoolean();
                }
            };
        }

        public com.epam.lab.spider.model.vk.Post.Likes getLikes() {
            return new com.epam.lab.spider.model.vk.Post.Likes() {
                @Override
                public int getCount() {
                    return get(LIKES_COUNT).toInt();
                }

                @Override
                public boolean getUserLikes() {
                    return get(LIKES_USER_LIKES).toBoolean();
                }

                @Override
                public boolean canLike() {
                    return get(LIKES_CAN_LIKE).toBoolean();
                }

                @Override
                public boolean canPublish() {
                    return get(LIKES_CAN_PUBLISH).toBoolean();
                }
            };
        }

        public com.epam.lab.spider.model.vk.Post.Reposts getReposts() {
            return new com.epam.lab.spider.model.vk.Post.Reposts() {
                @Override
                public int getCount() {
                    return get(REPOSTS_COUNT).toInt();
                }

                @Override
                public boolean getUserReposted() {
                    return get(REPOSTS_USER_REPOSTED).toBoolean();
                }
            };
        }

        public List<Link> getPostSource() {
            return postSource;
        }

    }

}
