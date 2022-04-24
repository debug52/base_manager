package com.debug52.spring.security.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserVO {
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("角色名")
    private String role;
    @ApiModelProperty("token信息")
    private String token;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("头像地址")
    private String imgUrl;
    @ApiModelProperty("电话")
    private String tel;
    @ApiModelProperty("公司")
    private String company;
    @ApiModelProperty("是否是超级管理员，1是，2否")
    private Integer isSuperAdministrator;
    @ApiModelProperty("区域")
    private String region;
}
