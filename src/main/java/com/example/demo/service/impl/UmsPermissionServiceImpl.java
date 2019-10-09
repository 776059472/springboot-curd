package com.example.demo.service.impl;

import com.example.demo.dao.UmsPermissionDao;
import com.example.demo.dto.UmsPermissionNode;
import com.example.demo.pojo.UmsPermission;
import com.example.demo.service.UmsPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户权限管理Service实现类
 * Created by macro on 2018/9/29.
 */
@Service
public class UmsPermissionServiceImpl implements UmsPermissionService {

    @Autowired
    private UmsPermissionDao permissionDao;

    @Override
    public int create(UmsPermission permission) {
        permission.setStatus(1);
        permission.setCreateTime(new Date());
        permission.setSort(0);
        UmsPermission rePer = permissionDao.save(permission);
        return rePer == null ? 0 : 1;
    }

    
    @Override
    public int update(Integer id, UmsPermission permission) {
        permission.setId(id);

        return (permissionDao.save(permission) == null ? 0 : 1);
    }

    @Override
    public int delete(List<Integer> ids) {
        for (Integer id : ids){
            permissionDao.deleteById(id);
        }
        return 1;
    }

    @Override
    public List<UmsPermissionNode> treeList() {

        List<UmsPermission> permissionList = permissionDao.findAll();

        List<UmsPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission,permissionList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<UmsPermission> list() {
        return permissionDao.findAll();
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private UmsPermissionNode covert(UmsPermission permission,List<UmsPermission> permissionList){
        UmsPermissionNode node = new UmsPermissionNode();
        BeanUtils.copyProperties(permission,node);
        List<UmsPermissionNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission,permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
