package com.tyytogether.api;

import com.google.gson.Gson;
import com.tyytogether.user.UserBase;
import com.tyytogether.driver.RoleMap;
import com.tyytogether.functional.DiyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class EasyAuth{
    Gson gson = new Gson();

    @Autowired
    private HttpServletRequest request;

    public void auth(String needPermission, DiyFilter diyFilter) {
        /**
         * 框架处理内容
        * */
        // 认证
        String headerTokenInfo = request.getHeader("tokenInfo");
        if(headerTokenInfo == null){
            throw new RuntimeException("Certification failed, please log in again");
        }

        // 鉴权
        UserBase tokenObj = gson.fromJson(headerTokenInfo, UserBase.class);

        if( !RoleMap.INSTANCE.getMap$easy_auth_authentication().containsKey(tokenObj.getRole().toLowerCase()) ){
            throw new IllegalStateException(String.format("User role: %s，undefine with 'EasyAuthRole' annotation",tokenObj.getRole()));
        }

        if(!RoleMap.INSTANCE.getMap$easy_auth_authentication()
                .get(tokenObj.getRole().toLowerCase())
                .contains(needPermission)){
            throw new IllegalStateException(String.format("User role: %s，don`t have permission: %s",tokenObj.getRole(), needPermission));
        }

        /**
        * 用户自定义处理内容
        * */
        if(diyFilter != null){
            try {
                diyFilter.diyFilter();
            }catch (Exception e){
                throw e;
            }
        }

    }
}