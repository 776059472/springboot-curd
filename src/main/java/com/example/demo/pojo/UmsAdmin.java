package com.example.demo.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ums_admin")
public class UmsAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String icon;        //头像

    private String email;

    private String nickName;   //昵称

    private String note;        //备注信息

    private Date createTime;   //创建时间

    private Date loginTime;    //最后登录时间

    private Boolean status;      //状态


}
