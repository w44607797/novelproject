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

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/author")
@RequiresRoles(value = {"user","admin"},logical = Logical.OR)
@Slf4j
public class AuthorController {

    @Autowired
    FileService fileService;

    @Autowired
    NovelService novelService;

    //提交小说
    @PostMapping("/admit")
    public BaseEntity admitNovel(@RequestParam("novelName") String novelName,
                             @RequestParam("text")String text,
                             @RequestParam("authorName")String authorName,
                             @RequestParam("authorId")int authorId,
                             @RequestParam("title")String title,
                             @RequestParam("type")String type){
        Novel novel = new Novel();
        novel.setNovelName(novelName);
        novel.setAuthorId(authorId);
        novel.setTextPath(text);
        novel.setAuthorName(authorName);
        novel.setTitle(title);
        novel.setType(type);
        novel.setStatus((byte)0);
        try {
            novelService.insertNovel(novel);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("小说上传失败");
            return BaseEntity.failed(500,"小说上传失败，请联系管理员");
        }
        return BaseEntity.success();
    }

    @PostMapping("/uploadcontent")
    public BaseEntity uploadNovelContent(@RequestParam("novelId")int novelId,
                                         @RequestParam("content")String content) throws IOException {

        try {
            fileService.storageContent(content);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("更新小说内容失败");
            return BaseEntity.failed(510,"小说内容上传失败，请联系管理员");
        }
        return BaseEntity.success();

    }


    //上传小说封面的API

    @PostMapping("/coverfile/upload/{novelId}")
    public BaseEntity uploadImgFile(MultipartFile file,
                                    @PathVariable("novelId") int novelId) throws IOException {

        String path = "/cover";

        String extendsion = (Objects.requireNonNull(FileUtil.
                getFileExtension(file))).
                toLowerCase(Locale.ROOT);
        if(!(extendsion.equals(".png") ||
                extendsion.equals(".jpg") ||
                extendsion.equals(".bmp")||
                extendsion.equals(".jpeg"))){
            return BaseEntity.failed(501,"上传的文件类型不符合");
        }

        String s = fileService.uploadFile(file, path, extendsion,novelId);
        if (s != null) {
            BaseEntity.failed(502,s);
        }
        return BaseEntity.success("上传成功");

    }

    //上传小说内容文件API

    @PostMapping("/textfile/upload/{novelid}")
    public BaseEntity uploadTextFile(MultipartFile file,
                                     @PathVariable("novelid")int novelId) throws IOException {

        String path = "/content";

        String extendsion = (Objects.requireNonNull(FileUtil.
                getFileExtension(file))).
                toLowerCase(Locale.ROOT);
        if (!extendsion.equals(".txt")) {
            return BaseEntity.failed(501,"上传的文件类型不符合");
        }
        String s = fileService.uploadFile(file, path, extendsion, novelId);
        if(s==null){
            return BaseEntity.success();
        }
        return BaseEntity.failed(503,s);
    }

}
