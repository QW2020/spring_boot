package com.qw.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName: CommonController <br/>
 * Description: <br/>
 * date: 2020/8/20 19:54<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    /**
     * 127.0.0.1/common/dashBoard  ----  get
     */
    @GetMapping("/dashBoard")
    public String dashBoardPage(){
        return "index";
    }
}
