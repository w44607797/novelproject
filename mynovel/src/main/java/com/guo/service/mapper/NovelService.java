package com.guo.service.mapper;

import com.guo.bean.Novel;

import java.util.List;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/15
 **/


public interface NovelService {
    List<Novel> searchNovel(Map map);
    List<Novel> getPandingNovel();
    int deletePandingNovel(int id);
    int passPandingNovel(int id);

}
