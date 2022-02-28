package com.guo.config;

import com.guo.service.mapper.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author guokaifeng
 * @createDate: 2022/2/14
 **/

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    AccountService accountService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String permission;
        //目前就user以及admin所以就不讨论多个权限的情况了
        try {
            permission = accountService.getpermission(principalCollection.getPrimaryPrincipal());
        }catch (NumberFormatException numberFormatException){
            log.error("授权时传入账号格式错误:primaryPrincipal:"+(String) principalCollection.getPrimaryPrincipal());
            return null;
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询账号权限数据库操作错误");
            return null;
        }
        simpleAuthorizationInfo.addRole(permission);
        //simpleAuthorizationInfo.addStringPermission("user:delete:*");
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        int user_id = 0;
        String password = "";
        String salt;
        try {
            user_id = Integer.parseInt(usernamePasswordToken.getUsername());
            password = accountService.getPasswordById(user_id);
            salt = accountService.getSalt(user_id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.info("找不到id或id格式有误");
            return null;
        }
        if(salt==null){
            return null;
        }

        return new SimpleAuthenticationInfo(principal,password, ByteSource.Util.bytes(salt),this.getName());
    }
}
