package com.debug52.spring.security.userprovider.mapper;

import com.debug52.spring.security.bean.PermissionBean;
import com.debug52.spring.security.bean.UserDO;
import com.debug52.spring.security.bean.UserDetail;

import java.util.List;

/**
 * @author debug52
 * @version 1.0
 **/

public interface SecrityUserMapper {
    //根据用户id查询出所有权限
    List<PermissionBean> selectSPerById(Integer userId);

    List<PermissionBean> selectSPerByUserName(String username);
    List<Integer> selectUserRoleSByUserId(Integer userId);
    List<UserDO> selectByUserSName(String username);
    List<Integer> getUserSPermissionList(Integer userId);
    List<String> selectUserSRole(Integer userId);
    UserDetail selectUsersDetailByUserId(Integer userid);


}
