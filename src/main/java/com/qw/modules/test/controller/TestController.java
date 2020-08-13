package com.qw.modules.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: TestController <br/>
 * Description: <br/>
 * date: 2020/8/10 13:58<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Value("${server.port}")
    private int port;
    @Value("${com.name}")
    private String name;
    @Value("${com.age}")
    private int age;
    @Value("${com.desc}")
    private String desc;
    @Value("${com.random}")
    private String random;

    /**
     *
     * 127.0.0.1:8080/test/config ---get
     */
    @GetMapping("/config")
    @ResponseBody
    public String configTest(){
        return new StringBuffer()
                .append(port).append("--------")
                .append(name).append("--------")
                .append(age).append("--------")
                .append(desc).append("--------")
                .append(random).append("--------")
                .toString();
    }


    @GetMapping("/testDesc")
    @ResponseBody
    public String testDesc(){
        return "Holle";
    }

    /**
     *
     * 127.0.0.1:8080/test/logTest ---get
     */
    @GetMapping("/logTest")
    @ResponseBody
    public String logTest(){
        LOGGER.debug("-----------------logTest()---------------"+"this is DEBUG LOG");
        LOGGER.trace("-----------------logTest()---------------"+"this is trace LOG");
        LOGGER.info("-----------------logTest()---------------"+"this is info LOG");
        LOGGER.warn("-----------------logTest()---------------"+"this is warn LOG");
        LOGGER.error("-----------------logTest()---------------"+"this is error LOG");
        return "this is log test";
    }
}
