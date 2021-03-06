package com.guo.service.impl;

import com.guo.bean.Novel;
import com.guo.bean.mapper.NovelMapper;
import com.guo.bean.mapper.UserInfoMapper;
import com.guo.service.mapper.FileService;
import com.guo.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/22
 **/

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    NovelMapper novelMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    private static final String FILE_PATH = "E:/myservice";


    //上传小说封面或者小说内容共用方法

    @Override
    public String uploadFile(MultipartFile file, String path, String extendsion, int novelId) throws IOException {
        if (file.isEmpty()) {
            log.error("上传的文件为空");
            return "文件为空";
        }
        if (extendsion == null) {
            log.error("获取文件后缀名出错");
            return "文件格式或名称有误";
        }
        File profile = new File(FILE_PATH + path);
        if (!profile.exists()) {
            log.info("没有存储文件夹，尝试新建");
            if (profile.mkdirs()) {
                log.info("存储文件夹新建成功");
            } else {
                log.error("存储文件夹新建失败");
                return "服务器存储失败";
            }
        }

        //生成存放位置

        String totalPath;
        try {
            StringBuilder stringBuilder = new StringBuilder(60);
            String uuid = FileUtil.getUUID();
            stringBuilder.append(FILE_PATH);
            stringBuilder.append(path);
            stringBuilder.append("/");
            stringBuilder.append(uuid);
            stringBuilder.append(extendsion);
            totalPath = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件上传合成路径出错:" + "path:" + path + "extendsion:" + extendsion);
            return "业务出错,请联系管理员";
        }
        try {
            InputStream inputStream = file.getInputStream();
            File tofile = new File(totalPath);
            OutputStream outputStream = new FileOutputStream(tofile);
            int readlen;
            byte[] bytes = new byte[4096];
            while ((readlen = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, readlen);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件上传IO流出错");
            return "业务出错,请联系管理员";
        }

        //如果存放的是头像就在此终止

        if(path.equals("/headshot")){
            Map<String,Object> map = new HashMap<>();
            map.put("headshot",totalPath);
            try {
                userInfoMapper.updateUserInfo(map);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("数据库添加头像路径出错");
                //不存放头像
                File headfile = new File(totalPath);
                headfile.delete();
                return "服务端出错，请联系管理员";
            }
            return null;
        }




        Map<String, Object> uploadMap = new HashMap<>();
        uploadMap.put("novelId", novelId);

        String uploadType = path.replace("/", "");

        if (uploadType.equals("content")) {
            uploadMap.put("textPath", totalPath);
        } else {
            uploadMap.put("imgPath", totalPath);
        }
        try {
            novelMapper.reviseNovel(uploadMap);
        } catch (Exception e) {

            File deleteFile = new File(totalPath);
            if (deleteFile.delete()) {
                log.info("文件删除成功");
            } else {
                log.error("文件删除失败");
            }
            e.printStackTrace();
            log.error("更新文件地址到服务器数据库失败");
            return "文件上传失败";
        }
        return null;
    }

    //这个方法用来从txt文件得到小说内容

    @Override
    public String getFileContent(int novelId) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("novelId", novelId);
        map.put("condition", "text_path");

        Novel detailByParam;
        try {
            detailByParam = novelMapper.getDetailByParamWithId(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库查询小说内容地址出错");
            return "获取小说内容出错，请联系管理员";
        }
        String path = detailByParam.getTextPath();
        if (path == null) {
            return "该小说还没有内容";
        }
        File file = new File(path);
        long size = file.length();
        if (size == 0) {
            return "改小说还没有内容";
        }


        //将得到文件路径下的文件内容读取拼接返回
        //这里本来想到的方法是StringBuilder+bufferedReader一行一行读
        //百度了一下优化了

        FileInputStream fileInputStream = new FileInputStream(path);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            fileInputStream.close();
        } catch (Exception e) {
            fileInputStream.close();
            log.error("文件内容提取失败");
            return "获取小说内容失败";
        }
        return outputStream.toString(String.valueOf(StandardCharsets.UTF_8));
    }

    @Override
    public String storageContent(String content) throws IOException {
        String resultPath;
        //拼接小说内容存储地址
        try{
            StringBuilder stringBuilder = new StringBuilder(60);
            String uuid = FileUtil.getUUID();
            stringBuilder.append(FILE_PATH);
            stringBuilder.append("/content");
            stringBuilder.append("/");
            stringBuilder.append(uuid);
            stringBuilder.append(".txt");
            resultPath = stringBuilder.toString();
        }catch (Exception e){
            log.error("生成存储地址出错(StringBuilder)");
            return "小说内容上传出错,请联系管理员";
        }
        //追加写入小说内容
        FileOutputStream outputStream = new FileOutputStream(resultPath,true);
        try{
            byte[] bytes = content.getBytes();
            outputStream.write(bytes);
        }catch (Exception e){
            log.error("小说内容写入错误");
            return "小说内容上传出错,请联系管理员";
        }finally {
            outputStream.close();
        }
        return null;
    }


    //获得图片文件base64码

    @Override
    public String getBase64ImgFile(int novelId) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("condition","img_path");
        map.put("novelId",novelId);
        Novel novel = null;
        try {
            novel = novelMapper.getDetailByParamWithId(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询小说图片地址出错");
            return null;
        }
        String path = novel.getImgPath();
        File file = new File(path);
        String base64Encode = FileUtil.fileToBase64(file);
        return base64Encode;

    }

    //获得小说内容存储地址

    @Override
    public String getTextFilePath(int novelId) {
        Map<String,Object> map = new HashMap<>();
        map.put("condition","text_path");
        map.put("novelId",novelId);
        String path;
        try {
            Novel novel = novelMapper.getDetailByParamWithId(map);
            path = novel.getTextPath();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询小说内容出错");
            return null;
        }
        if(path==null){
            log.error("未查询到小说内容");
            return null;
        }
        return path;

    }

    //获得小说图片存储地址

    @Override
    public String getImgFilePath(int novelId) {
        Map<String,Object> map = new HashMap<>();
        map.put("condition","img_path");
        map.put("novelId",novelId);
        novelMapper.getDetailByParamWithId(map);
        String path;
        try {
            Novel novel = novelMapper.getDetailByParamWithId(map);
            path = novel.getImgPath();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询小说内容出错");
            return null;
        }
        if(path==null){
            log.error("未查询到小说图片");
            return "作者还未上传小说图片";
        }
        return path;

    }


}
