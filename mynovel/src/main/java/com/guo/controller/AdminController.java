package com.guo.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.guo.bean.BaseEntity;
import com.guo.bean.Novel;
import com.guo.service.mapper.NovelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
@RequiresRoles(value = "admin")
@CrossOrigin(originPatterns = "*",allowCredentials = "true")
@Api(tags = "管理员接口")
public class AdminController {

    @Autowired
    NovelService novelService;

    @GetMapping("/audit")
    @ApiOperation(value = "获得待审小说")
    public BaseEntity<List<Novel>> auditNovel() {
        List<Novel> pandingNovel = novelService.getPandingNovel();
        if (pandingNovel == null) {
            return BaseEntity.failed(301, "没有小说待审");
        }
        return BaseEntity.success(pandingNovel);
    }

    //管理员审核小说

    @DeleteMapping("/novel/delete/{id}")
    @ApiOperation(value = "不通过小说")
    public BaseEntity auditDelete(@PathVariable("id") int id) {
        int result = novelService.deletePandingNovel(id);
        if (result == -1) {
            return BaseEntity.failed(310, "数据库删除待审小说错误");
        } else if (result == 0) {
            return BaseEntity.failed(301, "未找到改id所属审核小说");
        } else {
            return BaseEntity.success("删除成功");
        }
    }

    //审核小说通过api

    @PutMapping("/novel/pass/{id}")
    @ApiOperation(value = "通过小说")
    public BaseEntity auditPass(@PathVariable("id") int id) {
        int i = novelService.passPandingNovel(id);
        if (i == 0) {
            log.error("小说id不存在");
            return BaseEntity.failed(302, "小说id不存在");
        } else if (i == -1) {
            return BaseEntity.failed(312,"数据库更新待审小说错误");
        }else {
            return BaseEntity.success("成功上架");
        }
    }
}
