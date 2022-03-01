package com.guo.service.mapper;

import com.guo.bean.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface UserInfoService {
    int updateUserInfo(Map map);
    int insertUserInfo(int userId,String userName,String email);
    String infoAddImgParam(int userId) throws IOException;
    int updateImg(int userId, MultipartFile multipartFile) throws IOException;
    String getHeadShotBase64(int userId) throws IOException;
    UserInfo getUserInfo(Map map);
}
