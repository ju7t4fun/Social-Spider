package com.epam.lab.spider.model.entity;


import com.epam.lab.spider.model.PersistenceIdentifiable;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

/**
 * @author Yura Kovalik
 * @since 1.0.2
 */
public interface Task extends PersistenceIdentifiable {

    Set<Wall> getDestination();

    void setDestination(Set<Wall> destination);

    boolean addDestination(Wall wall);

    boolean removeDestination(Wall wall);

    Set<Wall> getSource();

    void setSource(Set<Wall> source);

    boolean addSource(Wall wall);

    boolean removeSource(Wall wall);

    Filter getFilter();

    void setFilter(Filter filter);

    Integer getId();


    Integer getUserId();

    void setUserId(Integer userId);

    Integer getFilterId();

    void setFilterId(Integer filterId);

    Type getType();

    void setType(Type type);

    State getState();

    void setState(State state);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    ContentType getContentType();

    void setContentType(ContentType contentType);

    StartTimeType getStartTimeType();

    void setStartTimeType(StartTimeType startTimeType);

    Date getNextTaskRunDate();

    void setNextTaskRunDate(Date nextTaskRunDate);

    Set<PeriodIntervalRecord> getDayPeriod();

    void setDayPeriod(Set<PeriodIntervalRecord> dayPeriod);

    Set<ScheduleRecord> getSchedulers();

    void setSchedulers(Set<ScheduleRecord> schedulers);

    Integer getGrabbingSize();

    void setGrabbingSize(Integer grabbingSize);

    Integer getPostCount();

    void setPostCount(Integer postCount);

    Integer getPostDelayMax();

    void setPostDelayMax(Integer postDelayMax);

    Integer getPostDelayMin();

    void setPostDelayMin(Integer postDelayMin);

    Integer getIntervalMax();

    void setIntervalMax(Integer intervalMax);

    Integer getIntervalMin();

    void setIntervalMin(Integer intervalMin);

    String getHashTags();

    void setHashTags(String hashTags);

    String getSignature();

    void setSignature(String signature);

    ActionAfterPosting getActionAfterPosting();

    void setActionAfterPosting(ActionAfterPosting actionAfterPosting);

    GrabbingMode getGrabbingMode();

    void setGrabbingMode(GrabbingMode grabbingMode);

    WorkTimeLimit getWorkTimeLimit();

    void setWorkTimeLimit(WorkTimeLimit workTimeLimit);

    GrabbingType getGrabbingType();

    void setGrabbingType(GrabbingType grabbingType);

    Integer getRepeatCount();

    void setRepeatCount(Integer repeatCount);

    Repeat getRepeat();

    void setRepeat(Repeat repeat);

    enum Type {
        COPY, REPOST, FAVORITE
    }

    enum State {
        RUNNING, STOPPED, ERROR
    }

    enum Repeat {
        REPEAT_DISABLE, REPEAT_ON_TIME, REPEAT_ON_COUNT
    }

    enum GrabbingType {
        BEGIN,RANDOM,END,NEW
    }

    enum StartTimeType {
        INTERVAL, SCHEDULE, CRON
    }


    enum WorkTimeLimit {
        ROUND_DAILY, DAY_PERIOD
    }

    enum GrabbingMode {
        TOTAL, PER_GROUP
    }

    enum ActionAfterPosting {
        LIKE, REPOST, DO_NOTHING
    }

    class ContentType {
        public static final int TEXT = 1;
        public static final int PHOTO = 1<<1;
        public static final int AUDIO = 1<<2;
        public static final int VIDEO = 1<<3;
        public static final int DOCUMENTS =1<<4;
        public static final int HASH_TAGS = 1 << 5;
        public static final int LINKS = 1<<6;
        public static final int PAGES = 1<<7;
        public static final int RE_POSTS = 1 << 8;
        public static final int SIMPLE_TITLE = 1<<9;
        public static final int TEXT_TITLE = 1<<10;
        int type = 0;

        public ContentType() {
        }

        public ContentType(Integer type) {
            this.type = type;
        }

        public boolean hasText(){
            return (type & TEXT)!=0 ;
        }

        public boolean hasPhoto(){
            return (type & PHOTO)!=0 ;
        }

        public boolean hasAudio(){
            return (type & AUDIO)!=0 ;
        }

        public boolean hasVideo(){
            return (type & VIDEO)!=0 ;
        }

        public boolean hasDoc(){
            return (type & DOCUMENTS)!=0 ;
        }

        public boolean hasHashTags() {
            return (type & HASH_TAGS) != 0;
        }

        public boolean hasLinks(){
            return (type & LINKS)!=0 ;
        }

        public boolean hasPages(){
            return (type & PAGES)!=0 ;
        }

        public boolean hasRePosts() {
            return (type & RE_POSTS) != 0;
        }

        public boolean hasSimpleTitle(){
            return (type & SIMPLE_TITLE)!=0 ;
        }

        public boolean hasTextTitle(){
            return (type & TEXT_TITLE)!=0 ;
        }

        @Override
        public String toString() {

            StringBuilder contentTypeStringBuilder = new StringBuilder();
            Task.ContentType contentType = this;
            if(contentType.hasText())contentTypeStringBuilder.append("text").append(", ");
            if(contentType.hasPhoto())contentTypeStringBuilder.append("photo").append(", ");
            if(contentType.hasAudio())contentTypeStringBuilder.append("audio").append(", ");
            if(contentType.hasVideo())contentTypeStringBuilder.append("video").append(", ");
            if(contentType.hasDoc())contentTypeStringBuilder.append("doc").append(", ");
            if (contentType.hasHashTags()) contentTypeStringBuilder.append("hash tag").append(", ");
            if(contentType.hasLinks())contentTypeStringBuilder.append("links").append(", ");
            if(contentType.hasPages())contentTypeStringBuilder.append("pages").append(", ");
            if (contentType.hasRePosts()) contentTypeStringBuilder.append("repost").append(", ");
            String result;
            if(contentTypeStringBuilder.length()>2)result = contentTypeStringBuilder.substring(0,contentTypeStringBuilder.length()-2);
            else result = "";
            return result;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public void addType(Integer type) {
            this.type |= type;
        }

    }

    class ScheduleRecord {
        private Integer id;
        private Time time;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Time getTime() {
            return time;
        }

        public void setTime(Time time) {
            this.time = time;
        }
    }

    class PeriodIntervalRecord {
        private Integer id;
        private Time start;
        private Time finish;

        public Time getFinish() {
            return finish;
        }

        public void setFinish(Time finish) {
            this.finish = finish;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Time getStart() {
            return start;
        }

        public void setStart(Time start) {
            this.start = start;
        }

    }

}
