package com.guo.service.impl;

import com.guo.bean.ImgFile;
import com.guo.bean.Novel;
import com.guo.bean.mapper.NovelMapper;
import com.guo.service.mapper.NovelService;
import com.guo.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.io.IOException;
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
    public List<Novel> getDetailByParam(Map map) {
        List<Novel> novelList = null;
        try{
            novelList = novelMapper.getDetailByParam(map);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询不到对应的小说");
        }
        return novelList;
    }


    @Override
    public void NovelAddParam(List<Novel> novelList) throws IOException {

        for(Novel novel:novelList){
            NovelAddParam(novel);
        }
    }

    @Override
    public void NovelAddParam(Novel novel) throws IOException {

        //给小说加上图片参数

        int novelId = novel.getNovelId();
        Map<String,Object> map = new HashMap<>();
        map.put("condition","img_path");
        map.put("novelId",novelId);
        Novel tempNovel = new Novel();
        try {
            tempNovel = novelMapper.getDetailByParamWithId(map);
            String imgPath = tempNovel.getImgPath();
            File file = new File(imgPath);
            String base64 = FileUtil.fileToBase64(file);
            ImgFile imgFile = new ImgFile();
            imgFile.setName("img");
            imgFile.setBase64Encode(base64);
            novel.setImgfile(imgFile);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    @Override
    public List<Novel> getRecomandList(int begin) {
        return novelMapper.getRecomand(begin);
    }

    @Override
    public int insertNovel(Novel novel) {
        return novelMapper.insertNovel(novel);
    }


}
