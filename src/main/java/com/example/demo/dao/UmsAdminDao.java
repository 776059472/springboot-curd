package com.example.demo.dao;

import com.example.demo.pojo.UmsAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsAdminDao extends JpaRepository<UmsAdmin, Integer> {

    /**
     * 根据 用户名 获取用户信息
     * @param username
     * @return UmsAdmin
     */
    @Query(value = "select * from ums_admin where username=? ",nativeQuery = true)
    UmsAdmin getAdminByUsername(String username);

    /**
     * 根据 用户名 和 昵称 查找用户信息分页
     * @param name
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Query(value = "select ums_admin from ums_admin where username = :name or nick_name = :name" +
            "limit :pageSize, :pageNum" ,nativeQuery = true)
    List<UmsAdmin> findAdminListByNamePage(String name, Integer pageSize, Integer pageNum);


}
