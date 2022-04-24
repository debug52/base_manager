package com.debug52.gateway.mapper;

import com.debug52.gateway.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description:
 * @Author debug52
 * @Date 2021/10/14
 **/
public interface UserMapper {
    //根据用户id查询角色状态
    User selectByUserId(Integer userId);
    List<Integer> getUserPermission4Logout(Integer userId);
    List<Integer> getUserPermissionList(@Param("userId") Integer userId);
    List<User> selectByUserName(String username);
}
