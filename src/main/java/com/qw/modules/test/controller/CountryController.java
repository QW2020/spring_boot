package com.qw.modules.test.controller;

import com.qw.modules.test.pojo.Country;
import com.qw.modules.test.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: CountryController <br/>
 * Description: <br/>
 * date: 2020/8/11 19:58<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private CountryService countryService;

    /**
     * 127.0.0.1/api/country/522 ---- get
     */
    @GetMapping("/country/{countryId}")
    public Country getCountryByCountryId(@PathVariable int countryId){
        return countryService.getCountryByCountryId(countryId);
    }

    /**
     * 127.0.0.1/api/country?countryName=China ---- get
     */
    @GetMapping("/country")
    public Country getCountryByCountryName(@RequestParam String countryName){
        return countryService.getCountryByCountryName(countryName);
    }

    /**
     * 127.0.0.1/api/redis/country/522 ---- get
     */
    @GetMapping("/redis/country/{countryId}")
    public Country mograteCountryByRedis(@PathVariable int countryId){
        return countryService.mograteCountryByRedis(countryId);
    }
}
