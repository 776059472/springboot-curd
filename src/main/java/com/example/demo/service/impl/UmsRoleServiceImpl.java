package com.example.demo.service.impl;

import com.example.demo.dao.UmsPermissionDao;
import com.example.demo.dao.UmsRoleDao;
import com.example.demo.dao.UmsRolePermissionRelationDao;
import com.example.demo.pojo.UmsPermission;
import com.example.demo.pojo.UmsRole;
import com.example.demo.pojo.UmsRolePermissionRelation;
import com.example.demo.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台角色管理Service实现类
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleDao roleDao;
    @Autowired
    private UmsPermissionDao permissionDao;

    @Autowired
    private UmsRolePermissionRelationDao rolePermissionRelationDao;

    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setStatus(1);
        role.setAdminCount(0);
        role.setSort(0);
        roleDao.save(role);
        return 1;
    }

    @Override
    public int update(Integer id, UmsRole role) {
        role.setId(id);
        roleDao.save(role);
        return 1;
    }

    @Override
    public int delete(List<Integer> ids) {
        return roleDao.deleteAllByIds(ids);
    }

    @Override
    public List<UmsPermission> getPermissionList(Integer roleId) {
        return permissionDao.getPermissionList(roleId);
    }

    @Override
    public int updatePermission(Integer roleId, List<Integer> permissionIds) {
        //先删除原有关系
        rolePermissionRelationDao.deleteById(roleId);

        //start 批量插入新关系
        List<UmsRolePermissionRelation> relationList = new ArrayList<>();

        for (Integer permissionId : permissionIds) {
            UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);
            relationList.add(relation);
        }

        List<UmsRolePermissionRelation> list = rolePermissionRelationDao.saveAll(relationList);
        //end批量插入新关系

        if(list.isEmpty()){
            return 0;
        }

        return 1;
    }

    @Override
    public List<UmsRole> list() {
        return roleDao.findAll();
    }
}
