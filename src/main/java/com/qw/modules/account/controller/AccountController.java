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
     */
    @GetMapping("/users")
    public String users(){
        return "index";
    }
}
