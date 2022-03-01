package com.guo.bean.mapper;

import com.guo.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/26
 **/


@Mapper
@Repository
public interface UserInfoMapper {
    int updateUserInfo(Map map);
    UserInfo getUserInfo(Map map);
    int insertUserInfo(@Param("userId")int userId,
                       @Param("userName")String userName,
                       @Param("email")String email);
    String getUserImgPath(int userId);
}
