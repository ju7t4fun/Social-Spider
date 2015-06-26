package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.model.db.service.FilterService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 * Update by shell on 25.06.2015
 */
public class Task {

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final WallService service = factory.create(WallService.class);
    private static final FilterService filterService = factory.create(FilterService.class);

    private Integer id;
    private Integer userId;
    private Integer filterId;
    private Type type;
    private State state = State.STOPPED;
    private Boolean deleted = false;

    private Filter filter = null;
    private Set<Wall> destination = null;
    private Set<Wall> source = null;
    /**
     * Field added by shell at 25.06.2015
     */
    private Date nextTaskRunDate;
    private ContentType contentType = new ContentType(ContentType.TEXT|ContentType.Audio);
    private StartTimeType startTimeType = StartTimeType.INTERVAL;
    private WorkTimeLimit workTimeLimit = WorkTimeLimit.ROUND_DAILY;
    private GrabbingMode grabbingMode = GrabbingMode.PER_GROUP;
    private ActionAfterPosting actionAfterPosting = ActionAfterPosting.DO_NOTHING;
    private String signature;
    private String hashTags;
    // in minutes
    private Integer intervalMin = 3;
    private Integer intervalMax = 10;
    // in seconds
    private Integer postDelayMin = 5;
    private Integer postDelayMax = 30;
    //
    private Integer postCount = 1;
    private Integer grabbingSize = 10;
    //
    private Set<ScheduleRecord> schedulers = null;
    private Set<PeriodIntervalRecord> dayPeriod = null;
    /**
     * End of field added by shell
     */
    public enum Type {
        COPY, REPOST, FAVORITE
    }

    public enum State {
        RUNNING, STOPPED, ERROR
    }

    /**
     * Types added by shell at 25.06.2015
     */
    public enum StartTimeType{
        INTERVAL, SCHEDULE, CRON
    }
    public enum WorkTimeLimit{
        ROUND_DAILY, DAY_PERIOD
    }
    public enum GrabbingMode {
        TOTAL, PER_GROUP
    }
    public enum ActionAfterPosting{
        LIKE, REPOST, DO_NOTHING
    }
    public static class ContentType{
        public static final int TEXT    = 1<<0;
        public static final int Photo   = 1<<1;
        public static final int Audio   = 1<<2;
        public static final int Video   = 1<<3;
        public static final int Documents=1<<4;
        public static final int Hashtags =1<<5;
        public static final int Links   = 1<<6;
        public static final int Pages   = 1<<7;
        public static final int Reposts = 1<<8;

        public boolean hasText(){
            return (type & TEXT)!=0 ;
        }
        public boolean hasPhoto(){
            return (type & Photo)!=0 ;
        }
        public boolean hasAudio(){
            return (type & Audio)!=0 ;
        }
        public boolean hasVideo(){
            return (type & Video)!=0 ;
        }
        public boolean hasHashtags(){
            return (type & Hashtags)!=0 ;
        }
        public boolean hasLinks(){
            return (type & Links)!=0 ;
        }
        public boolean hasPages(){
            return (type & Pages)!=0 ;
        }
        public boolean hasReposts(){
            return (type & Reposts)!=0 ;
        }

        public ContentType() {
        }

        public ContentType(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
        int type;

    }
    public static class ScheduleRecord{
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
    public static class PeriodIntervalRecord{
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
    /**
     * End of types added by shell
     */

    public Set<Wall> getDestination() {
        if (destination == null) {
            if (id == null)
                destination = new HashSet<>();
            else
                destination = new HashSet<>(service.getDestinationByTaskId(id));
        }
        return destination;
    }

    public boolean addDestination(Wall wall) {
        return getDestination().add(wall);
    }

    public boolean removeDestination(Wall wall) {
        return getDestination().remove(wall);
    }

    public Set<Wall> getSource() {
        if (source == null) {
            if (id == null)
                source = new HashSet<>();
            else
                source = new HashSet<>(service.getSourceByTaskId(id));
        }
        return source;
    }

    public boolean addSource(Wall wall) {
        return getSource().add(wall);
    }

    public boolean removeSource(Wall wall) {
        return getSource().remove(wall);
    }

    public Filter getFilter() {
        if (filter == null) {
            if (filterId == null)
                filter = new Filter();
            else
                filter = filterService.getById(filterId);
        }
        return filter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filter_id) {
        this.filterId = filter_id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setDestination(Set<Wall> destination) {
        this.destination = destination;
    }

    public void setSource(Set<Wall> source) {
        this.source = source;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public StartTimeType getStartTimeType() {
        return startTimeType;
    }

    public void setStartTimeType(StartTimeType startTimeType) {
        this.startTimeType = startTimeType;
    }

    public Date getNextTaskRunDate() {
        return nextTaskRunDate;
    }

    public void setNextTaskRunDate(Date nextTaskRunDate) {
        this.nextTaskRunDate = nextTaskRunDate;
    }

    public Set<PeriodIntervalRecord> getDayPeriod() {
        return dayPeriod;
    }

    public void setDayPeriod(Set<PeriodIntervalRecord> dayPeriod) {
        this.dayPeriod = dayPeriod;
    }

    public Set<ScheduleRecord> getSchedulers() {
        return schedulers;
    }

    public void setSchedulers(Set<ScheduleRecord> schedulers) {
        this.schedulers = schedulers;
    }

    public Integer getGrabbingSize() {
        return grabbingSize;
    }

    public void setGrabbingSize(Integer grabbingSize) {
        this.grabbingSize = grabbingSize;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getPostDelayMax() {
        return postDelayMax;
    }

    public void setPostDelayMax(Integer postDelayMax) {
        this.postDelayMax = postDelayMax;
    }

    public Integer getPostDelayMin() {
        return postDelayMin;
    }

    public void setPostDelayMin(Integer postDelayMin) {
        this.postDelayMin = postDelayMin;
    }

    public Integer getIntervalMax() {
        return intervalMax;
    }

    public void setIntervalMax(Integer intervalMax) {
        this.intervalMax = intervalMax;
    }

    public Integer getIntervalMin() {
        return intervalMin;
    }

    public void setIntervalMin(Integer intervalMin) {
        this.intervalMin = intervalMin;
    }

    public String getHashTags() {
        return hashTags;
    }

    public void setHashTags(String hashTags) {
        this.hashTags = hashTags;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public ActionAfterPosting getActionAfterPosting() {
        return actionAfterPosting;
    }

    public void setActionAfterPosting(ActionAfterPosting actionAfterPosting) {
        this.actionAfterPosting = actionAfterPosting;
    }

    public GrabbingMode getGrabbingMode() {
        return grabbingMode;
    }

    public void setGrabbingMode(GrabbingMode grabbingMode) {
        this.grabbingMode = grabbingMode;
    }

    public WorkTimeLimit getWorkTimeLimit() {
        return workTimeLimit;
    }

    public void setWorkTimeLimit(WorkTimeLimit workTimeLimit) {
        this.workTimeLimit = workTimeLimit;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userId=" + userId +
                ", filterId=" + filterId +
                ", type=" + type +
                ", state=" + state +
                ", deleted=" + deleted +
                ", filter=" + filter +
                ", destination=" + destination +
                ", source=" + source +
                '}';
    }

}
