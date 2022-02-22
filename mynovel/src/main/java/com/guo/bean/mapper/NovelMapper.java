package com.guo.bean.mapper;

import com.guo.bean.Novel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/15
 **/

@Mapper
@Repository
public interface NovelMapper {
    List<Novel> getNovelList(Map<String,String> map);
    List<Novel> getPandingNovel();
    int deletePandingNovel(int id);
    int passPandingNovel(Novel novel);
    Novel getAllDetail(int novelId);
    
}
