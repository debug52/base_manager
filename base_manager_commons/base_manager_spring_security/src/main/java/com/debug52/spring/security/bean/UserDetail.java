package com.debug52.spring.security.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author debug52
 * @version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
    private Integer userId;
    private String username;
    private String name;
    private List<String> role;
    private String company;
    private String tel;
    private Date createDate;
    private List<PermissionBean> permissionsList;
    private String imgUrl;
}
