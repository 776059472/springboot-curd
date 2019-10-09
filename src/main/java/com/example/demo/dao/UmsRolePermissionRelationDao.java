package com.example.demo.dao;

import com.example.demo.pojo.UmsRolePermissionRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色和权限关系
 */
@Repository
public interface UmsRolePermissionRelationDao extends JpaRepository<UmsRolePermissionRelation, Integer> {


}
