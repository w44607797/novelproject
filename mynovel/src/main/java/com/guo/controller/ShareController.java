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

    //搜索用户得到用户信息
    //匹配多个用户



    @GetMapping("/searchnovel/{authorname}/{novelname}")
    public BaseEntity<List<Novel>> getNovel(@RequestParam("authorname") String authorName,
                                @RequestParam("novelname") String novelName) {
        HashMap<String, String> map = new HashMap();
        map.put("authorName", authorName);
        map.put("novelName", novelName);
        List<Novel> novelList = novelService.searchNovel(map);
        return BaseEntity.success(novelList);
    }
    @GetMapping("/recomand")
    public BaseEntity<List<Novel>> getRecomandList(){
        List<Novel> novelList;
        try {
            novelList = novelService.getPandingNovel();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询推荐小说业务失败");
            return BaseEntity.failed(520,"获取推荐小说列表失败");
        }
        if(novelList==null){
            log.error("查询推荐小说结果为空");
            return BaseEntity.failed(530,"获取推荐小说列表失败");
        }
        return BaseEntity.success(novelList);
    }

    @GetMapping("/userinfo/{userid}")
        public BaseEntity<UserInfo> getUserInfo(@PathVariable("userid")String userId){
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        UserInfo userInfo = userInfoMapper.getUserInfo(map);
        return BaseEntity.success(userInfo);


    }




}
