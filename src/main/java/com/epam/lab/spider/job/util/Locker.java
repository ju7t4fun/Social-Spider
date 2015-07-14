package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.utils.EventLogger;
import com.epam.lab.spider.model.db.entity.DataLock;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.*;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by shell on 6/17/2015.
 */
public class Locker {
    public static final Logger LOG = Logger.getLogger(Locker.class);

    private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static DataLockService dataLockService = new DataLockService();

    private static NewPostService newPostService = serviceFactory.create(NewPostService.class);
    private static ProfileService profileService = serviceFactory.create(ProfileService.class);
    private static WallService wallService = serviceFactory.create(WallService.class);

    private static Locker ourInstance = null;
    private Map<String, Map<Integer, Set<DataLock.Mode>>> lockCache = new HashMap<>();

    private Locker() {
        lockCache = dataLockService.restore();
    }

    public static synchronized Locker getInstance() {
        if (ourInstance == null) {
            ourInstance = new Locker();
        }
        return ourInstance;
    }

    public synchronized boolean isLock(Wall wall) {
        return isLockOrNull("owner", wall.getOwnerId())
                || isLockOrNull("wall", wall.getId())
                || isLockOrNull("profile", wall.getProfile_id());

    }

    public synchronized void lock(Wall wall, DataLock.Mode mode) {
        Integer index;
        String table;
        Set<DataLock.Mode> modeSet;
        Map<Integer, Set<DataLock.Mode>> currentLockTable;


        if (mode == DataLock.Mode.ACCESS_DENY || mode == DataLock.Mode.DEFAULT || mode == DataLock.Mode.POST_LIMIT) {
            if (!isLock(wall)) {
                newPostService.setSpecialStageByWall(wall.getId(), NewPost.State.ERROR);
            }

            table = "wall";
            index = wall.getId();
            currentLockTable = lockCache.get(table);
            if (currentLockTable == null) {
                currentLockTable = new HashMap<>();
                lockCache.put(table, currentLockTable);
            }
            modeSet = currentLockTable.get(index);
            if (modeSet == null) {
                modeSet = new HashSet<>();
                currentLockTable.put(index, modeSet);
            }

            if (!modeSet.contains(mode)) {
                modeSet.add(mode);
                EventLogger eventLogger = EventLogger.getLogger(wall.getProfile().getUserId());
                if (mode == DataLock.Mode.ACCESS_DENY) {

                    eventLogger.error("Error. Access Deny.", "Wall #"+wall.getOwner().getVkId()+" is locked. Access deny. Bind another account and unblock.");
                } else if (mode == DataLock.Mode.DEFAULT || mode == DataLock.Mode.POST_LIMIT) {
                    eventLogger.error("Error. Limit of Post.", "Wall #"+wall.getOwner().getVkId()+" is locked. Limit of Post per group has expired. Please wait to next day and unblock.");
                }
                dataLockService.createLock(table, index, mode, wall.getProfile().getUserId());
            }
        }
    }

    public synchronized boolean isLock(Profile profile) {
        try {
            return lockCache.get("profile").containsKey(profile.getId());
        } catch (NullPointerException x) {
            LOG.debug("Object never used by here");
            return false;
        }
    }

    public synchronized void lock(Profile profile, DataLock.Mode mode) {
        Integer index;
        String table;
        Set<DataLock.Mode> modeSet;
        Map<Integer, Set<DataLock.Mode>> currentLockTable;
        if (mode == DataLock.Mode.DEFAULT || mode == DataLock.Mode.AUTH_KEY || mode == DataLock.Mode.CAPTCHA) {
            if (!isLock(profile)) {
                newPostService.setSpecialStageByProfile(profile.getId(), NewPost.State.ERROR);
            }
            table = "profile";
            index = profile.getId();
            currentLockTable = lockCache.get(table);
            if (currentLockTable == null) {
                currentLockTable = new HashMap<>();
                lockCache.put(table, currentLockTable);
            }
            modeSet = currentLockTable.get(index);
            if (modeSet == null) {
                modeSet = new HashSet<>();
                currentLockTable.put(index, modeSet);
            }
            if (!modeSet.contains(mode)) {
                modeSet.add(mode);

                EventLogger eventLogger = EventLogger.getLogger(profile.getUserId());
                if (mode == DataLock.Mode.DEFAULT || mode == DataLock.Mode.AUTH_KEY) {
                    eventLogger.error("Error. Reauthorized NEED!", "Profile #"+profile.getVkId()+" is locked. Please reauthorized to VK and unblock!");
                } else if (mode == DataLock.Mode.CAPTCHA) {
                    eventLogger.error("Error.  Captcha NEED!", "Profile #"+profile.getVkId()+" is locked. Captcha input need! " +
                            "Type captcha in VK and enlarge post delay or/and grabbing interval to resistance this error. Finally unblock account.");
                }

                EventLogger.getLogger(profile.getUserId());
                dataLockService.createLock(table, index, mode, profile.getUserId());
            }
        }
    }

    private boolean isLockOrNull(String table, Integer index) {
        try {
            return lockCache.get(table).containsKey(index);
        } catch (NullPointerException x) {
//            LOG.debug("Object never used by here");
            return false;
        }
    }

    public synchronized boolean isProfileLock(Integer index) {
        return isLockOrNull("profile", index);
    }

    public synchronized boolean isProfileReadableLock(Integer index) {
        try {
            boolean isLock = lockCache.get("profile").containsKey(index);
            if (!isLock) return false;

            Set<DataLock.Mode> set = new HashSet<>();
            set.addAll(lockCache.get("profile").get(index));
            set.remove(DataLock.Mode.CAPTCHA);

            return set.isEmpty();
        } catch (NullPointerException x) {
            //LOG.debug("Object never used by here");
            return false;
        }
    }

    public synchronized void unLock(DataLock dataLock) {
        DataLock.Mode mode = dataLock.getMode();
        Integer index = dataLock.getIndex();
        String table = dataLock.getTable();

        Set<DataLock.Mode> modeSet;
        Map<Integer, Set<DataLock.Mode>> currentLockTable;
        currentLockTable = lockCache.get(table);
        if (currentLockTable == null) {
            return;
        }
        modeSet = currentLockTable.get(index);
        if (modeSet == null) {
            return;
        }
        if (modeSet.contains(mode)) {
            modeSet.remove(mode);
            if (modeSet.isEmpty()) {
                currentLockTable.remove(index);
                dataLockService.deleteLock(table, index, mode);
            }
        }
        // TODO: REWRITE TO USE RESTORE WITH CORRECT POST TIME
        boolean needRestorePosts = false;
        if(needRestorePosts) {
            if (table.equals("profile")) {
                Profile profile = profileService.getById(index);
                List<Wall> walls = wallService.getWallsByProfileId(profile.getId());
                for (Wall wall : walls) {
                    if (!isLock(wall)) {
                        newPostService.setRestoredStageByWall(wall.getId());
//                    newPostService.setSpecialStageByWall(wall.getId(), NewPost.State.RESTORED);
                    }
                }
            }
            if (table.equals("wall")) {
                Wall wall = wallService.getById(index);
                if (!isLock(wall)) {
                    newPostService.setRestoredStageByWall(wall.getId());
                    newPostService.setSpecialStageByWall(wall.getId(), NewPost.State.RESTORED);
                }
            }
        }
    }
}
