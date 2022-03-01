package com.guo.service.mapper;

import com.guo.bean.Account;
import com.guo.bean.Novel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/10
 **/


public interface AccountService {
    Account searchAccoundById(int id);
    String getPasswordById(int id);
    boolean insertUser(String userName,int userId,String userPassword);
    String getSalt(int id);
    Account getUserDetailById(String object,String[] condition,String[] value);
    Account getUserDetailById(String aim,String condition,Object value);
    String getpermission(Object id);
    String addUserInfo(int userId);
}
