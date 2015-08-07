package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;

import java.util.Date;
import java.util.Set;

/**
 * @author Marian Voronovskyi
 * @author Yura Kovalik
 */
public class TaskImpl implements Task, PersistenceIdentificationChangeable {
    private Integer id;
    private Integer userId;
    private Integer filterId;
    private Type type = Type.COPY;
    private State state = State.STOPPED;
    private Boolean deleted = false;
    private Filter filter = null;
    private Set<Wall> destination = null;
    private Set<Wall> source = null;

    private Date nextTaskRunDate;
    private ContentType contentType = new ContentType(ContentType.TEXT | ContentType.AUDIO);
    private StartTimeType startTimeType = StartTimeType.INTERVAL;
    private WorkTimeLimit workTimeLimit = WorkTimeLimit.ROUND_DAILY;
    private GrabbingMode grabbingMode = GrabbingMode.PER_GROUP;
    private GrabbingType grabbingType = GrabbingType.BEGIN;
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
    private Repeat repeat = Repeat.REPEAT_DISABLE;
    private Integer repeatCount = 90;
    //
    private Set<ScheduleRecord> schedulers = null;
    private Set<PeriodIntervalRecord> dayPeriod = null;

    @Override
    public Set<Wall> getDestination() {
        return destination;
    }

    public void setDestination(Set<Wall> destination) {
        this.destination = destination;
    }

    public boolean addDestination(Wall wall) {
        return getDestination().add(wall);
    }

    public boolean removeDestination(Wall wall) {
        return getDestination().remove(wall);
    }

    public Set<Wall> getSource() {
        return source;
    }

    public void setSource(Set<Wall> source) {
        this.source = source;
    }

    public boolean addSource(Wall wall) {
        return getSource().add(wall);
    }

    public boolean removeSource(Wall wall) {
        return getSource().remove(wall);
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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
        if (filter != null) {
            return filter.getId();
        }
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
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

    public GrabbingType getGrabbingType() {
        return grabbingType;
    }

    public void setGrabbingType(GrabbingType grabbingType) {
        this.grabbingType = grabbingType;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Repeat getRepeat() {
        return repeat;
    }

    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
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
