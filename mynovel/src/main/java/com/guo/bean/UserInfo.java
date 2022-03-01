package com.guo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author guokaifeng
 * @createDate: 2022/2/26
 **/


//userinfo相关的内容还没有完善

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfo implements Serializable {
    @TableField("user_id")
    private int userId;
    @TableField("user_name")
    private String userName;
    private String headshot;
    @TableField("user_level")
    private int userLevel;
    private ImgFile img;
    private String email;
}
