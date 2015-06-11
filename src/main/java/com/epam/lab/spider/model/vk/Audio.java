package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Audio extends Model implements Attachment {

    public static final String ID = "id";
    public static final String OWNER_ID = "owner_id";
    public static final String ARTIST = "artist";
    public static final String TITLE = "title";
    public static final String DURATION = "duration";
    public static final String URL = "url";
    public static final String LYRICS_ID = "lyrics_id";
    public static final String ALBUM_ID = "album_id";
    public static final String GENRE_ID = "genre_id";

    public Audio() {
        super();
    }

    public Audio(Node root) {
        super(root, Audio.class);
    }

    public static List<Audio> parseAudio(Node root) {
        List<Audio> audios = new ArrayList<Audio>();
        List<Node> nodes = root.child("audio");
        for (Node node : nodes)
            audios.add(new Audio(node));
        return audios;
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    @Override
    public Type getType() {
        return Type.AUDIO;
    }

    @Override
    public int getOwnerId() {
        return get(OWNER_ID).toInt();
    }

    public String getArtist() {
        return get(ARTIST).toString();
    }

    public String getTitle() {
        return get(TITLE).toString();
    }

    public int getDuration() {
        return get(DURATION).toInt();
    }

    public URL getUrl() {
        return get(URL).toURL();
    }

    public int getLyricsId() {
        return get(LYRICS_ID).toInt();
    }

    public int getAlbumId() {
        return get(ALBUM_ID).toInt();
    }

    public int getGenreId() {
        return get(GENRE_ID).toInt();
    }

    public interface Lyrics {
        int getId();

        String getText();
    }

    public static class Album extends Model {

        public static final String ID = "id";
        public static final String OWNER_ID = "owner_id";
        public static final String TITLE = "title";

        public Album() {
            super();
        }

        public Album(Node root) {
            super(root, Album.class);
        }

        public static List<Album> parseAlbum(Node root) {
            List<Album> albums = new ArrayList<Album>();
            List<Node> nodes = root.child("album");
            for (Node node : nodes)
                albums.add(new Album(node));
            return albums;
        }

        public int getId() {
            return get(ID).toInt();
        }

        public int getOwnerId() {
            return get(OWNER_ID).toInt();
        }

        public String getTitle() {
            return get(TITLE).toString();
        }

    }

}
