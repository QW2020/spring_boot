package com.qw.modules.account.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ClassName: UserRoleDao <br/>
 * Description: <br/>
 * date: 2020/8/21 18:41<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Repository
@Mapper
public interface UserRoleDao {

    //删除中间表关联数据
    @Delete("delete from user_role where user_id = #{userId}")
    void deleteUserRoleByUserId(int userId);

    //新增
    @Insert("insert into user_role (user_id, role_id) " +
            "values (#{userId}, #{roleId})")
    void insertUserRole(int userId,int roleId);

    //删除中间表关联数据
    @Delete("delete from user_role where role_id = #{roleId}")
    void deleteUserRoleByRoleId(int roleId);
}
