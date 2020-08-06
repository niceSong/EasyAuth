package com.tyytogether.repository.impl;

import com.tyytogether.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(String key, String value, Long expire, TimeUnit timeUnit) {
        String newKey = this.assembleRedisKey(key);
        redisTemplate.opsForValue().set(newKey, value, expire, timeUnit);
    }

    private String assembleRedisKey(String primaryKey) {
        return "EASY_AUTH"  + ":" + primaryKey;
    }
}
