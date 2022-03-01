package com.guo.controller;

import com.guo.bean.BaseEntity;
import com.guo.bean.Novel;
import com.guo.service.mapper.FileService;
import com.guo.service.mapper.NovelService;
import com.guo.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * @author guokaifeng
 * @createDate: 2022/2/23
 **/


@RestController
@RequestMapping("/novel")
@RequiresRoles(value = {"user","admin"},logical = Logical.OR)
@Slf4j
public class NovelController {

    @Autowired
    FileService fileService;

    @Autowired
    NovelService novelService;



    //当用户浏览小说内容时返回小说内容
    @GetMapping("/browse/{novelid}")
    public BaseEntity getNovelContent(@PathVariable("novelid")int id) throws IOException {
        String content = null;
        try {
            content = fileService.getFileContent(id);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("数据库获取小说内容失败");
            return BaseEntity.failed(540,"服务端出错，请联系管理员");
        }

        return BaseEntity.success(content);
    }

    //根据小说参数进行分类查询,比如查询小说类别
    @GetMapping("/reasearch")
    public BaseEntity<List<Novel>> getNovelByParam(@RequestParam(value = "novelname",required = false)String novelName,
                                      @RequestParam(value = "authorname",required = false)String authorName,
                                      @RequestParam(value = "type",required = false)String type) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("condition","novel_name,upload_time,title,author_name,type");
        map.put("novelName",novelName);
        map.put("authorName",authorName);
        map.put("type",type);
        List<Novel> novelList = novelService.getDetailByParam(map);
        novelService.NovelAddParam(novelList);
        return BaseEntity.success(novelList);


    }








}
