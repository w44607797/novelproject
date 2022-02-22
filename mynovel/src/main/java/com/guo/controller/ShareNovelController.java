package com.guo.controller;


import com.guo.bean.Novel;
import com.guo.service.mapper.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/share")
public class ShareNovelController {

    @Autowired
    NovelService novelService;


    @GetMapping("/searchNovel")
    public List<Novel> getNovel(@RequestParam("authorName") String authorName,
                                @RequestParam("novelName") String novelName) {
        HashMap<String, String> map = new HashMap();
        map.put("authorName", authorName);
        map.put("novelName", novelName);
        List<Novel> novelList = novelService.searchNovel(map);
        return novelList;
    }

}
