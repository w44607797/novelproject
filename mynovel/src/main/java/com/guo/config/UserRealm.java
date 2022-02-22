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
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("superadmin");
        simpleAuthorizationInfo.addStringPermission("user:delete:*");
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
