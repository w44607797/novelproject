package com.guo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author guokaifeng
 * @createDate: 2022/2/26
 **/

@TableName("user_info")
public class UserInfo implements Serializable {
    @TableField("user_id")
    private int userId;
    @TableField("user_name")
    private String userName;
    private String headshot;
    @TableField("user_level")
    private int userLevel;
    private String email;
}
