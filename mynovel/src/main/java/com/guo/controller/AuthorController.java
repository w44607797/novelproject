package com.guo.controller;


import com.guo.bean.Account;
import com.guo.bean.Novel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController {


    //提交小说
    @PostMapping("/admit")
    public String admitNovel(@RequestParam("novelName") String novelName,
                             @RequestParam("text")String text,
                             @RequestParam("authorName")String authorName,
                             @RequestParam("authorId")int authorId,
                             @RequestParam("title")String title,
                             @RequestParam("type")String type){
        Novel novel = new Novel();
        novel.setNovelName(novelName);
        novel.setAuthorId(authorId);
        novel.setText(text);
        novel.setAuthorName(authorName);
        novel.setTitle(title);
        novel.setType(type);
        return null;

    }
}
