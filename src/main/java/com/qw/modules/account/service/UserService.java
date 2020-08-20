package com.qw.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.qw.modules.account.pojo.User;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;

/**
 * ClassName: UserService <br/>
 * Description: <br/>
 * date: 2020/8/20 13:36<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
public interface UserService {
    Result<User> insertUser(User user);

    Result<User> login(User user);

    PageInfo<User> getUsersBySearchVo(SearchVo searchVo);
}
