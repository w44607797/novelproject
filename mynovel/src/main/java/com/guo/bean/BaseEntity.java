package com.guo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author guokaifeng
 * @createDate: 2022/2/19
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity<T> implements Serializable {
    private T data;
    private boolean success;
    private int code;
    private String message;

    public static <T> BaseEntity<T> success(T t){
        BaseEntity<T> baseEntity = new BaseEntity<>();
        baseEntity.setSuccess(true);
        baseEntity.setCode(200);
        baseEntity.setData(t);
        return baseEntity;
    }
    public static <T> BaseEntity<T> success(){
        BaseEntity<T> baseEntity = new BaseEntity<>();
        baseEntity.setSuccess(true);
        baseEntity.setCode(200);
        return baseEntity;
    }
    public static <T> BaseEntity<T> message(String message){
        BaseEntity<T> baseEntity = new BaseEntity<>();
        baseEntity.setSuccess(true);
        baseEntity.setCode(200);
        baseEntity.setMessage(message);
        return baseEntity;
    }



    public static <T> BaseEntity<T> failed(int code,String error){
        BaseEntity<T> baseEntity = new BaseEntity<>();
        baseEntity.setCode(code);
        baseEntity.setData(null);
        baseEntity.setMessage(error);
        baseEntity.setSuccess(false);
        return baseEntity;
    }


}


