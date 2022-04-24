package com.debug52.system.config.utils;

import com.debug52.utils.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author debug52
 * @:version 1.0
 **/
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZE_TOKEN = "Authorization";
    @Autowired
    RedisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenKey = request.getHeader(AUTHORIZE_TOKEN);
        String token = (String) redisUtil.get(tokenKey);
        if (token==null||"".equals(token)){
            request.setAttribute("userId",null);
        }
        String userId = (String) redisUtil.get(tokenKey+"userId");
        request.setAttribute("userId",userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
