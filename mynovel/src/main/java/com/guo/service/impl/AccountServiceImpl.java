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
        try {
            return accountMapper.getAccountById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("id不存在");
            return null;
        }
    }

    @Override
    public String getPasswordById(int id) {
        return accountMapper.getPasswordById(id);
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
                accountMapper.insertUser(userName,userId,newpassword);
            }
        }catch (Exception e){
            log.error("注册插入用户出错");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public String getSalt(int id) {
        String salt;
        try {
            salt = accountMapper.getSalt(id);
        } catch (Exception e){
            log.info("盐查询失败");
            return null;
        }
        return salt;
    }


}
