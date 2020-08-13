package com.qw.modules.test.dao;

import com.qw.modules.test.pojo.Country;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: CountryDao <br/>
 * Description: <br/>
 * date: 2020/8/11 20:11<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Repository
@Mapper
public interface CountryDao {

    @Select("select * from m_country where country_id = #{countryId}")
    @Results(id = "countryResults",value = {
            @Result(column = "country_id",property = "countryId"),
            @Result(column = "country_id",property = "cities",javaType = List.class,
            many = @Many(select = "com.qw.modules.test.dao.CityDao.getCitiesByCountryId"))
    })
    Country getCountryByCountryId(int countryId);

    @Select("select * from m_country where country_name = #{countryName}")
    @ResultMap(value = "countryResults")
    Country getCountryByCountryName(String countryName);
}
