package com.guo.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author guokaifeng
 * @createDate: 2022/2/27
 **/

public class SecurityUtil {
    public static String toMd5(String password,String salt){
        return new Md5Hash(password,salt).toString();
    }
}
