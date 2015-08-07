package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Node;
import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Period;

import java.util.List;

public class Stats extends Methods {

    public Stats(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает статистику сообщества или приложения.
     */
    public List<Period> get(Parameters param) throws VKException {
        Response response = request("stats.get", param).execute();
        return Period.parsePeriod(response.root());
    }

    /**
     * Добавляет данные о текущем сеансе в статистику посещаемости приложения.
     */
    public boolean trackVisitor() throws VKException {
        Response response = request("stats.trackVisitor", new Parameters()).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает статистику для записи на стене.
     */
    public Reach getPostReach(Parameters param) throws VKException {
        final Response response = request("stats.getPostReach", param).execute();
        final Node stats = response.root().child("stats").get(0);
        return new Reach() {
            @Override
            public int getReachSubscribers() {
                return stats.child("reach_subscribers").get(0).value().toInt();
            }

            @Override
            public int getReachTotal() {
                return stats.child("reach_total").get(0).value().toInt();
            }

            @Override
            public int getLinks() {
                return stats.child("links").get(0).value().toInt();
            }

            @Override
            public int getToGroup() {
                return stats.child("to_group").get(0).value().toInt();
            }

            @Override
            public int getJoinGroup() {
                return stats.child("join_group").get(0).value().toInt();
            }

            @Override
            public int getReport() {
                return stats.child("report").get(0).value().toInt();
            }

            @Override
            public int getHide() {
                return stats.child("hide").get(0).value().toInt();
            }

            @Override
            public int getUnsubscribe() {
                return stats.child("unsubscribe").get(0).value().toInt();
            }
        };
    }

    public interface Reach {
        int getReachSubscribers();

        int getReachTotal();

        int getLinks();

        int getToGroup();

        int getJoinGroup();

        int getReport();

        int getHide();

        int getUnsubscribe();
    }

}
