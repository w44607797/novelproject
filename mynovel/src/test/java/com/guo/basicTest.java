package com.guo;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

import java.util.List;

/**
 * @author guokaifeng
 * @createDate: 2022/2/16
 **/

public class basicTest {
    @Test
    public void testMd5(){
        Md5Hash md5Hash = new Md5Hash("123","wwww");
        System.out.println(md5Hash);
        System.out.println(md5Hash.toHex());
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        System.out.println(salt);
    }
    @Test
    public void testbasic(){

    }
}
