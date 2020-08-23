package com.qw.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.qw.modules.account.pojo.Role;
import com.qw.modules.account.pojo.User;
import com.qw.modules.account.service.RoleService;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: RoleController <br/>
 * Description: <br/>
 * date: 2020/8/22 11:14<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 127.0.0.1/api/roles  ---- get
     * 查询所有
     */
    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.getRoles();
    }

    /**
     * 127.0.0.1/api/role  --- post
     * {"roleName":"employees"}
     * 新增
     */
    @PostMapping(value = "/role",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Role> insertRole(@RequestBody Role role){
        return roleService.insertRole(role);
    }

    /**
     * 127.0.0.1/api/roles ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"qw","orderBy":"role_name","sort":"desc"}
     * 模糊查询，脚本查询
     */
    @PostMapping(value = "/roles",consumes = "application/json")
    public PageInfo<Role> getRolesBySearchVo(@RequestBody SearchVo searchVo){
        return roleService.getRolesBySearchVo(searchVo);
    }

    /**
     * 127.0.0.1/api/updateRole ---- put
     * {"roleName":"qw2","roleId":"4"}
     * 修改
     */
    @PutMapping(value = "/updateRole",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Role> updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    /**
     * 127.0.0.1/api/deleteRole/x ---- delete
     * 删除
     */
    @DeleteMapping("/deleteRole/{roleId}")
    public Result<Object> deleteRole(@PathVariable int roleId){
        return roleService.deleteRole(roleId);
    }

    /**
     * 127.0.0.1/api/role/1 ---- get
     * 查询
     */
    @GetMapping("/role/{roleId}")
    public Role getRoleByRoleId(@PathVariable int roleId){
        return roleService.getRoleByRoleId(roleId);
    }
}
