package com.qw.modules.account.dao;

import com.qw.modules.account.pojo.Role;
import com.qw.modules.account.pojo.User;
import com.qw.modules.common.vo.SearchVo;
import com.qw.modules.test.pojo.City;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: UserDao <br/>
 * Description: <br/>
 * date: 2020/8/20 8:57<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Repository
@Mapper
public interface UserDao {

    //新增
    @Insert("insert into user (user_name,password,user_img,create_date) " +
            "values(#{userName},#{password},#{userImg},#{createDate})")
    @Options(useGeneratedKeys = true,keyColumn = "user_id",keyProperty = "userId")
    void insertUser(User user);

    //通过name查询
    @Select("select * from user where user_name = #{userName}")
    @ResultMap(value = "userResults")
    User getUserByUserName(String userName);

    //模糊查询
    @Select("<script>" +
            "select * from user "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (user_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by user_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<User> getUsersBySearchVo(SearchVo searchVo);

    //修改
    @Update("update user set user_name = #{userName},user_img = #{userImg}" +
            " where user_id = #{userId}")
    void updateUser(User user);

    //删除
    @Delete("delete from user where user_id = #{userId}")
    void deleteUser(int userId);

    //多表查询
    @Select("select * from user where user_id = #{userId}")
    @Results(id = "userResults", value ={
            @Result(column = "user_id",property = "userId"),
            @Result(column = "user_id",property = "roles",
                    javaType = List.class,
                    many = @Many(select = "com.qw.modules.account." +
                            "dao.RoleDao.getRolesByUserId"))})
    User getUserByUserId(int userId);

    //通过roleId连表查询users
    @Select("select * from user u left join user_role us on " +
            "u.user_id = us.user_id where us.role_id = #{roleId}")
    List<User> getUsersByRoleId(int roleId);
}
