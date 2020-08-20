package com.qw.modules.account.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qw.modules.account.dao.UserDao;
import com.qw.modules.account.pojo.User;
import com.qw.modules.account.service.UserService;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import com.qw.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

/**
 * ClassName: UserServiceImpl <br/>
 * Description: <br/>
 * date: 2020/8/20 13:36<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    //注册
    public Result<User> insertUser(User user) {
        //判断对象是否存在
        User user1 = userDao.getUserByUserName(user.getUserName());
        if (user1 != null){
            return new Result<User>(Result.ResultStatus.FAILD.status,
                    "User name is repeat");
        }
        //设置时间
        user.setCreateDate(LocalDateTime.now());
        //给密码加密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        //新增
        userDao.insertUser(user);
        return new Result<User>(Result.ResultStatus.SUCCESS.status,
                "Insert success.",user);
    }

    @Override
    //登录   用户名、密码 比对
    public Result<User> login(User user) {
        //用户存在，判断密码是否相等
        User user1 = userDao.getUserByUserName(user.getUserName());
        if (user1 != null &&
                user1.getPassword().equals(MD5Util.getMD5(user.getPassword()))){
            return new Result<User>(Result.ResultStatus.SUCCESS.status,
                    "Success.",user1);
        }
        return new Result<User>(Result.ResultStatus.FAILD.status,
                "UserName or password is error.");
    }

    //模糊查询  分页
    @Override
    public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<User>(
                Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))
                .orElse(Collections.emptyList()));
    }
}
