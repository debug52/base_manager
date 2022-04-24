package com.debug52.spring.security.bean;

import lombok.Data;

@Data
public class UserDO {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private Integer state;
}
