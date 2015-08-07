package com.epam.lab.spider.model;

/**
 * @author Yura Kovalik
 */
public class PersistenceGlobalIdentifier {
    private Class<? extends PersistenceIdentifiable> clazz;
    private Integer id;

    public PersistenceGlobalIdentifier(Class<? extends PersistenceIdentifiable> clazz, Integer id) {
        this.clazz = clazz;
        this.id = id;
    }

    public Class<? extends PersistenceIdentifiable> getClazz() {
        return clazz;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersistenceGlobalIdentifier that = (PersistenceGlobalIdentifier) o;

        if (!clazz.equals(that.clazz)) return false;
        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        int result = clazz.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
