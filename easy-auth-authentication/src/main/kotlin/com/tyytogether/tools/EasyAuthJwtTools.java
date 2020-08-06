package com.tyytogether.tools;

import com.google.gson.Gson;
import com.tyytogether.repository.RedisRepository;
import com.tyytogether.user.UserBase;
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
        redisRepository.set(userInfoObj.getId(), token, expire, timeUnit);
        return token;
    }
}
