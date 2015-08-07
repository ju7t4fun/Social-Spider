package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Node;
import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.job.exception.FindingEmptyResultException;
import com.epam.lab.spider.job.exception.WallStopException;
import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.model.vk.Post;
import com.epam.lab.spider.model.vk.PostOffsetDecorator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Execute extends Methods {

    private static final Logger LOG = Logger.getLogger(Execute.class);

    public Execute(AccessToken token) {
        super(token);
    }

    public Map<String, String> getVideoPlayer(List<String> videoIds) throws VKException {
        String videos = null;
        for (String id : videoIds) {
            videos = videos == null ? id.replace("video", "") : videos + "," + id.replace("video", "");
        }
        Parameters param = new Parameters();
        param.add("videos", videos);
        Response response = request("execute.getVideoPlayer", param).execute();
        List<Node> item = response.root().child("item");
        Map<String, String> players = new HashMap<>();
        for (int i = 0; i < videoIds.size(); i++)
            players.put(videoIds.get(i), item.get(i).value().toString());
        return players;
    }

    public Map<String, String> getAudioUrl(List<String> audioIds) throws VKException {
        Map<String, String> url = new HashMap<>();
        String audios = null;
        for (String id : audioIds)
            audios = audios == null ? id.replace("audio", "") : audios + ',' + id.replace("audio", "");
        Parameters param = new Parameters();
        param.add("audios", audios);
        Response response = request("execute.getAudioUrl", param).execute();
        List<Node> item = response.root().child("item");
        for (int i = 0; i < audioIds.size(); i++) {
            url.put(audioIds.get(i), item.get(i).value().toString());
        }
        return url;
    }

    public List<PostingTask> getPostStats(List<PostingTask> postingTasks) throws VKException {
        String postIds = null;
        for (PostingTask post : postingTasks) {
            postIds = postIds == null ? post.getFullId() : postIds + "," + post.getFullId();
        }
        Parameters param = new Parameters();
        param.add("posts", postIds);
        Node root = request("execute.getPostStats", param).execute().root();
        final List<Node> likes = root.child("likes").get(0).child();
        final List<Node> comments = root.child("comments").get(0).child();
        final List<Node> reposts = root.child("reposts").get(0).child();

        if (postingTasks.size() == likes.size()) {
            for (int i = 0; i < postingTasks.size(); i++) {
                final int index = i;
                PostingTask post = postingTasks.get(index);
                post.setStats(new PostingTaskImpl.Stats() {
                    @Override
                    public int getLikes() {
                        return likes.get(index).value().toInt();
                    }

                    @Override
                    public int getRePosts() {
                        return reposts.get(index).value().toInt();
                    }

                    @Override
                    public int getComments() {
                        return comments.get(index).value().toInt();
                    }
                });
                postingTasks.set(i, post);
            }
        } else {
            Map<String, PostingTaskImpl.Stats> fullPostIdStats = new HashMap<>();
            final List<Node> id = root.child("id").get(0).child();
            final List<Node> ownerId = root.child("owner_id").get(0).child();
            for (int i = 0; i < id.size(); i++) {
                final int index = i;
                fullPostIdStats.put(ownerId.get(index).value().toInt() + "_" + id.get(index).value().toInt(),
                        new PostingTaskImpl.Stats() {
                            @Override
                            public int getLikes() {
                                return likes.get(index).value().toInt();
                            }

                            @Override
                            public int getRePosts() {
                                return reposts.get(index).value().toInt();
                            }

                            @Override
                            public int getComments() {
                                return comments.get(index).value().toInt();
                            }
                        });
            }
            for (PostingTask post : postingTasks) {
                if (fullPostIdStats.containsKey(post.getFullId())) {
                    post.setStats(fullPostIdStats.get(post.getFullId()));
                } else {
                    post.setStats(new PostingTaskImpl.Stats() {
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
                    });
                }
            }
        }
        return postingTasks;
    }

    /**
     * Возвращает список случайних записей со стены пользователя или сообщества. Работает для последних 2^15 постов.
     */
    public List<Post> getRandomPostFromWall(Integer ownerId, Integer count, Filter filter, Integer maxLoopCount, int
            seed) throws VKException {
        Parameters param = new Parameters();
        param.add("owner_id", ownerId);
        param.add("count", count);
        param.add("exclude_post", 0);
        if (maxLoopCount != null) param.add("max_loops", maxLoopCount);
        param.add("random_seed", seed);
        if (filter != null && filter.getLikes() != null) param.add("likes", filter.getLikes());
        if (filter != null && filter.getRePosts() != null) param.add("reposts", filter.getRePosts());
        if (filter != null && filter.getComments() != null) param.add("comments", filter.getComments());
        Response response = request("execute.getRandomPostFromWall", param).execute();
        return Post.parseItem(response.root().child("items").get(0));
    }

    /**
     * Возвращает список последних записей со стены пользователя или сообщества. Максимум 50 постов.
     */
    public List<Post> getNewPostFromWall(Integer ownerId, Integer count, Integer lastPostId, Integer maxLoopCount)
            throws VKException {
        Parameters param = new Parameters();
        param.add("owner_id", ownerId);
        if (count != null) param.add("count", count);
        if (maxLoopCount != null) param.add("max_loops", maxLoopCount);
        if (lastPostId != null) param.add("last_post_id", lastPostId);
        Response response = request("execute.getNewPostFromWall", param).execute();
        return Post.parseItem(response.root().child("items").get(0));
    }
    public List<Post> testOverLoop(Integer ... args)
            throws VKException {
        Parameters param = new Parameters();
        param.add("owner_id", 1);
        Response response = request("execute.overLoop", param).execute();
        return Post.parseItem(response.root().child("items").get(0));
    }
    /**
     *
     */
    public List<PostOffsetDecorator> getPostFromBeginWall(Integer ownerId, Integer count, Integer lastPostId, Integer offset, Filter filter)
            throws VKException, WallStopException, FindingEmptyResultException {
        Parameters param = bindSyncMethod(ownerId,count,lastPostId,offset,filter);
        Response response = request("execute.postsByWallBeginSync", param).execute();
        return bindSyncResponse(response);
    }
    /**
     *
     */
    public List<PostOffsetDecorator> getPostFromEndWall(Integer ownerId, Integer count, Integer lastPostId, Integer offset, Filter filter)
            throws VKException, WallStopException, FindingEmptyResultException {
        Parameters param = bindSyncMethod(ownerId,count,lastPostId,offset,filter);
        Response response = request("execute.postsByWallEndSync", param).execute();
        return bindSyncResponse(response);
    }



    private Parameters bindSyncMethod(Integer ownerId, Integer count, Integer lastPostId, Integer offset, Filter filter){
        Parameters param = new Parameters();
        param.add("owner_id", ownerId);
        if (count != null) param.add("count", count);
        if (offset != null) param.add("offset", offset);
        if (lastPostId != null) param.add("last_post_id", lastPostId);

        if (filter != null && filter.getLikes() != null) param.add("likes", filter.getLikes());
        if (filter != null && filter.getRePosts() != null) param.add("reposts", filter.getRePosts());
        if (filter != null && filter.getComments() != null) param.add("comments", filter.getComments());
        return param;
    }
    private List<PostOffsetDecorator>  bindSyncResponse(Response response) throws WallStopException, FindingEmptyResultException {
        List<Node> nodeHasDataList = response.root().child("data");
        if(!nodeHasDataList.isEmpty()){
            String data = nodeHasDataList.get(0).value().toString();
            if(data.equals("none")){
                throw  new WallStopException();
            }else if(data.equals("finding")){
                Integer offset = null;
                Integer id = null;
                throw  new FindingEmptyResultException(id, offset);
            }
        }
        List<Post> posts = Post.parseItem(response.root().child("items").get(0));
        List<PostOffsetDecorator> decoratedPosts = new ArrayList<>();
        LOG.info(response.toString());
        List<Node> nodes = response.root().child("offset_map").get(0).child();
        int i = 0;
        for(Post post:posts){
            PostOffsetDecorator postOffsetDecorator = new PostOffsetDecorator();
            postOffsetDecorator.setPost(post);
            Integer offsetValue = nodes.get(i++).value().toInt();
            postOffsetDecorator.setOffset(offsetValue);
            decoratedPosts.add(postOffsetDecorator);
        }
        return decoratedPosts;
    }
}
