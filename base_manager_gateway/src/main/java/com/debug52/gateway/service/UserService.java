package com.debug52.gateway.service;

/**
 * @ClassName UserService
 * @Description:
 * @Author debug52
 * @Date 2021/10/14
 **/
public interface UserService {
    boolean getUserPermission4Logout(Integer userId);
    boolean getUserPermission(Integer userId);
}
