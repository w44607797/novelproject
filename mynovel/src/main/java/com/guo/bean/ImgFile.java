package com.guo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author guokaifeng
 * @createDate: 2022/2/28
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgFile implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String base64Encode;
}
