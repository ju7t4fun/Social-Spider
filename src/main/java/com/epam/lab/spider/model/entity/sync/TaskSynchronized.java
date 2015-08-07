package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;

import java.util.Date;
import java.util.Set;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class TaskSynchronized extends EntitySynchronized<Task> implements Task {
    public TaskSynchronized(Task entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public Set<Wall> getDestination() {
        return getEntity().getDestination();
    }

    public void setDestination(Set<Wall> destination) {
        setUnSynchronized();
        setUnSynchronized();
        getEntity().setDestination(destination);
    }

    public boolean addDestination(Wall wall) {
        return getEntity().addDestination(wall);
    }

    public boolean removeDestination(Wall wall) {
        return getEntity().removeDestination(wall);
    }

    public Set<Wall> getSource() {
        return getEntity().getSource();
    }

    public void setSource(Set<Wall> source) {
        setUnSynchronized();
        getEntity().setSource(source);
    }

    public boolean addSource(Wall wall) {
        return getEntity().addSource(wall);
    }

    public boolean removeSource(Wall wall) {
        return getEntity().removeSource(wall);
    }

    public Filter getFilter() {
        return getEntity().getFilter();
    }

    public void setFilter(Filter filter) {
        setUnSynchronized();
        getEntity().setFilter(filter);
    }

    public Integer getId() {
        return getEntity().getId();
    }

    public Integer getUserId() {
        return getEntity().getUserId();
    }

    public void setUserId(Integer userId) {
        setUnSynchronized();
        getEntity().setUserId(userId);
    }

    public Integer getFilterId() {
        return getEntity().getFilterId();
    }

    public void setFilterId(Integer filterId) {
        setUnSynchronized();
        getEntity().setFilterId(filterId);
    }

    public Type getType() {
        return getEntity().getType();
    }

    public void setType(Type type) {
        setUnSynchronized();
        getEntity().setType(type);
    }

    public State getState() {
        return getEntity().getState();
    }

    public void setState(State state) {
        setUnSynchronized();
        getEntity().setState(state);
    }

    public Boolean getDeleted() {
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        setUnSynchronized();
        getEntity().setDeleted(deleted);
    }

    public ContentType getContentType() {
        return getEntity().getContentType();
    }

    public void setContentType(ContentType contentType) {
        setUnSynchronized();
        getEntity().setContentType(contentType);
    }

    public StartTimeType getStartTimeType() {
        return getEntity().getStartTimeType();
    }

    public void setStartTimeType(StartTimeType startTimeType) {
        setUnSynchronized();
        getEntity().setStartTimeType(startTimeType);
    }

    public Date getNextTaskRunDate() {
        return getEntity().getNextTaskRunDate();
    }

    public void setNextTaskRunDate(Date nextTaskRunDate) {
        setUnSynchronized();
        getEntity().setNextTaskRunDate(nextTaskRunDate);
    }

    public Set<PeriodIntervalRecord> getDayPeriod() {
        return getEntity().getDayPeriod();
    }

    public void setDayPeriod(Set<PeriodIntervalRecord> dayPeriod) {
        setUnSynchronized();
        getEntity().setDayPeriod(dayPeriod);
    }

    public Set<ScheduleRecord> getSchedulers() {
        return getEntity().getSchedulers();
    }

    public void setSchedulers(Set<ScheduleRecord> schedulers) {
        setUnSynchronized();
        getEntity().setSchedulers(schedulers);
    }

    public Integer getGrabbingSize() {
        return getEntity().getGrabbingSize();
    }

    public void setGrabbingSize(Integer grabbingSize) {
        setUnSynchronized();
        getEntity().setGrabbingSize(grabbingSize);
    }

    public Integer getPostCount() {
        return getEntity().getPostCount();
    }

    public void setPostCount(Integer postCount) {
        setUnSynchronized();
        getEntity().setPostCount(postCount);
    }

    public Integer getPostDelayMax() {
        return getEntity().getPostDelayMax();
    }

    public void setPostDelayMax(Integer postDelayMax) {
        setUnSynchronized();
        getEntity().setPostDelayMax(postDelayMax);
    }

    public Integer getPostDelayMin() {
        return getEntity().getPostDelayMin();
    }

    public void setPostDelayMin(Integer postDelayMin) {
        setUnSynchronized();
        getEntity().setPostDelayMin(postDelayMin);
    }

    public Integer getIntervalMax() {
        return getEntity().getIntervalMax();
    }

    public void setIntervalMax(Integer intervalMax) {
        setUnSynchronized();
        getEntity().setIntervalMax(intervalMax);
    }

    public Integer getIntervalMin() {
        return getEntity().getIntervalMin();
    }

    public void setIntervalMin(Integer intervalMin) {
        setUnSynchronized();
        getEntity().setIntervalMin(intervalMin);
    }

    public String getHashTags() {
        return getEntity().getHashTags();
    }

    public void setHashTags(String hashTags) {
        setUnSynchronized();
        getEntity().setHashTags(hashTags);
    }

    public String getSignature() {
        return getEntity().getSignature();
    }

    public void setSignature(String signature) {
        setUnSynchronized();
        getEntity().setSignature(signature);
    }

    public ActionAfterPosting getActionAfterPosting() {
        return getEntity().getActionAfterPosting();
    }

    public void setActionAfterPosting(ActionAfterPosting actionAfterPosting) {
        setUnSynchronized();
        getEntity().setActionAfterPosting(actionAfterPosting);
    }

    public GrabbingMode getGrabbingMode() {
        return getEntity().getGrabbingMode();
    }

    public void setGrabbingMode(GrabbingMode grabbingMode) {
        setUnSynchronized();
        getEntity().setGrabbingMode(grabbingMode);
    }

    public WorkTimeLimit getWorkTimeLimit() {
        return getEntity().getWorkTimeLimit();
    }

    public void setWorkTimeLimit(WorkTimeLimit workTimeLimit) {
        setUnSynchronized();
        getEntity().setWorkTimeLimit(workTimeLimit);
    }

    public GrabbingType getGrabbingType() {
        return getEntity().getGrabbingType();
    }

    public void setGrabbingType(GrabbingType grabbingType) {
        setUnSynchronized();
        getEntity().setGrabbingType(grabbingType);
    }

    public Integer getRepeatCount() {
        return getEntity().getRepeatCount();
    }

    public void setRepeatCount(Integer repeatCount) {
        setUnSynchronized();
        getEntity().setRepeatCount(repeatCount);
    }

    public Repeat getRepeat() {
        return getEntity().getRepeat();
    }

    public void setRepeat(Repeat repeat) {
        setUnSynchronized();
        getEntity().setRepeat(repeat);
    }
}
