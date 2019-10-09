package com.example.demo.dao;

import com.example.demo.pojo.UmsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色Dao
 */
@Repository
public interface UmsRoleDao extends JpaRepository<UmsRole, Integer> {

    @Query("delete from UmsRole where id in(:ids)")
    int deleteAllByIds(List<Integer> ids);

    /**
     * 获取用户所有角色
     * @param adminId
     * @return
     */
    @Query(value = "SELECT r.* FROM ums_role r LEFT JOIN ums_admin_role_relation ar ON ar.role_id = r.id\n" +
            "WHERE ar.admin_id = ?", nativeQuery = true)
    List<UmsRole> getRoleList(Integer adminId);


}
