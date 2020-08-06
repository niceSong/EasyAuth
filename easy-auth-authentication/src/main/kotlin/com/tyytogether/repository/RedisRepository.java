package com.tyytogether.repository;

import java.util.concurrent.TimeUnit;

public interface RedisRepository {

    void set(String key, String value, Long expire, TimeUnit timeUnit);

}
