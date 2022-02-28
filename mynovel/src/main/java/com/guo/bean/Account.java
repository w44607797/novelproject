package com.guo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @author guokaifeng
 * @createDate: 2022/2/10
 **/

@TableName("account")
@Data
@NoArgsConstructor
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableField("user_id")
    private int userId;
    @TableField("user_Name")
    private String userName;
    @TableField("user_password")
    private String userPassword;
    private String salt;
    private String permission;

}
