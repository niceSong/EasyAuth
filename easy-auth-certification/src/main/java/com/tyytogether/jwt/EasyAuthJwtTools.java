package com.tyytogether.jwt;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EasyAuthJwtTools {

    @Autowired
    private EasyJWT easyJWT;

    private Gson gson = new Gson();

    public String parseToken(String token) {
        try{
            return easyJWT.verify(token);
        }catch (Exception e){
            throw e;
        }
    }

}
