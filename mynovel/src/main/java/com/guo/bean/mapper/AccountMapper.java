package com.guo.bean.mapper;

import com.guo.bean.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author guokaifeng
 * @createDate: 2022/2/10
 **/
@Mapper
@Repository
public interface AccountMapper {
    public Account getAccountById(int id);
    String getPasswordById(int id);
    void insertUser(String user_name,int user_id,String user_passworld);
    String getSalt(int id);
}
