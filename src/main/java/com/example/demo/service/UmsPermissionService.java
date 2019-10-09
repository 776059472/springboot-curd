package com.example.demo.service;

import com.example.demo.dto.UmsPermissionNode;
import com.example.demo.pojo.UmsPermission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户权限管理Service
 */
public interface UmsPermissionService {

    /**
     * 添加权限
     * @param permission
     * @return
     */
    @Transactional
    int create(UmsPermission permission);


    /**
     * 修改权限
     */
    @Transactional
    int update(Integer id, UmsPermission permission);

    /**
     * 批量删除权限
     */
    @Transactional
    int delete(List<Integer> ids);

    /**
     * 以层级结构返回所有权限
     */
    List<UmsPermissionNode> treeList();

    /*
     * 获取所有权限
     */
    List<UmsPermission> list();
}
