package com.qw.modules.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName: AccountController <br/>
 * Description: <br/>
 * date: 2020/8/17 15:56<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    /**
     * 127.0.0.1/account/users   ---- get
     * user list 页面
     */
    @GetMapping("/users")
    public String users(){
        return "index";
    }

    /**
     * 127.0.0.1/account/roles   ---- get
     * role list 页面
     */
    @GetMapping("/roles")
    public String roles(){
        return "index";
    }

    /**
     * 127.0.0.1/account/resources   ---- get
     * resource list 页面
     */
    @GetMapping("/resources")
    public String resources(){
        return "index";
    }

    /**
     * 127.0.0.1/account/login   ---- get
     * 登录页面
     */
    @GetMapping("/login")
    public String loginPage(){
        return "indexSimple";
    }

    /**
     * 127.0.0.1/account/register   ---- get
     * 注册页面
     */
    @GetMapping("/register")
    public String registerPage(){
        return "indexSimple";
    }
}
