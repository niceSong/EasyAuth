package com.tyytogether.jwt;

import com.google.gson.Gson;
import com.tyytogether.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class JwtTools {

    @Autowired
    private EasyJWT easyJWT;

    @Autowired
    private RedisRepository redisRepository;

    private Gson gson = new Gson();

    public void tokenObjSaveToRedis(Object tokenObj, Long expire, TimeUnit timeUnit) {
        String tokenInfo = gson.toJson(tokenObj);
        String token = easyJWT.sign(tokenInfo);
        // key   -> ${tokenInfo}
        // value -> ${token}
        redisRepository.set(tokenInfo, token, expire, timeUnit);
    }

    public String parseToken(String token) {
        try{
            return easyJWT.verify(token);
        }catch (Exception e){
            throw e;
        }
    }
}
