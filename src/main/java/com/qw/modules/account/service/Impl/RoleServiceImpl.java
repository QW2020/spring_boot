package com.qw.modules.account.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qw.modules.account.dao.RoleDao;
import com.qw.modules.account.dao.RoleResourceDao;
import com.qw.modules.account.dao.UserRoleDao;
import com.qw.modules.account.pojo.Resource;
import com.qw.modules.account.pojo.Role;
import com.qw.modules.account.pojo.User;
import com.qw.modules.account.service.RoleService;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import com.qw.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * ClassName: RoleServiceImpl <br/>
 * Description: <br/>
 * date: 2020/8/22 11:19<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleResourceDao roleResourceDao;
    @Autowired
    private UserRoleDao userRoleDao;

    //查询所有
    @Override
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    //新增
    @Override
    public Result<Role> insertRole(Role role) {
        //判断对象是否存在
        Role role1 = roleDao.getRoleByRoleName(role.getRoleName());
        if (role1 != null){
            return new Result<Role>(Result.ResultStatus.FAILD.status,
                    "Role name is repeat");
        }

        //新增
        roleDao.insertRole(role);

        //给中间表添加
        roleResourceDao.deleteRoleResourceByRoleId(role.getRoleId());
        List<Resource> resources = role.getResources();
        if (resources!=null && !resources.isEmpty()){
            resources.stream().forEach(item ->{
                roleResourceDao.insertRoleResource(role.getRoleId(),item.getResourceId());
            });
        }

        return new Result<Role>(Result.ResultStatus.SUCCESS.status,
                "Insert success.",role);
    }

    //模糊查询 分页
    @Override
    public PageInfo<Role> getRolesBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<Role>(
                Optional.ofNullable(roleDao.getRolesBySearchVo(searchVo))
                        .orElse(Collections.emptyList()));
    }

    //修改
    @Override
    public Result<Role> updateRole(Role role) {
        //用户存在，判断密码是否相等
        Role role1 = roleDao.getRoleByRoleName(role.getRoleName());
        if (role1 != null && role1.getRoleId() != role.getRoleId()){
            return new Result<Role>(Result.ResultStatus.FAILD.status,
                    "Role name is repeat");
        }
        //修改
        roleDao.updateRole(role);

        //修改中间表对应参数，先把已有的删除
        roleResourceDao.deleteRoleResourceByRoleId(role.getRoleId());
        List<Resource> resources = role.getResources();
        if (resources!=null && !resources.isEmpty()){
            resources.stream().forEach(item ->{
                roleResourceDao.insertRoleResource(role.getRoleId(),item.getResourceId());
            });
        }

        return new Result<Role>(Result.ResultStatus.SUCCESS.status,
                "Update role success.",role);
    }

    //删除
    @Override
    public Result<Object> deleteRole(int roleId) {
        roleDao.deleteRole(roleId);
        //删掉中间表对应数据
        roleResourceDao.deleteRoleResourceByRoleId(roleId);
        userRoleDao.deleteUserRoleByRoleId(roleId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,
                "Delete role success.");
    }

    //查询
    @Override
    public Role getRoleByRoleId(int roleId) {
        return roleDao.getRoleByRoleId(roleId);
    }
}
