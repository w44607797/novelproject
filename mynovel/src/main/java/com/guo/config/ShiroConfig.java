package com.guo.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/14
 **/

@Configuration
public class ShiroConfig {

    @Bean(value = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean filterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        Map<String,String> map = new HashMap<>();
        //主页
        map.put("/share/**","anon");
        //管理员api
        map.put("/admin/**","authc");
        //关于小说的
        map.put("/novel/**","authc");
        //登出
        map.put("/logout","authc");
        //作者相关
        map.put("/author","authc");

        factoryBean.setLoginUrl("/nologin");
        factoryBean.setFilterChainDefinitionMap(map);
        factoryBean.setUnauthorizedUrl("/unauth");
        return factoryBean;
    }


    @Bean
    public DefaultWebSecurityManager manager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        return manager;
    }

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
