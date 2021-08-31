package com.interview.user.cache;

public interface UserCache<V> {
    void putElement(String key, V value);

    V getElement(String key);

    void removeElement(String key);

    boolean isCacheEmpty(String key);

    void refreshCache(String key, V value);
}
