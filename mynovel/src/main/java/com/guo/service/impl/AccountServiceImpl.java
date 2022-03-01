package com.guo.service.impl;

import com.guo.bean.Account;
import com.guo.bean.Novel;
import com.guo.bean.mapper.AccountMapper;
import com.guo.bean.mapper.NovelMapper;
import com.guo.service.mapper.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/10
 **/

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;


    @Override
    public Account searchAccoundById(int id){
        Map<String,Object> map = new HashMap<>();
        map.put("condition","user_id,user_name");
        map.put("userId",id);
        try {
            return accountMapper.getUserDetailById(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("id不存在");
            return null;
        }
    }

    @Override
    public String getPasswordById(int id) {
        Map<String,Object> map = new HashMap<>();
        map.put("condition","user_password");
        map.put("userId",id);
        Account account = accountMapper.getUserDetailById(map);
        return account.getUserPassword();
    }

    @Override
    public boolean insertUser(String userName,int userId,String userPassword) {
        try {
            if (accountMapper.getAccountById(userId) != null) {
                log.error("id已存在");
                return false;
            } else {
                //加密注册
                String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
                String newpassword = new Md5Hash(userPassword,salt).toString();
                accountMapper.insertUser(userName,userId,newpassword,salt);
            }
        }catch (Exception e){
            log.error("注册数据库添加用户出错");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public String getSalt(int id) {
        String salt;
        Map<String,Object> map = new HashMap<>();
        map.put("condition","salt");
        map.put("userId",id);
        try {
            Account account = accountMapper.getUserDetailById(map);
            salt = account.getSalt();
        } catch (Exception e){
            log.info("盐查询失败");
            return null;
        }
        return salt;
    }

    @Override
    public Account getUserDetailById(String object,String[] condition,String[] value) {
        if(condition.length!=value.length){
            log.error("传入的参数有误");
            return null;
        }
        Map<String,String> map = new HashMap<>();
        map.put("condition",object);
        for(int i = 0;i<condition.length;i++){
            map.put(condition[i],value[i]);
        }
        Account resultAccount = null;
        try {
            resultAccount = accountMapper.getUserDetailById(map);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询用户参数在数据库层面出错");
        }
        if(resultAccount==null){
            log.error("查询不到对应id的用户");
        }
        return resultAccount;
    }


    @Override
    public Account getUserDetailById(String aim, String condition,Object value) {
        Map<String,Object> map = new HashMap<>();
        map.put("condition",aim);
        map.put(condition,value);
        Account resultAccount = null;
        try {
            resultAccount = accountMapper.getUserDetailById(map);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询用户参数在数据库层面出错");
        }
        if(resultAccount==null){
            log.error("查询不到对应的用户");
        }
        return resultAccount;
    }

    @Override
    public String getpermission(Object id) {
        Map<String,Object> map = new HashMap<>();
        map.put("condition","permission");
        map.put("userId",id);
        Account account = accountMapper.getUserDetailById(map);
        return account.getPermission();
    }

    @Override
    public String addUserInfo(int userId) {
        return null;
    }

}
