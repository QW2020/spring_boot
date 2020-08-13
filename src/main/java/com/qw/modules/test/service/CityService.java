package com.qw.modules.test.service;

import com.github.pagehelper.PageInfo;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import com.qw.modules.test.pojo.City;

import java.util.List;

/**
 * ClassName: CityService <br/>
 * Description: <br/>
 * date: 2020/8/11 21:47<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
public interface CityService {
    List<City> getCitiesByCountryId(int countryId);

    PageInfo<City> getCitiesBySearchVo(int countryId, SearchVo searchVo);

    PageInfo<City> getCitiesBySearchVo(SearchVo searchVo);

    Result<City> insertCity(City city);

    Result<City> updateCity(City city);

    Result<Object> deleteCity(int cityId);
}
