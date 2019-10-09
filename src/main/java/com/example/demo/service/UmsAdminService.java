package com.example.demo.service;


import com.example.demo.dto.UmsAdminParam;
import com.example.demo.pojo.UmsAdmin;
import com.example.demo.pojo.UmsPermission;
import com.example.demo.pojo.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理员Service
 */
public interface UmsAdminService {


    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);


    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户id获取用户
     */
    UmsAdmin getAdminById(Integer id);

    /**
     * 修改指定用户信息
     */
    @Transactional
    int updateAdminById(Integer id, UmsAdmin admin);

    /*
     * 删除指定用户
     */
    @Transactional
    int deleteAdminById(Integer id);


    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateAdminRoleById(Integer adminId, List<Integer> roleIds);

    /**
     * 获取用户对于角色
     */
    List<UmsRole> getAdminRoleList(Integer adminId);


    /**
     * 根据用户名或昵称分页查询用户
     */
    List<UmsAdmin> list(String name, Integer pageSize, Integer pageNum);



    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionListByAdminId(Integer adminId);


}
