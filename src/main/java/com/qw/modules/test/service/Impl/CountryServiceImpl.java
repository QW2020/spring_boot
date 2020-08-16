package com.qw.modules.test.service.Impl;

import com.qw.modules.test.dao.CountryDao;
import com.qw.modules.test.pojo.Country;
import com.qw.modules.test.service.CountryService;
import com.qw.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Country getCountryByCountryId(int countryId) {
        return countryDao.getCountryByCountryId(countryId);
    }

    @Override
    public Country getCountryByCountryName(String countryName) {
        return countryDao.getCountryByCountryName(countryName);
    }

    //把数据库的查询到的数据储存到redis里面，然后从redis读取
    @Override
    public Country mograteCountryByRedis(int countryId) {
        Country country = countryDao.getCountryByCountryId(countryId);
        String countryKey = String.format("country%d",countryId);
        //调用工具类
        redisUtils.set(countryKey,country);
        return (Country)redisUtils.get(countryKey);
    }
}
