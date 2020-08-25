package com.qw.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.qw.modules.account.pojo.User;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import org.springframework.web.multipart.MultipartFile;

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

    Result<User> updateUser(User user);

    Result<Object> deleteUser(int userId);

    User getUserByUserId(int userId);

    Result<String> uploadUserImg(MultipartFile file);

    User getUserByUserName(String userName);

    void logout();
}
