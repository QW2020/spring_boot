package com.qw.modules.test.controller;

import com.qw.modules.test.pojo.City;
import com.qw.modules.test.pojo.Country;
import com.qw.modules.test.service.CityService;
import com.qw.modules.test.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

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
     * 测试value属性
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


    /**
     * 127.0.0.1/test/testDesc?paramKey=fuck ---get
     * 测试过滤器
     */
    @GetMapping("/testDesc")
    @ResponseBody
    public String testDesc(HttpServletRequest request, @RequestParam(value = "paramKey") String paramValue){
        String paramValue2 = request.getParameter("paramKey");
        return "This is test module desc." + paramValue + "==" + paramValue2;
    }

    /**
     *
     * 127.0.0.1:8080/test/logTest ---get
     * 测试log
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

    /**
     * 127.0.0.1/test/index    ---get
     */
    @GetMapping("/index")
    public String testIndexPage(ModelMap modelMap){
        int countryId = 522;
        List<City> cities = cityService.getCitiesByCountryId(countryId);
        cities = cities.stream().limit(10).collect(Collectors.toList());
        Country country = countryService.getCountryByCountryId(countryId);

        modelMap.addAttribute("thymeleafTitle", "scdscsadcsacd");
        modelMap.addAttribute("checked", true);
        modelMap.addAttribute("currentNumber", 99);
        modelMap.addAttribute("changeType", "checkbox");
        modelMap.addAttribute("baiduUrl", "/test/log");
        modelMap.addAttribute("city", cities.get(0));
        modelMap.addAttribute("shopLogo",
                "http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
        modelMap.addAttribute("shopLogo",
                "/upload/1111.png");
        modelMap.addAttribute("country", country);
        modelMap.addAttribute("cities", cities);
        modelMap.addAttribute("updateCityUri", "/api/city");
//        modelMap.addAttribute("template", "test/index");
        // 返回外层的碎片组装器
        return "index";
    }
}
