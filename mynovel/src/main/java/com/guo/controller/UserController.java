package com.guo.controller;



import com.fasterxml.jackson.databind.ser.Serializers;
import com.guo.bean.BaseEntity;
import com.guo.bean.mapper.UserInfoMapper;
import com.guo.service.mapper.AccountService;
import com.guo.service.mapper.UserInfoService;
import com.guo.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.metadata.HanaCallMetaDataProvider;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserInfoService userInfoService;

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

            try {
                userInfoService.insertUserInfo(userid,username,null);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("数据库添加用户信息出错");
            }
            return BaseEntity.success();
        }

        return BaseEntity.failed(102, "id已存在或注册插入错误");
    }

    //没有登录的界面

    @GetMapping("/nologin")
    public BaseEntity noLoginPage() {
        return BaseEntity.failed(103, "还未登录");
    }

    //给用户更新用户资料的api

    @PostMapping("/user/updateinfo")
    public BaseEntity updateUserInfo(@RequestParam(value = "userName",required = false)String userName,
                                     @RequestParam(value = "email",required = false)String email){
        Map<String,Object> map = new HashMap<>();
        map.put("userName",userName);
        map.put("email",email);
        int num = 0;
        try {
            num = userInfoService.updateUserInfo(map);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新失败");
            return BaseEntity.failed(103,"服务端更新失败");
        }
        if(num==0){
            return BaseEntity.failed(103,"服务端更新失败");
        }
        return BaseEntity.success();

    }

    //更新或上传用户头像api

    @PostMapping("/userinfo/update/headshot")
    public BaseEntity updateHeadShot(MultipartFile multipartFile,
                                     @RequestParam("userId")int userId) throws IOException {
        int result = userInfoService.updateImg(userId,multipartFile);
        if (result==1){
            return BaseEntity.success();
        }else {
            return BaseEntity.failed(104,"服务端更新失败");
        }
    }
    @GetMapping("/userinfo/headshot")
    public BaseEntity<String> getUserImg(@RequestParam("userId")int userId) throws IOException {
        return BaseEntity.success(userInfoService.getHeadShotBase64(userId));
    }

}
