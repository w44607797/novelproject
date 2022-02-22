package com.guo.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.guo.bean.BaseEntity;
import com.guo.bean.Novel;
import com.guo.service.mapper.NovelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author guokaifeng
 * @createDate: 2022/2/17
 **/

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    NovelService novelService;

    @RequestMapping("/userdetail/{id}")
    public String getUserDetail(@PathVariable("id") int id) {
        return id + "";
    }

//    @DeleteMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id") int id){
//
//    }

    @GetMapping("/audit")
    public BaseEntity<List<Novel>> auditNovel() {
        List<Novel> pandingNovel = novelService.getPandingNovel();
        if (pandingNovel == null) {
            return BaseEntity.failed(301, "没有小说待审");
        }
        return BaseEntity.success(pandingNovel);
    }

    @DeleteMapping("/novel/delete/{id}")
    public BaseEntity auditDelete(@PathVariable("id") int id) {
        int result = novelService.deletePandingNovel(id);
        if (result == -1) {
            return BaseEntity.failed(500, "数据库删除待审小说错误");
        } else if (result == 0) {
            return BaseEntity.failed(510, "未找到改id所属审核小说");
        } else {
            return BaseEntity.success("删除成功");
        }
    }

    @PutMapping("/novel/pass/{id}")
    public BaseEntity auditPass(@PathVariable("id") int id) {
        int i = novelService.passPandingNovel(id);
        if (i == 0) {
            log.error("小说id不存在");
            return BaseEntity.failed(511, "小说id不存在");
        } else if (i == -1) {
            return BaseEntity.failed(501,"数据库更新待审小说错误");
        }else {
            return BaseEntity.success("成功上架");
        }
    }
}
