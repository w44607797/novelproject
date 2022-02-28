package com.guo;

import com.guo.bean.Account;
import com.guo.bean.Novel;
import com.guo.bean.mapper.NovelMapper;
import com.guo.service.mapper.AccountService;
import com.guo.service.mapper.FileService;
import com.guo.service.mapper.NovelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/2/10
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class totalTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    private NovelService novelService;

    @Autowired
    private FileService fileService;
    @Test
    public void testMybatis(){

        String password = accountService.getPasswordById(1);
        System.out.println(password);
    }

    @Test
    public void getAll(){
        System.out.println(novelMapper.getRecomand(0));
    }

    @Test
    public void demogetUserDetail(){

        Account userDetailById = accountService.getUserDetailById("user_name", "userId", 1);
        System.out.println(userDetailById);
    }

    @Test
    public void getNovelDetail(){
        Novel detailByParam = novelService.getDetailByParam("text_path", "novelId", 1);
        System.out.println(detailByParam);
    }
    @Test
    public void getFileContentTest() throws IOException {
        String fileContent = fileService.getFileContent(1);
        System.out.println(fileContent);

    }
}
