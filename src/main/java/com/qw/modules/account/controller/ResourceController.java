package com.qw.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.qw.modules.account.pojo.Resource;
import com.qw.modules.account.pojo.Role;
import com.qw.modules.account.service.ResourceService;
import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: ResourceController <br/>
 * Description: <br/>
 * date: 2020/8/22 22:42<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 127.0.0.1/api/resources  ---- get
     * 查询所有
     */
    @GetMapping("/resources")
    public List<Resource> getResources(){
        return resourceService.getResources();
    }

    /**
     * 127.0.0.1/api/resource  --- post
     * {"resourceName":"employees"}
     * 新增
     */
    @PostMapping(value = "/resource",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Resource> insertResource(@RequestBody Resource resource){
        return resourceService.insertResource(resource);
    }

    /**
     * 127.0.0.1/api/resources ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"qw","orderBy":"role_name","sort":"desc"}
     * 模糊查询，脚本查询
     */
    @PostMapping(value = "/resources",consumes = "application/json")
    public PageInfo<Resource> getResourcesBySearchVo(@RequestBody SearchVo searchVo){
        return resourceService.getResourcesBySearchVo(searchVo);
    }

    /**
     * 127.0.0.1/api/updateResource ---- put
     * {"resourceName":"qw2","resourceId":"4"}
     * 修改
     */
    @PutMapping(value = "/updateResource",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Resource> updateResource(@RequestBody Resource resource){
        return resourceService.updateResource(resource);
    }

    /**
     * 127.0.0.1/api/deleteResource/x ---- delete
     * 删除
     */
    @DeleteMapping("/deleteResource/{resourceId}")
    public Result<Object> deleteResource(@PathVariable int resourceId){
        return resourceService.deleteResource(resourceId);
    }

    /**
     * 127.0.0.1/api/resource/1 ---- get
     * 查询
     */
    @GetMapping("/resource/{resourceId}")
    public Resource getResourceByResourceId(@PathVariable int resourceId){
        return resourceService.getResourceByResourceId(resourceId);
    }
}
