package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;

/**
 * 用户登录参数
 */
@Getter
@Setter
public class UmsAdminLoginParam {
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

}
