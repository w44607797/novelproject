package com.guo.service.impl;

import com.guo.bean.UserInfo;
import com.guo.bean.mapper.UserInfoMapper;
import com.guo.service.mapper.FileService;
import com.guo.service.mapper.UserInfoService;
import com.guo.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Service
@Slf4j

public class UserInfoImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    FileService fileService;


    @Override
    public int updateUserInfo(Map map) {
        return userInfoMapper.updateUserInfo(map);
    }

    @Override
    public int insertUserInfo(int userId, String userName, String email) {
        return userInfoMapper.insertUserInfo(userId, userName, email);
    }

    @Override
    public String infoAddImgParam(int userId) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        UserInfo userInfo;
        try {
            userInfo = userInfoMapper.getUserInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String path = userInfo.getHeadshot();
        File file = new File(path);
        if (path == null || (!file.exists())) {
            return null;
        }

        String base64 = FileUtil.fileToBase64(file);
        return base64;

    }

    @Override
    public int updateImg(int userId, MultipartFile multipartFile) throws IOException {
        String path = "/headshot";
        String extension = FileUtil.getFileExtension(multipartFile).toLowerCase(Locale.ROOT);
        if (!(extension.equals(".jpg") ||
                extension.equals("png"))) {
            return 0;
        }
        String message = fileService.uploadFile(multipartFile, path, extension, userId);
        if (message == null) {
            return 1;
        }
        return 0;


    }

    @Override
    public String getHeadShotBase64(int userId) throws IOException {
        String userImgPath = userInfoMapper.getUserImgPath(userId);
        File file = new File(userImgPath);
        String base64 = FileUtil.fileToBase64(file);
        return base64;
    }

    @Override
    public UserInfo getUserInfo(Map map) {
        return userInfoMapper.getUserInfo(map);
    }
}
