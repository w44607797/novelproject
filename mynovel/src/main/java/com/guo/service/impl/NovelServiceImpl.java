package com.guo.service.impl;

import com.guo.bean.Novel;
import com.guo.bean.mapper.NovelMapper;
import com.guo.service.mapper.NovelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/15
 **/

@Service
@Slf4j
public class NovelServiceImpl implements NovelService {

    @Autowired
    NovelMapper novelMapper;

    @Override
    public List<Novel> searchNovel(Map map) {
        return novelMapper.getNovelList(map);
    }

    @Override
    public List<Novel> getPandingNovel() {
        try {
            return novelMapper.getPandingNovel();
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询审核小说列表失败");
            return null;
        }
    }

    @Override
    public int deletePandingNovel(int id) {
        int number;
        try {
            number = novelMapper.deletePandingNovel(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库删除失败");
            return -1;
        }
        return number;
    }

    @Override
    public int passPandingNovel(int id) {
        Novel novel = new Novel();
        novel.setNovelId(id);
        novel.setStatus((byte) 1);
        int result = 0;
        try {
            result = novelMapper.passPandingNovel(novel);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库层面更新失败");
        }
        return result;
    }


}
