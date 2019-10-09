package com.example.demo.dao;

import com.example.demo.pojo.UmsPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *权限Dao
 */
@Repository
public interface UmsPermissionDao extends JpaRepository<UmsPermission, Integer> {

    /**
     *  根据角色获取权限
     * @param roleId
     * @return
     */
    @Query(value = "\n" +
            "SELECT p.* FROM ums_permission p " +
            "LEFT JOIN ums_role_permission_relation rp " +
            "ON p.id = rp.permission_id " +
            "where rp.role_id = ?",nativeQuery = true)
    List<UmsPermission> getPermissionList(Integer roleId);


    @Query(value = "select * from ums_permission p \n" +
            "LEFT JOIN ums_role_permission_relation rp \n" +
            "ON p.id=rp.permission_id \n" +
            "LEFT JOIN ums_admin_role_relation ar \n" +
            "ON ar.role_id = rp.role_id \n" +
            "WHERE ar.admin_id= ?",nativeQuery = true)
    List<UmsPermission> getPermissionListByAdminId(Integer adminId);

}
