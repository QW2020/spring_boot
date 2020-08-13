package com.qw.modules.test.service;

import com.qw.modules.test.pojo.Country;

/**
 * ClassName: CountryService <br/>
 * Description: <br/>
 * date: 2020/8/11 20:04<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
public interface CountryService {
    Country getCountryByCountryId(int countryId);

    Country getCountryByCountryName(String countryName);
}
