package com.guo.controller;

import com.guo.bean.Account;
import com.guo.bean.BaseEntity;
import com.guo.bean.Novel;
import com.guo.bean.UserInfo;
import com.guo.bean.mapper.UserInfoMapper;
import com.guo.service.mapper.AccountService;
import com.guo.service.mapper.NovelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
public class ShareController {

    @Autowired
    AccountService accountService;

    @Autowired
    NovelService novelService;

    @Autowired
    UserInfoMapper userInfoMapper;


    //模糊搜索小说，匹配关键字
    @GetMapping("/searchnovel/{authorname}/{novelname}")
    public BaseEntity<List<Novel>> getNovel(@RequestParam(value = "authorname",required = false) String authorName,
                                @RequestParam(value = "novelname",required = false) String novelName) {
        HashMap<String, String> map = new HashMap();
        map.put("authorName", authorName);
        map.put("novelName", novelName);
        List<Novel> novelList = novelService.searchNovel(map);
        return BaseEntity.success(novelList);
    }

    //分页展示小说推荐页面

    @GetMapping("/recomand/{begin}")
    public BaseEntity<List<Novel>> getRecomandList(@PathVariable("begin")int begin) throws IOException {
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
        return BaseEntity.success(novelList);
    }

    //userinfo相关的内容还没有完善

    @GetMapping("/userinfo/{userid}")
        public BaseEntity<UserInfo> getUserInfo(@PathVariable("userid")String userId){
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        UserInfo userInfo = null;
        try {
            userInfo = userInfoMapper.getUserInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库查询用户个人资料失败");
            return BaseEntity.failed(550,"服务端出错，请联系管理员");
        }
        return BaseEntity.success(userInfo);


    }

}
