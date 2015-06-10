package com.epam.lab.spider.controller.vk;

import com.epam.lab.spider.controller.vk.api.*;

public interface Api {

    Users users();
    Auth auth();
    Wall wall();
    Photos photos();
    Friends friends();
    Widgets widgets();
    Storage storage();
    Status status();
    Audios audio();
    Pages pages();
    Groups groups();
    Board board();
    Videos video();
    Notes notes();
    Places places();
    Account account();
    Messages messages();
    NewsFeed newsFeed();
    Likes likes();
    Polls polls();
    Docs docs();
    Fave fave();
    Notifications notifications();
    Stats stats();
    Search search();
    Apps apps();
    Utils utils();
    DataBase dataBase();
    Gifts gifts();
    Execute execute();

}
