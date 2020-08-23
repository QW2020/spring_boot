package com.qw.modules.account.dao;

import com.github.pagehelper.PageInfo;
import com.qw.modules.account.pojo.Resource;
import com.qw.modules.account.pojo.Role;
import com.qw.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: Resource <br/>
 * Description: <br/>
 * date: 2020/8/22 22:41<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Repository
@Mapper
public interface ResourceDao {
    //连表查询resource
    @Select("select * from resource r left join role_resource rr " +
            "on r.resource_id = rr.resource_id where rr.role_id = #{roleId} ")
    List<Resource> getResourceByRoleId(int roleId);

    //查询所有任务
    @Select("select * from resource")
    List<Resource> getResource();

    //多表查询resources
    @Select("select * from resource where resource_id = #{resourceId}")
    @Results(id = "resourceResults", value ={
            @Result(column = "resource_id",property = "resourceId"),
            @Result(column = "resource_id",property = "roles",
                    javaType = List.class,
                    many = @Many(select = "com.qw.modules.account.dao.RoleDao.getRolesByResourceId"))})
    Resource getResourceByResourceId(int resourceId);

    //删除
    @Delete("delete from resource where resource_id = #{resourceId}")
    void deleteResource(int resourceId);

    //修改
    @Update("update resource set resource_name = #{resourceName}, resource_uri = #{resourceUri}, " +
            "permission = #{permission} where resource_id = #{resourceId}")
    void updateResource(Resource resource);

    //通过名字查询
    @Select("select * from resource where resource_name = #{resourceName}")
    Resource getResourceByResourceName(String resourceName);

    //模糊查询 分页
    @Select("<script>" +
            "select * from resource "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (resource_name like '%${keyWord}%' or resource_uri like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by resource_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Resource> getResourcesBySearchVo(SearchVo searchVo);

    //新增
    @Insert("insert into resource (resource_name,resource_uri,permission) " +
            "values(#{resourceName}, #{resourceUri}, #{permission})")
    @Options(useGeneratedKeys = true,keyColumn = "resource_id",keyProperty = "resourceId")
    void insertResource(Resource resource);
}
