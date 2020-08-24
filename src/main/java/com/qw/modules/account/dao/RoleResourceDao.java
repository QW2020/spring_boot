package com.qw.modules.account.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ClassName: RoleResource <br/>
 * Description: <br/>
 * date: 2020/8/22 22:06<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Repository
@Mapper
public interface RoleResourceDao {
    //删除中间表关联数据
    @Delete("delete from role_resource where role_id = #{roleId}")
    void deleteRoleResourceByRoleId(int roleId);

    //新增
    @Insert("insert into role_resource (role_id, resource_id) " +
            "values (#{roleId}, #{resourceId})")
    void insertRoleResource(int roleId,int resourceId);

    //删除中间表关联数据
    @Delete("delete from role_resource where resource_id = #{resourceId}")
    void deleteRoleResourceByResourceId(int resourceId);

}
