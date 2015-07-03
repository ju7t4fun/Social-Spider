package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Node;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Execute extends Methods {

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


}
