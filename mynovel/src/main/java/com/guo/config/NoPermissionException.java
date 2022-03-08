package com.guo.config;
import com.guo.bean.BaseEntity;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author guokaifeng
 * @createDate: 2022/3/8
 **/
@ControllerAdvice
public class NoPermissionException {
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public BaseEntity handleShiroException(Exception ex) {
        return BaseEntity.failed(000,"无权限或还没有登录");
    }
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public BaseEntity AuthorizationException(Exception ex) {
        return BaseEntity.failed(001,"无权限或还没有登录");
    }
}

