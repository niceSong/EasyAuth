package com.tyytogether.controller;

import com.tyytogether.api.EasyAuth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

//    @Autowired
//    private EasyAuthJwtTools easyAuthJwtTools;

    @GetMapping("/sample")
    public void sample(){
        new EasyAuth().auth("changeUserAmount", null);
        System.out.println("认证/鉴权，成功");
    }

//    @PutMapping("/login")
//    public void login(){
//        UserBase user = new SampleUserBase("someInfo", "1", "Admin");
//        easyAuthJwtTools.generateToken(user,5L, TimeUnit.MINUTES);
//    }
}
