package com.tyytogether.api;

import com.google.gson.Gson;
import com.tyytogether.base.UserBase;
import com.tyytogether.driver.RoleMap;
import com.tyytogether.functional.DiyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class EasyAuth extends OncePerRequestFilter{
    String needPermission;
    DiyFilter diyFilter;
    Gson gson = new Gson();

    public void auth(String needPermission, DiyFilter diyFilter){
        this.needPermission = needPermission;
        this.diyFilter = diyFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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

        if( !RoleMap.INSTANCE.getMap$easy_auth_authentication().containsKey(tokenObj.getRole()) ){
            throw new IllegalStateException("User role: ${tokenObj.role}，undefine with 'EasyAuthRole' annotation");
        }

        if(!RoleMap.INSTANCE.getMap$easy_auth_authentication().get(tokenObj.getRole()).contains(needPermission)){
            throw new IllegalStateException("User role: ${tokenObj.role}，do not have permission: ${this.needPermission}");
        }

        /**
         * 用户自定义处理内容
         * */
        try {
            this.diyFilter.diyFilter();
        }catch (Exception e){
            throw e;
        }

        doFilter(request, response, filterChain);
    }
}