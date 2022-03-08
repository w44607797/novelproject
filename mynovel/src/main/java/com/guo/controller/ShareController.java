package com.guo.controller;

import com.guo.bean.*;
import com.guo.bean.mapper.UserInfoMapper;
import com.guo.service.mapper.AccountService;
import com.guo.service.mapper.NovelService;
import com.guo.service.mapper.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/17
 **/

    //这个类是用来给没有认证授权的人可以访问的，除此其他类都需要认证或者授权

@RequestMapping("/share")
@RestController
@Slf4j
@CrossOrigin(originPatterns = "*",allowCredentials = "true")
@Api(tags = "主页接口")
public class ShareController {

    @Autowired
    AccountService accountService;

    @Autowired
    NovelService novelService;

    @Autowired
    UserInfoService userInfoService;



    //模糊搜索小说，匹配关键字
    @GetMapping("/searchnovel")
    @ApiOperation(value = "模糊搜索小说")
    public BaseEntity<List<Novel>> getNovel(@RequestParam(value = "authorname",required = false) String authorName,
                                @RequestParam(value = "novelname",required = false) String novelName) throws IOException {
        HashMap<String, String> map = new HashMap();
        map.put("authorName", authorName);
        map.put("novelName", novelName);
        List<Novel> novelList = novelService.searchNovel(map);
        //添加图片参数
        novelService.NovelAddParam(novelList);
        return BaseEntity.success(novelList);
    }

    //分页展示小说推荐页面

    @GetMapping("/recomand/{begin}")
    @ApiOperation(value = "分页展示小说推荐页面")
    public BaseEntity<Object[]> getRecomandList(@PathVariable(value = "begin",required = false)int begin) throws IOException {
        List<Novel> novelList;
        try {
            novelList = novelService.getRecomandList(begin);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询推荐小说业务失败");
            return BaseEntity.failed(520,"获取推荐小说列表失败");
        }
        if(novelList==null){
            log.error("查询推荐小说结果为空");
            return BaseEntity.failed(530,"获取推荐小说列表失败");
        }
            novelService.NovelAddParam(novelList);
        return BaseEntity.success(novelList.toArray());
    }

    //搜索用户信息的api
    @ApiOperation(value = "搜索用户信息")
    @GetMapping(value = "/userinfo")
        public BaseEntity<UserInfo> getUserInfo(@RequestParam("userName")String userName) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("userName",userName);
        UserInfo userInfo = null;
        try {
            userInfo = userInfoService.getUserInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库查询用户个人资料失败");
            return BaseEntity.failed(550,"服务端出错，请联系管理员");
        }
        if(userInfo==null){
            return BaseEntity.message("没有对应作者");
        }

        //返回的信息携带头像图片参数

        ImgFile imgFile = new ImgFile();
        try {
            String headShotBase64 = userInfoService.getHeadShotBase64(userInfo.getUserId());
            imgFile.setBase64Encode(headShotBase64);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("数据库获取用户头像错误");
        }
        return BaseEntity.success(userInfo);
    }

}
