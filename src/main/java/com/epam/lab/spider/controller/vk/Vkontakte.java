package com.epam.lab.spider.controller.vk;

import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.controller.vk.auth.ClientAuthorization;
import com.epam.lab.spider.controller.vk.api.*;

public class Vkontakte implements Authorization, Api {

    private Configuration conf = new Configuration();
    private AccessToken accessToken = null;

    public Vkontakte() {
        super();
    }

    public Vkontakte(int appId) {
        conf.setAppId(appId);
    }

    public Configuration conf() {
        return conf;
    }

    public AccessToken signIn() {
        return signIn(true);
    }

    @Override
    public AccessToken signIn(boolean needs) {
        return accessToken = new ClientAuthorization(conf).signIn(needs);
    }


    @Override
    public Users users() {
        return new Users(accessToken);
    }

    @Override
    public Auth auth() {
        return new Auth(accessToken);
    }

    @Override
    public Wall wall() {
        return new Wall(accessToken);
    }

    @Override
    public Photos photos() {
        return new Photos(accessToken);
    }

    @Override
    public Friends friends() {
        return new Friends(accessToken);
    }

    @Override
    public Widgets widgets() {
        return new Widgets(accessToken);
    }

    @Override
    public Storage storage() {
        return new Storage(accessToken);
    }

    @Override
    public Status status() {
        return new Status(accessToken);
    }

    @Override
    public Audios audio() {
        return new Audios(accessToken);
    }

    @Override
    public Pages pages() {
        return new Pages(accessToken);
    }

    @Override
    public Groups groups() {
        return new Groups(accessToken);
    }

    @Override
    public Board board() {
        return new Board(accessToken);
    }

    @Override
    public Videos video() {
        return new Videos(accessToken);
    }

    @Override
    public Notes notes() {
        return new Notes(accessToken);
    }

    @Override
    public Places places() {
        return new Places(accessToken);
    }

    @Override
    public Account account() {
        return new Account(accessToken);
    }

    @Override
    public Messages messages() {
        return new Messages(accessToken);
    }

    @Override
    public NewsFeed newsFeed() {
        return new NewsFeed(accessToken);
    }

    @Override
    public Likes likes() {
        return new Likes(accessToken);
    }

    @Override
    public Polls polls() {
        return new Polls(accessToken);
    }

    @Override
    public Docs docs() {
        return new Docs(accessToken);
    }

    @Override
    public Fave fave() {
        return new Fave(accessToken);
    }

    @Override
    public Notifications notifications() {
        return new Notifications(accessToken);
    }

    @Override
    public Stats stats() {
        return new Stats(accessToken);
    }

    @Override
    public Search search() {
        return new Search(accessToken);
    }

    @Override
    public Apps apps() {
        return new Apps(accessToken);
    }

    @Override
    public Utils utils() {
        return new Utils(accessToken);
    }

    @Override
    public DataBase dataBase() {
        return new DataBase(accessToken);
    }

    @Override
    public Gifts gifts() {
        return new Gifts(accessToken);
    }

    @Override
    public Execute execute() {
        return new Execute(accessToken);
    }

}
