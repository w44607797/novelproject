package com.guo.controller;



import com.guo.bean.BaseEntity;
import com.guo.service.mapper.AccountService;
import com.guo.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    AccountService accountService;

    //用户登录api

    @PostMapping("/login")
    public BaseEntity login(@RequestParam("userid") String userid,
                            @RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        String salt;
        try {
            salt = accountService.getSalt(Integer.parseInt(userid));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.error("登入传入的id包含非法字符");
            return BaseEntity.failed(100,"用户账号只能为0-9数字");
        }
        if(salt==null){
            return BaseEntity.failed(110,"服务端查询出错，请联系管理员");
        }
        String securityPassowrd = SecurityUtil.toMd5(password,salt);

        UsernamePasswordToken token = new UsernamePasswordToken(userid,securityPassowrd);
        try {
            subject.login(token);
            return BaseEntity.success();
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            e.printStackTrace();
            log.info("用户名或密码错误");
            return BaseEntity.failed(101, "用户名或密码错误");
        }
    }

    //登出

    @RequestMapping("/logout")
    @RequiresRoles("user")
    public BaseEntity logoutUser() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        log.info("用户登出");
        return BaseEntity.success("用户登出");
    }

    //注册

    @PostMapping("/register")
    public BaseEntity userRegister(@RequestParam("password") String password,
                                   @RequestParam("username") String username,
                                   @RequestParam("userid") int userid) {
        if (accountService.insertUser(username, userid, password)) {
            return BaseEntity.success();
        }

        return BaseEntity.failed(102, "id已存在或注册插入错误");
    }

    @GetMapping("/nologin")
    public BaseEntity noLoginPage() {
        return BaseEntity.failed(103, "还未登录");
    }

}
