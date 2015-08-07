package com.epam.lab.spider.model;

/**
 * @author Yura Kovalik
 */
public abstract class EntitySynchronized<E extends PersistenceIdentifiable> {
    private E entity;
    private boolean sync;

    public EntitySynchronized(E entity) {
        this.entity = entity;
    }

    public boolean isSynchronized() {
        return sync;
    }

    public void setSynchronized() {
        this.sync = true;
    }

    public void setUnSynchronized() {
        this.sync = false;
    }

    protected E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    public Integer getId() {
        return getEntity().getId();
    }
}
