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
    public String get(String key){
        return redisTemplate.opsForValue().get(assembleRedisKey(key));
    }

    private String assembleRedisKey(String primaryKey) {
        return "EASY_AUTH"  + ":" + primaryKey;
    }
}
