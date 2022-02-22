package com.guo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guokaifeng
 * @createDate: 2022/2/17
 **/

@RequestMapping("/share")
@RestController
public class ShareController {
    @RequestMapping("/detail/user/{id}")
    public String userDetail(@PathVariable("id")int id){
        return "demouser"+id;
    }



}
