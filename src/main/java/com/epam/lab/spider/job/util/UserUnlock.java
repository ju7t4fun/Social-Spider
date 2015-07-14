package com.epam.lab.spider.job.util;

import com.epam.lab.spider.model.db.entity.DataLock;
import com.epam.lab.spider.model.db.service.DataLockService;

import java.util.List;

/**
 * Created by hell-engine on 7/10/2015.
 */
public class UserUnlock {
    private static DataLockService dataLockService = new DataLockService();
    private static final Locker LOCKER = Locker.getInstance();
    public static boolean forceFullUserUnlock(Integer userId){
        List<DataLock> locks = dataLockService.dataLockByUser(userId);
        for(DataLock lock:locks){
            LOCKER.unLock(lock);
        }
        return !locks.isEmpty();
    }
    public static void main(String[] args){
        forceFullUserUnlock(1);
    }
}
