package com.guo.utils;

import com.guo.bean.Novel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * @author guokaifeng
 * @createDate: 2022/2/23
 **/


@Slf4j
public class FileUtil {
    public static String getFileExtension(MultipartFile File){
        String originalFileName = File.getOriginalFilename();
        String result = null;
        try {
            result = originalFileName.substring(originalFileName.lastIndexOf("."));
        }catch (Exception e){
            return null;
        }
        return result;
    }


    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString()
                .replace("-","");
    }

    public static String fileToBase64(File file) throws IOException {
        if(!file.exists()){
            log.error("Base64传入的文件不存在");
            return null;
        }
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes;
        try {
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件转二进制流出错");
            return null;
        }finally {
            inputStream.close();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }



}
