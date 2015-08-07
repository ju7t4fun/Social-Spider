package com.epam.lab.spider.job.util;

import com.epam.lab.spider.model.entity.DataLock;
import com.epam.lab.spider.persistence.service.DataLockService;

import java.util.List;

/**
 * @author Yura Kovalik
 */
public class UserUnlock {
    private static final Locker LOCKER = Locker.getInstance();
    private static DataLockService dataLockService = new DataLockService();

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
