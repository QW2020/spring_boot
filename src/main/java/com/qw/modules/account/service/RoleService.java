package com.qw.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.qw.modules.account.pojo.Role;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;

import java.util.List;

/**
 * ClassName: RoleService <br/>
 * Description: <br/>
 * date: 2020/8/22 11:19<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
public interface RoleService {
    List<Role> getRoles();

    Result<Role> insertRole(Role role);

    PageInfo<Role> getRolesBySearchVo(SearchVo searchVo);

    Result<Role> updateRole(Role role);

    Result<Object> deleteRole(int roleId);

    Role getRoleByRoleId(int roleId);
}
