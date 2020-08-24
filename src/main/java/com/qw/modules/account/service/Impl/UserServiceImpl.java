package com.qw.modules.account.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qw.config.ResourceConfigBean;
import com.qw.modules.account.dao.UserDao;
import com.qw.modules.account.dao.UserRoleDao;
import com.qw.modules.account.pojo.Role;
import com.qw.modules.account.pojo.User;
import com.qw.modules.account.service.UserService;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import com.qw.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private ResourceConfigBean resourceConfigBean;

    @Override
    @Transactional
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

        //给中间表添加
        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        List<Role> roles = user.getRoles();
        if (roles!=null && !roles.isEmpty()){
            roles.stream().forEach(item ->{
                userRoleDao.insertUserRole(user.getUserId(),item.getRoleId());
            });
        }

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

    //修改
    @Override
    @Transactional
    public Result<User> updateUser(User user) {
        //用户存在，判断密码是否相等
        User user1 = userDao.getUserByUserName(user.getUserName());
        if (user1 != null && user1.getUserId() != user.getUserId()){
            return new Result<User>(Result.ResultStatus.FAILD.status,
                    "User name is repeat");
        }
        //修改
        userDao.updateUser(user);

        //修改中间表对应参数，先把已有的删除
        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        List<Role> roles = user.getRoles();
        if (roles!=null && !roles.isEmpty()){
            roles.stream().forEach(item ->{
                userRoleDao.insertUserRole(user.getUserId(),item.getRoleId());
            });
        }

        return new Result<User>(Result.ResultStatus.SUCCESS.status,
                "Update user success.",user);
    }

    @Override
    @Transactional
    //删除
    public Result<Object> deleteUser(int userId) {
        userDao.deleteUser(userId);
        //删掉中间表对应数据
        userRoleDao.deleteUserRoleByUserId(userId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,
                "Delete user success.");
    }

    //查询
    @Override
    public User getUserByUserId(int userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    public Result<String> uploadUserImg(MultipartFile file) {
        //如果为空，重新选择
        if (file.isEmpty()){
            return new Result<String>(
                    Result.ResultStatus.FAILD.status,
                    "Please select img. ");
        }
        //相对路径
        String relativePath = "";
        //本地绝对路径
        String destFilePath = "";
        try {
            //拿到操作系统名字
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().startsWith("win")) {
                //如果是windows系统，拿到本地路径 + 文件名
                destFilePath = resourceConfigBean.getLocationPathForWindows()
                        + file.getOriginalFilename();
            } else {
                //如果是Linux，拿到本地Linux路径 + 文件名
                destFilePath = resourceConfigBean.getLocationPathForLinux()
                        + file.getOriginalFilename();
            }
            //设置相对路径
            relativePath = resourceConfigBean.getRelativePath() +
                    file.getOriginalFilename();
            //目标文件
            File destFile = new File(destFilePath);
            //上传到目标文件
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<String>(
                    Result.ResultStatus.FAILD.status,
                    "Upload file failed.");
        }
        return new Result<String>(
                Result.ResultStatus.SUCCESS.status,
                "Upload file success.",relativePath);
    }
}
