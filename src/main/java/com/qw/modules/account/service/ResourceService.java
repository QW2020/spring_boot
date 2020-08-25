package com.qw.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.qw.modules.account.pojo.Resource;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;

import java.util.List;

/**
 * ClassName: ResourceService <br/>
 * Description: <br/>
 * date: 2020/8/22 22:43<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
public interface ResourceService {
    List<Resource> getResources();

    Result<Resource> insertResource(Resource resource);

    PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo);

    Result<Resource> updateResource(Resource resource);

    Result<Object> deleteResource(int resourceId);

    Resource getResourceByResourceId(int resourceId);

    List<Resource> getResourceByRoleId(int roleId);
}
