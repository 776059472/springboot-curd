package com.example.demo;

import com.example.demo.dao.UmsAdminDao;
import com.example.demo.pojo.UmsAdmin;
import com.example.demo.service.UmsAdminService;
import com.example.demo.util.JwtTokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminDao adminDao;

    @Test
    public void contextLoads() throws Exception{


        String token = adminService.login("yushen", "123456");
        System.out.println(token);

        String encode = passwordEncoder.encode("123456");

        System.out.println(encode);
        String userNameFromToken = jwtTokenUtil.getUserNameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5dXNoZW4iLCJjcmVhdGVkIjoxNTcwNTMyOTcxMjE1LCJleHAiOjE1NzExMzc3NzF9.VVz-2nT7Apzp8ujO-zr_OspZHvq_LQx3EKYQSr9WJp7u4C4nQF2QdhQ09ovDsAMnMHJH-huu0aEVKOKNSrBF-Q");
        System.out.println(userNameFromToken);
    }


    @Test
    public void test2() throws Exception {
        UmsAdmin admin = new UmsAdmin();
        UmsAdmin save = adminDao.save(admin);
        System.out.println(save);
    }


}
