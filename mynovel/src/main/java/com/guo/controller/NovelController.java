package com.guo.controller;

import com.guo.bean.BaseEntity;
import com.guo.service.mapper.FileService;
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
import java.util.Locale;
import java.util.Objects;

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



    //当用户浏览小说内容时返回小说内容
    @GetMapping("/browse/{novelid}")
    public BaseEntity getNovelContent(@PathVariable("novelid")int id) throws IOException {
        String content = fileService.getFileContent(id);
        return BaseEntity.success(content);
    }







}
