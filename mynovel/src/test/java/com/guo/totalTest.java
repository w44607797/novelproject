package com.guo;

import com.guo.bean.Account;
import com.guo.bean.mapper.NovelMapper;
import com.guo.controller.testController;
import com.guo.service.mapper.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author guokaifeng
 * @createDate: 2022/2/10
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class totalTest {

    @Autowired
    private testController testController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private NovelMapper novelMapper;
    @Test
    public void testMybatis(){
        Account account = testController.getAccount(1);
        System.out.println(account);
        String password = accountService.getPasswordById(1);
        System.out.println(password);
    }

    @Test
    public void getAll(){
        System.out.println(novelMapper.getAllDetail(1));
    }

}
