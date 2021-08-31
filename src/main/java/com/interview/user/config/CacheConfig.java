package com.interview.user.config;

import com.interview.user.cache.UserCache;
import com.interview.user.cache.UserCacheImpl;
import com.interview.user.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static com.interview.user.common.Constants.*;

@Configuration
public class CacheConfig {
    private static final String PROFILE_CACHE = "profileCache";
    private static final String PASSWORD_RESET_CACHE = "passwordResetCache";
    private static final String ACTIVE_PROFILE_CACHE = "activeProfileCache";
    private static final String TOKEN_CACHE = "tokenCache";
    private final ServiceConfig serviceConfig;

    @Autowired
    public CacheConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }


    @Bean
    @Qualifier("profileRedisTemplate")
    public RedisTemplate profileRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer valueSerializer = new Jackson2JsonRedisSerializer(Profile.class);
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        return template;
    }

    @Bean
    @Qualifier("genericRedisTemplate")
    public RedisTemplate genericRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        return template;
    }

    @Bean(name = PROFILE_CACHE)
    public UserCache profileCache(@Qualifier("profileRedisTemplate") RedisTemplate profileRedisTemplate) {
        return new UserCacheImpl<>(PROFILE_CACHE, profileRedisTemplate, serviceConfig.getProfileCacheDuration(),
                PROFILE_CACHE_PREFIX);
    }

    @Bean(name = PASSWORD_RESET_CACHE)
    public UserCache passwordResetCache(@Qualifier("genericRedisTemplate") RedisTemplate genericRedisTemplate) {
        return new UserCacheImpl<>(PASSWORD_RESET_CACHE, genericRedisTemplate, serviceConfig.getPasswordResetCacheDuration(),
                PASSWORD_RESET_CACHE_PREFIX);
    }

    @Bean(name = ACTIVE_PROFILE_CACHE)
    public UserCache activeProfileCache(@Qualifier("genericRedisTemplate") RedisTemplate genericRedisTemplate) {
        return new UserCacheImpl<>(ACTIVE_PROFILE_CACHE, genericRedisTemplate, serviceConfig.getActiveProfileCacheDuration(),
                ACTIVE_PROFILE_CACHE_PREFIX);
    }

    @Bean(name = TOKEN_CACHE)
    public UserCache tokenCache(@Qualifier("genericRedisTemplate") RedisTemplate genericRedisTemplate) {
        return new UserCacheImpl<>(TOKEN_CACHE, genericRedisTemplate, serviceConfig.getTokenCacheDuration(),
                TOKEN_CACHE_PREFIX);
    }
}