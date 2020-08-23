package com.qw.modules.account.dao;

import com.qw.modules.account.pojo.Resource;
import com.qw.modules.account.pojo.Role;
import com.qw.modules.account.pojo.User;
import com.qw.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: RoleDao <br/>
 * Description: <br/>
 * date: 2020/8/21 19:01<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Repository
@Mapper
public interface RoleDao {

    //通过userId连表查询Roles
    @Select("select * from role r left join user_role us " +
            "on r.role_id = us.role_id where us.user_id = #{userId} ")
    List<Role> getRolesByUserId(int userId);
    //通过resourceId连表查询Roles
    @Select("select * from role r left join role_resource rr " +
            "on r.role_id = rr.role_id where rr.resource_id = #{resourceId} ")
    List<Resource> getRolesByResourceId(int resourceId);

    //查询所有角色
    @Select("select * from role")
    List<Role> getRoles();

    //通过name查询
    @Select("select * from role where role_name = #{roleName}")
    Role getRoleByRoleName(String roleName);

    //新增
    @Insert("insert into role (role_name) values(#{roleName})")
    @Options(useGeneratedKeys = true,keyColumn = "role_id",keyProperty = "roleId")
    void insertRole(Role role);

    //模糊查询
    @Select("<script>" +
            "select * from role "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (role_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by role_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Role> getRolesBySearchVo(SearchVo searchVo);

    //修改
    @Update("update role set role_name = #{roleName} where role_id = #{roleId}")
    void updateRole(Role role);

    //删除
    @Delete("delete from role where role_id = #{roleId}")
    void deleteRole(int roleId);

    //多表查询roles
    @Select("select * from role where role_id = #{roleId}")
    @Results(id = "roleResults", value ={
            @Result(column = "role_id",property = "roleId"),
            @Result(column = "role_id",property = "resources",
                    javaType = List.class,
                    many = @Many(select = "com.qw.modules.account." +
                            "dao.ResourceDao.getResourceByRoleId")),
            @Result(column = "role_id",property = "users",
                    javaType = List.class,
                    many = @Many(select = "com.qw.modules.account." +
                            "dao.UserDao.getUsersByRoleId"))})
    Role getRoleByRoleId(int roleId);
}
