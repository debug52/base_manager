package com.debug52.gateway.service.impl;



import com.debug52.gateway.bean.User;
import com.debug52.gateway.mapper.UserMapper;
import com.debug52.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description:
 * @Author debug52
 * @Date 2021/10/14
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public boolean getUserPermission4Logout(Integer userId) {
        List<Integer> userPermission4Logout = userMapper.getUserPermission4Logout(userId);
        User user = userMapper.selectByUserId(userId);

        if (userPermission4Logout==null||userPermission4Logout.size()==0||!userPermission4Logout.contains(1)||user.getState()==2){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean getUserPermission(Integer userId) {
        List<Integer> userPermissionList = userMapper.getUserPermissionList(userId);
        if (userPermissionList==null||userPermissionList.size()==0){
            return false;
        }
        return true;
    }
}
