package com.guo.controller;

import com.guo.bean.Account;
import com.guo.bean.Novel;
import com.guo.service.mapper.AccountService;
import com.guo.service.mapper.NovelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author guokaifeng
 * @createDate: 2022/2/10
 **/


@RestController
@Slf4j
public class testController {

    @Autowired
    AccountService accountService;

    @Autowired
    NovelService novelService;

    @RequestMapping(value = "/account/{id}")
    public Account getAccount(@PathVariable("id") int id){
        return accountService.searchAccoundById(id);
    }

    @GetMapping(value = "/account")
    public String addAccount(@RequestParam(name = "username") String userName,
                             @RequestParam(name = "userid") Integer userId,
                             @RequestParam(name = "userpassword") String userPassword){

        return accountService.getPasswordById(userId);
    }






}
