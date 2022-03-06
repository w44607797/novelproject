package com.guo.service.mapper;

import com.guo.bean.Novel;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
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
    int updateNovel(int novelId,String column,String content);
    List<Novel> getDetailByParam(Map map);
    void NovelAddParam(List<Novel> novelList) throws IOException;
    void NovelAddParam(Novel novel) throws IOException;
    List<Novel> getRecomandList(int begin);
    int insertNovel(Novel novel);
}
