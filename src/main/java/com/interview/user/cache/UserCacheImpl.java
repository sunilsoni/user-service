package com.interview.user.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
public class UserCacheImpl<V> implements UserCache<V> {
    private RedisTemplate redisTemplate;
    private Integer ttl;
    private String cachePrefix;
    private String name;

    @Autowired
    public UserCacheImpl(String name, RedisTemplate redisTemplate, Integer ttl, String cachePrefix) {
        this.name = name;
        this.redisTemplate = redisTemplate;
        this.ttl = ttl;
        this.cachePrefix = cachePrefix;

    }

    @Override
    public void putElement(final String key, final V value) {
        try {
            String formattedKey = getKey(key);
            redisTemplate.opsForValue().set(formattedKey, value);
            redisTemplate.expire(formattedKey, ttl, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.warn("Jedis connection exception, fails adding element in cache. ", e);
        }
    }

    @Override
    public V getElement(final String key) {
        return (V) redisTemplate.opsForValue().get(getKey(key));
    }


    @Override
    public void removeElement(String key) {
        try {
            redisTemplate.delete(getKey(key));
        } catch (Exception e) {
            log.warn("Jedis connection exception, fails remove element. ", e);
        }
    }


    @Override
    public boolean isCacheEmpty(String key) {
        return !redisTemplate.hasKey(getKey(key));
    }

    @Override
    public void refreshCache(String key, V value) {
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        putElement(key, value);
        redisTemplate.exec();
    }

    private String getKey(String key) {
        return cachePrefix.concat(key);
    }
}
