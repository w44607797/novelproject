package com.guo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author guokaifeng
 * @createDate: 2022/2/15
 **/

@TableName("novel")
@Data
public class Novel {
    @TableField("novel_name")
    private String novelName;
    @TableField("novel_id")
    private int novelId;
    @TableField("upload_time")
    private String uploadTime;
    private String text;
    @TableField("author_name")
    private String authorName;
    @TableField("author_id")
    private int authorId;
    private String title;
    @TableField("img_path")
    private String imgPath;
    private byte status;
    private String type;
    private Account author;

}
