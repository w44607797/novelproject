package com.guo.bean.mapper;

import com.guo.bean.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * @author guokaifeng
 * @createDate: 2022/2/10
 **/
@Mapper
@Repository
public interface AccountMapper {
    Account getAccountById(int id);
    void insertUser(@Param("name")String user_name,
                    @Param("id")int user_id,
                    @Param("password")String user_passworld,
                    @Param("salt")String salt);
    Account getUserDetailById(Map map);
    Account updateUser(Map map);
}
