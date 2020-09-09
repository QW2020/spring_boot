package com.qw.config.shiro;

import com.qw.modules.account.pojo.Resource;
import com.qw.modules.account.pojo.Role;
import com.qw.modules.account.pojo.User;
import com.qw.modules.account.service.ResourceService;
import com.qw.modules.account.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName: MyRealm <br/>
 * Description: <br/>
 * date: 2020/8/25 13:54<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;

    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //资源授权器
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //身份验证时第一个参数封装的是user对象，所以这里直接取到对象
        User user = (User)principalCollection.getPrimaryPrincipal();
        List<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()){
            roles.stream().forEach(item ->{
                //将角色信息封装到资源授权器
                simpleAuthorizationInfo.addRole(item.getRoleName());
                List<Resource> resources = resourceService.getResourceByRoleId(item.getRoleId());
                if (resources != null && !resources.isEmpty()){
                    resources.stream().forEach(resource -> {
                        //使用封装的这个字段进行匹配
                        simpleAuthorizationInfo.addStringPermission(resource.getPermission());
                    });
                }
            });
        }

        return simpleAuthorizationInfo;
    }

    @Override
    //身份验证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //得到当前用户名字
        String userName = (String)authenticationToken.getPrincipal();
        //从数据库查询用户
        User user = userService.getUserByUserName(userName);
        if (user == null){
            throw new UnknownAccountException("The account do not exist.");
        }
        //身份验证器：包装的对象 User,数据库密码，name
        return new SimpleAuthenticationInfo(user,user.getPassword(), getName());
    }
}
