package com.example.demo.dao;

import com.example.demo.pojo.UmsAdminLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 登录记录Dao
 */

@Repository
public interface UmsAdminLoginLogDao extends JpaRepository<UmsAdminLoginLog, Integer> {
}
