package com.guo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @author guokaifeng
 * @createDate: 2022/2/10
 **/

@TableName("account")
@Data
public class Account {
    @TableField("user_id")
    private int userId;
    @TableField("user_Name")
    private String userName;
    @TableField("user_password")
    private String userPassword;

}
