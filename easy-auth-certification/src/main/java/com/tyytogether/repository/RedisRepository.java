package com.tyytogether.repository;

import java.util.concurrent.TimeUnit;

public interface RedisRepository {

    String get(String key);

}
