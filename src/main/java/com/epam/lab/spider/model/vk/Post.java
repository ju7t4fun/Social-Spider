package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post extends Model {

    public static final String ID = "id";
    public static final String OWNER_ID = "owner_id";
    public static final String FROM_ID = "from_id";
    public static final String DATE = "date";
    public static final String TEXT = "text";
    public static final String REPLY_OWNER_ID = "reply_owner_id";
    public static final String REPLY_POST_ID = "reply_post_id";
    public static final String FRIENDS_ONLY = "friends_only";
    public static final String COMMENTS_COUNT = "comments.count";
    public static final String COMMENTS_CAN_POST = "comments.can_post";
    public static final String LIKES_COUNT = "likes.count";
    public static final String LIKES_USER_LIKES = "likes.user_likes";
    public static final String LIKES_CAN_LIKE = "likes.can_like";
    public static final String LIKES_CAN_PUBLISH = "likes.can_publish";
    public static final String REPOSTS_COUNT = "reposts.count";
    public static final String REPOSTS_USER_REPOSTED = "reposts.user_reposted";
    public static final String POST_TYPE = "post_type";
    public static final String POST_SOURCE_TYPE = "post_source.type";
    public static final String POST_SOURCE_PLATFORM = "post_source.platform";
    public static final String POST_SOURCE_DATA = "post_source.data";
    public static final String POST_SOURCE_URL = "post_source.url";
    public static final String GEO_TYPE = "geo.type";
    public static final String GEO_COORDINATES = "geo.coordinates";
    public static final String GEO_PLACE_ID = "geo.place.id";
    public static final String GEO_PLACE_TITLE = "geo.place.title";
    public static final String GEO_PLACE_LATITUDE = "geo.place.latitude";
    public static final String GEO_PLACE_LONGITUDE = "geo.place.longitude";
    public static final String GEO_PLACE_CREATED = "geo.place.created";
    public static final String GEO_PLACE_ICON = "geo.place.icon";
    public static final String GEO_PLACE_COUNTRY = "geo.place.country";
    public static final String GEO_PLACE_CITY = "geo.place.city";
    public static final String GEO_PLACE_TYPE = "geo.place.type";
    public static final String GEO_PLACE_GROUP_ID = "geo.place.group_id";
    public static final String GEO_PLACE_GROUP_PHOTO = "geo.place.group_photo";
    public static final String GEO_PLACE_CHECKINS = "geo.place.checkins";
    public static final String GEO_PLACE_UPDATED = "geo.place.updated";
    public static final String GEO_PLACE_ADDRESS = "geo.place.address";
    public static final String SIGNER_ID = "signer_id";
    public static final String CAN_PIN = "can_pin";
    public static final String IS_PINNED = "is_pinned";

    private List<Attachment> attachments;
    private List<Post> copyHistory;

    public Post() {
        super();
    }

    public Post(Node root) {
        super(root, Post.class);
        List<Node> nodes;
        attachments = AttachmentFactory.parseAttachment(root);
        copyHistory = new ArrayList<Post>();
        if (root.hasChild("copy_history")) {
            nodes = root.child("copy_history").get(0).child();
            for (Node node : nodes)
                copyHistory.add(new Post(node));
        }
    }

    public static List<Post> parsePost(Node root) {
        List<Post> posts = new ArrayList<Post>();
        List<Node> nodes = root.child("post");
        for (Node node : nodes)
            posts.add(new Post(node));
        return posts;
    }
    public static List<Post> parseItem(Node root) {
        List<Post> posts = new ArrayList<Post>();
        List<Node> nodes = root.child("item");
        for (Node node : nodes)
            posts.add(new Post(node));
        return posts;
    }

    public int getId() {
        return get(ID).toInt();
    }

    public int getOwnerId() {
        return get(OWNER_ID).toInt();
    }

    public int getFromId() {
        return get(FROM_ID).toInt();
    }

    public Date getDate() {
        return get(DATE).toDate();
    }

    public String getText() {
        return get(TEXT).toString();
    }

    public int getReplyOwnerId() {
        return get(REPLY_OWNER_ID).toInt();
    }

    public int getReplyPostId() {
        return get(REPLY_POST_ID).toInt();
    }

    public boolean getFriendsOnly() {
        return get(FRIENDS_ONLY).toBoolean();
    }

    public String getPostType() {
        return get(POST_TYPE).toString();
    }

    public int getSignerId() {
        return get(SIGNER_ID).toInt();
    }

    public boolean getCanPin() {
        return get(CAN_PIN).toBoolean();
    }

    public boolean getIsPinned() {
        return get(IS_PINNED).toBoolean();
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public List<Post> getCopyHistory() {
        return copyHistory;
    }

    public Comments getComments() {
        return new Comments() {
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

    public Likes getLikes() {
        return new Likes() {
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

    public Reposts getReposts() {
        return new Reposts() {
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

    public Geo getGeo() {
        return new Geo() {
            @Override
            public String getType() {
                return get(GEO_TYPE).toString();
            }

            @Override
            public String getCoordinates() {
                return get(GEO_COORDINATES).toString();
            }

            @Override
            public Place getPlace() {
                return new Place() {
                    @Override
                    public int getId() {
                        return get(GEO_PLACE_ID).toInt();
                    }

                    @Override
                    public String getTitle() {
                        return get(GEO_PLACE_TITLE).toString();
                    }

                    @Override
                    public String getLatitude() {
                        return get(GEO_PLACE_LATITUDE).toString();
                    }

                    @Override
                    public String getLongitude() {
                        return get(GEO_PLACE_LONGITUDE).toString();
                    }

                    @Override
                    public Date getCreated() {
                        return get(GEO_PLACE_CREATED).toDate();
                    }

                    @Override
                    public String getIcon() {
                        return get(GEO_PLACE_ICON).toString();
                    }

                    @Override
                    public String getCountry() {
                        return get(GEO_PLACE_COUNTRY).toString();
                    }

                    @Override
                    public String getCity() {
                        return get(GEO_PLACE_CITY).toString();
                    }

                    @Override
                    public String getType() {
                        return get(GEO_PLACE_TYPE).toString();
                    }

                    @Override
                    public int getGroupId() {
                        return get(GEO_PLACE_GROUP_ID).toInt();
                    }

                    @Override
                    public String getGroupPhoto() {
                        return get(GEO_PLACE_GROUP_PHOTO).toString();
                    }

                    @Override
                    public int getCheckins() {
                        return get(GEO_PLACE_CHECKINS).toInt();
                    }

                    @Override
                    public Date getUpdated() {
                        return get(GEO_PLACE_UPDATED).toDate();
                    }

                    @Override
                    public String getAddress() {
                        return get(GEO_PLACE_ADDRESS).toString();
                    }
                };
            }
        };
    }

    public PostSource getPostSource() {
        return new PostSource() {
            @Override
            public String getType() {
                return get(POST_SOURCE_TYPE).toString();
            }

            @Override
            public String getPlatform() {
                return get(POST_SOURCE_PLATFORM).toString();
            }

            @Override
            public String getData() {
                return get(POST_SOURCE_DATA).toString();
            }

            @Override
            public URL getUrl() {
                return get(POST_SOURCE_URL).toURL();
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {")
                .append("id='").append(getId()).append("', ")
                .append("attachment=").append(attachments).append(", ")
                .append("copy_history=").append(copyHistory).append("}");
        return sb.toString();
    }

    public interface Likes {
        int getCount();

        boolean getUserLikes();

        boolean canLike();

        boolean canPublish();
    }

    public interface Comments {
        int getCount();

        boolean getCanPost();
    }

    public interface Reposts {
        int getCount();

        boolean getUserReposted();
    }

    public interface Geo {
        String getType();

        String getCoordinates();

        Place getPlace();

        interface Place {
            int getId();

            String getTitle();

            String getLatitude();

            String getLongitude();

            Date getCreated();

            String getIcon();

            String getCountry();

            String getCity();

            String getType();

            int getGroupId();

            String getGroupPhoto();

            int getCheckins();

            Date getUpdated();

            String getAddress();
        }
    }

    public interface PostSource {
        String getType();

        String getPlatform();

        String getData();

        URL getUrl();
    }
}