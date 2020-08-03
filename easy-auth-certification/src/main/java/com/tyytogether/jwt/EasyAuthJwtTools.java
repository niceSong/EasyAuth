package com.tyytogether.jwt;

import com.google.gson.Gson;
import com.tyytogether.base.UserBase;
import com.tyytogether.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class EasyAuthJwtTools {

    @Autowired
    private EasyJWT easyJWT;

    @Autowired
    private RedisRepository redisRepository;

    private Gson gson = new Gson();

    public <T extends UserBase> String generateToken(T userInfoObj, Long expire, TimeUnit timeUnit){
        String tokenInfo = gson.toJson(userInfoObj);
        String token = easyJWT.sign(tokenInfo);
        // key   -> ${tokenInfo}
        // value -> ${token}
        redisRepository.set(tokenInfo, token, expire, timeUnit);
        return token;
    }

    public String parseToken(String token) {
        try{
            return easyJWT.verify(token);
        }catch (Exception e){
            throw e;
        }
    }
}
