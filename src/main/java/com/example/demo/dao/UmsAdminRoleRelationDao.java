package com.example.demo.dao;

import com.example.demo.pojo.UmsAdminRoleRelation;
import com.example.demo.pojo.UmsPermission;
import com.example.demo.pojo.UmsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *用户和角色关
 */
@Repository
public interface UmsAdminRoleRelationDao extends JpaRepository<UmsAdminRoleRelation, Integer> {

}
