package com.qw.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.qw.modules.account.pojo.User;
import com.qw.modules.account.service.UserService;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

/**
 * ClassName: UserController <br/>
 * Description: <br/>
 * date: 2020/8/20 13:32<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 127.0.0.1/api/user  --- post
     * {"userName":"admin","password":"111"}
     * 注册
     */
    @PostMapping(value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }

    /**
     * n 127.0.0.1/api/logi --- post
     * {"userName":"admin","password":"111"}
     * 登录
     */
    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> login(@RequestBody User user){
        return userService.login(user);
    }

    /**
     * 127.0.0.1/api/users ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"qw","orderBy":"user_name","sort":"desc"}
     * 模糊查询，脚本查询
     */
    @PostMapping(value = "/users",consumes = "application/json")
    public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo){
        return userService.getUsersBySearchVo(searchVo);
    }
}
