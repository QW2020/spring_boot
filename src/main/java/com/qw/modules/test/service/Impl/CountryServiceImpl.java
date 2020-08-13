package com.qw.modules.test.service.Impl;

import com.qw.modules.test.dao.CountryDao;
import com.qw.modules.test.pojo.Country;
import com.qw.modules.test.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: CountryServiceImpl <br/>
 * Description: <br/>
 * date: 2020/8/11 20:04<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Override
    public Country getCountryByCountryId(int countryId) {
        return countryDao.getCountryByCountryId(countryId);
    }

    @Override
    public Country getCountryByCountryName(String countryName) {
        return countryDao.getCountryByCountryName(countryName);
    }
}
