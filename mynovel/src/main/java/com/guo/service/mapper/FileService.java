package com.guo.service.mapper;

import com.guo.bean.Novel;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author guokaifeng
 * @createDate: 2022/2/22
 **/

public interface FileService {
    String uploadFile(MultipartFile file,String path,String extension,int novelId) throws IOException;
    String getFileContent(int novelId) throws IOException;
    String storageContent(String content) throws IOException;
    String getImgFilePath(int novelId) throws IOException;
    String getBase64ImgFile(int novelId) throws IOException;
    String getTextFilePath(int novelId);
}
