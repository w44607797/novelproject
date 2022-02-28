package com.guo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * @author guokaifeng
 * @createDate: 2022/2/15
 **/

@TableName("novel")
@Data
@NoArgsConstructor
public class Novel implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField("novel_name")
    private String novelName;
    @TableField("novel_id")
    private int novelId;
    @TableField("upload_time")
    private String uploadTime;
    @TableField("text_path")
    private String textPath;
    @TableField("author_name")
    private String authorName;
    @TableField("author_id")
    private int authorId;
    private String title;
    @TableField("img_path")
    private String imgPath;
    private byte status;
    private String type;
    private ImgFile imgfile;
    private String content;

}
