package com.example.demo.service.impl;

import com.example.demo.dao.*;
import com.example.demo.dto.UmsAdminParam;
import com.example.demo.pojo.*;
import com.example.demo.service.UmsAdminService;
import com.example.demo.util.JwtTokenUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UmsAdminService实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private UmsAdminDao adminDao;
    @Autowired
    private UmsRoleDao roleDao;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    private UmsPermissionDao permissionDao;
    @Autowired
    private UmsAdminLoginLogDao adminLoginLogDao;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdmin admin = adminDao.getAdminByUsername(username);
        return admin;
    }


    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(true);

        //查询是否有相同用户名的用户
        UmsAdmin adminByUsername = adminDao.getAdminByUsername(umsAdmin.getUsername());
        if (adminByUsername != null) {
            return null;
        }

        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);

        adminDao.save(umsAdmin);

        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //生成token
            token = jwtTokenUtil.generateToken(userDetails);

            //登录记录
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }

        return token;
    }

    /**
     * 获取用户所有权限
     * @param adminId
     * @return
     */
    @Override
    public List<UmsPermission> getPermissionListByAdminId(Integer adminId) {
        return permissionDao.getPermissionListByAdminId(adminId);
    }

   /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        loginLog.setIp(request.getRemoteAddr());

        adminLoginLogDao.save(loginLog);
    }


    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        if (jwtTokenUtil.canRefresh(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public UmsAdmin getAdminById(Integer id) {
        return adminDao.findById(id).get();
    }

    @Override
    public List<UmsAdmin> list(String name, Integer pageSize, Integer pageNum) {

        List<UmsAdmin> adminListByNamePage = adminDao.findAdminListByNamePage(name, pageSize, pageNum);

        return adminListByNamePage;

    }

    @Override
    public int updateAdminById(Integer id, UmsAdmin admin) {
        admin.setId(id);
        //密码已经加密处理，需要单独修改

        if(admin.getPassword() == null || admin.getPassword().equals("")){
            return 0 ;
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        adminDao.save(admin);
        return 1;
    }

    @Override
    @Transactional
    public int deleteAdminById(Integer id) {

        Optional<UmsAdmin> optional = adminDao.findById(id);

        //判断是否有该用户
        if(optional.orElse(null) == null){
            return 0;
        }

        adminDao.deleteById(id);

        return 1;
    }

    @Override
    public int updateAdminRoleById(Integer adminId, List<Integer> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        adminRoleRelationDao.deleteById(adminId);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Integer roleId : roleIds) {
                UmsAdminRoleRelation roleRelation = new UmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationDao.deleteAll(list);
        }
        return count;
    }

    @Override
    public List<UmsRole> getAdminRoleList(Integer adminId) {
        return  roleDao.getRoleList(adminId);
    }

}
