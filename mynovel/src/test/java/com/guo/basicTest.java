package com.guo;

import com.guo.utils.FileUtil;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    public void testUUID(){
        UUID uuid = UUID.randomUUID();
        String tempUU = uuid.toString()
                .replace("-","");
        System.out.println(tempUU);
    }
    @Test
    public void testIO() throws IOException {
        File file = new File("demoio.txt");
        if(file.createNewFile()){
            System.out.println("创");
        }

    }
    @Test
    public void deleteFileTest(){
        File file = new File("E://test");
        file.delete();
    }
    @Test
    public void getFileSize() throws IOException {
        File file = new File("demodemo.txt");
     //   file.createNewFile();
        System.out.println(file.length());

        String a = "E:/myservice/content/fd07d948957747e1952ce1b3a9b3e9a4.txt";
        System.out.println(a.length());
    }

    @Test
    public void testBase64() throws IOException {
        File file = new File("E:\\myservice\\cover\\ZJ9M%~91(CS]]7NN%_V4SZ5.png");
        System.out.println(FileUtil.fileToBase64(file));
    }


}
