package com.qw.modules.account.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qw.modules.account.dao.ResourceDao;
import com.qw.modules.account.dao.RoleResourceDao;
import com.qw.modules.account.pojo.Resource;
import com.qw.modules.account.pojo.Role;
import com.qw.modules.account.service.ResourceService;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * ClassName: ResourceServiceImpl <br/>
 * Description: <br/>
 * date: 2020/8/22 22:44<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private RoleResourceDao roleResourceDao;

    //查询所有
    @Override
    public List<Resource> getResources() {
        return resourceDao.getResource();
    }

    //新增
    @Override
    public Result<Resource> insertResource(Resource resource) {
        //判断对象是否存在
        Resource resource1 = resourceDao.getResourceByResourceName(resource.getResourceName());
        if (resource1 != null){
            return new Result<Resource>(Result.ResultStatus.FAILD.status,
                    "Resource name is repeat");
        }

        //新增
        resourceDao.insertResource(resource);

        //给中间表添加
        roleResourceDao.deleteRoleResourceByResourceId(resource.getResourceId());
        List<Role> roles = resource.getRoles();
        if (roles!=null && !roles.isEmpty()){
            roles.stream().forEach(item ->{
                roleResourceDao.insertRoleResource(item.getRoleId(),resource.getResourceId());
            });
        }

        return new Result<Resource>(Result.ResultStatus.SUCCESS.status,
                "Insert success.",resource);
    }

    //模糊查询 分页
    @Override
    public PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<Resource>(
                Optional.ofNullable(resourceDao.getResourcesBySearchVo(searchVo))
                        .orElse(Collections.emptyList()));
    }

    //修改
    @Override
    public Result<Resource> updateResource(Resource resource) {
        //用户存在，判断密码是否相等
        Resource resource1 = resourceDao.getResourceByResourceName(resource.getResourceName());
        if (resource1 != null && resource1.getResourceId() != resource.getResourceId()){
            return new Result<Resource>(Result.ResultStatus.FAILD.status,
                    "Resource name is repeat");
        }
        //修改
        resourceDao.updateResource(resource);

        //给中间表添加
        roleResourceDao.deleteRoleResourceByResourceId(resource.getResourceId());
        List<Role> roles = resource.getRoles();
        if (roles!=null && !roles.isEmpty()){
            roles.stream().forEach(item ->{
                roleResourceDao.insertRoleResource(item.getRoleId(),resource.getResourceId());
            });
        }
        return new Result<Resource>(Result.ResultStatus.SUCCESS.status,
                "Update resource success.",resource);
    }

    //删除
    @Override
    public Result<Object> deleteResource(int resourceId) {
        resourceDao.deleteResource(resourceId);
        //删掉中间表对应数据
        roleResourceDao.deleteRoleResourceByResourceId(resourceId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,
                "Delete resource success.");
    }

    //查询
    @Override
    public Resource getResourceByResourceId(int resourceId) {
        return resourceDao.getResourceByResourceId(resourceId);
    }

    @Override
    public List<Resource> getResourceByRoleId(int roleId) {
        return resourceDao.getResourceByRoleId(roleId);
    }
}
