package com.debug52.spring.security.security;


import com.debug52.utils.util.R;
import com.debug52.utils.util.RedisUtil;
import com.debug52.utils.util.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//退出处理器
public class TokenLogoutHandler implements LogoutHandler {
    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String tokenKey = request.getHeader("Authorization");
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        String userId = (String) redisUtil.get(tokenKey + "userId");
        if (userId != null) {
            //从token获取用户名
            redisUtil.del(userId);
        }
        ResponseUtil.out(response, R.ok());
    }
}
