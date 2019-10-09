package com.example.demo.service;

import com.example.demo.pojo.UmsPermission;
import com.example.demo.pojo.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理Service
 */
public interface UmsRoleService {

    /* *
     * 添加角色*/
    @Transactional
    int create(UmsRole role);

    /**
     * 修改角色信息
     */
    @Transactional
    int update(Integer Integer, UmsRole role);

    /* *
     * 批量删除角色*/
    @Transactional
    int delete(List<Integer> ids);

    /*  *
     * 获取指定角色权限
     */
    List<UmsPermission> getPermissionList(Integer roleId);

    /*
     *
     * 修改指定角色的权限*/
    @Transactional
    int updatePermission(Integer roleId, List<Integer> permissionIds);

    /*
     * 获取角色列表
     */
    List<UmsRole> list();
}
