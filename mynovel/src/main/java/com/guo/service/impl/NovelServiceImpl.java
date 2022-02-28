package com.guo.service.impl;

import com.guo.bean.Novel;
import com.guo.bean.mapper.NovelMapper;
import com.guo.service.mapper.NovelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/15
 **/

@Service
@Slf4j
@Transactional
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
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
        return number;
    }

    @Override
    public int passPandingNovel(int id) {
        Map<String,Integer> map = new HashMap<>();
        map.put("status",1);
        map.put("novelId",id);
        int result = 0;
        try {
            result = novelMapper.reviseNovel(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库层面更新失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public int updateNovel(int novelId,String column,String content) {
        if(column.equals("novelId") || column.equals("status") || column.equals("imgPath")){
            log.error("非法修改特殊参数");
            return 0;
        }
        int result;
        try {
            Map<String,Object> map = new HashMap<>();
            map.put(column,content);
            map.put("novelId",novelId);
            result = novelMapper.reviseNovel(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库层面修改小说参数出错");
            result = -1;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Novel getDetailByParam(String aim,String condition,Object value) {
        Map<String,Object> map = new HashMap<>();
        map.put("condition",aim);
        map.put(condition,value);
        Novel novel = null;
        try{
            novel = novelMapper.getDetailByParam(map);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询不到对应的小说");
        }
        return novel;
    }


    @Override
    public void NovelAddParam(List<Novel> novelList) {

    }

    @Override
    public void NovelAddParam(Novel novel) {

    }


}
