package com.sample.controller;

import com.sample.Service.TestService;
import com.tyytogether.api.EasyAuth;
import com.sample.user.SampleUserBase;
import com.tyytogether.tools.EasyAuthJwtTools;
import com.tyytogether.user.UserBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class SampleController {

    @Autowired
    private EasyAuthJwtTools easyAuthJwtTools;

    @Autowired
    private EasyAuth easyAuth;

    @Autowired
    private TestService testService;

    @GetMapping("/sample/auth")
    public void sample(){
        easyAuth.auth("changeUserAmount", ()->{
            testService.test();
        });
        System.out.println("认证/鉴权，成功");
    }

    @GetMapping("/sample/login")
    public void loginSample(){
        UserBase user = new SampleUserBase("someInfo", "1", "Admin");
        easyAuthJwtTools.generateToken(user,1L, TimeUnit.HOURS);
    }
}
