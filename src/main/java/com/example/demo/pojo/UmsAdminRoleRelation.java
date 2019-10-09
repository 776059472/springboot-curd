package com.example.demo.pojo;

import lombok.Getter;
import lombok.Setter;
import org.omg.PortableInterceptor.INACTIVE;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ums_admin_role_relation")
public class UmsAdminRoleRelation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer adminId;

    private Integer roleId;


}