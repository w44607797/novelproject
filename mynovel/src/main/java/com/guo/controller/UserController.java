package com.guo.controller;


import com.guo.bean.Account;
import com.guo.bean.BaseEntity;
import com.guo.service.mapper.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Slf4j
public class UserController {

    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    public BaseEntity login(String userid,String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userid,password);
        try {
            subject.login(token);
//            if(subject.isAuthenticated()){
////                System.out.println(subject.hasRole("admin"));
////                System.out.println(subject.hasAllRoles(Arrays.asList("admin","superadmin")));
////                System.out.println(subject.isPermitted("user:update:*"));
//
//
//            }
            return BaseEntity.success();
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            e.printStackTrace();
            log.info("用户名或密码错误");
            return BaseEntity.failed(101,"用户名或密码错误");
        }


    }


    @RequestMapping("/logout")
    public BaseEntity logoutUser(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        log.info("用户登出");
        return BaseEntity.success("用户登出");

    }

    @PostMapping("/Register")
    public BaseEntity userRegister(@RequestParam("username")String userName,
                               @RequestParam("userpassword")String userpassword,
                               @RequestParam("userid")int userId){
        if(accountService.insertUser(userName,userId,userpassword)){
            return BaseEntity.success();
        }
        return BaseEntity.failed(102,"id已存在或注册插入错误");
    }

}
