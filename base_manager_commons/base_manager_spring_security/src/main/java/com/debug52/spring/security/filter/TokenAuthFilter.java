package com.debug52.spring.security.filter;

import com.debug52.utils.util.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenAuthFilter extends BasicAuthenticationFilter {


    private RedisTemplate redisTemplate;

    public TokenAuthFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取当前认证成功用户权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        //判断如果有权限信息，放到权限上下文中
        if (authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //从header获取token
        String tokenKey = request.getHeader("Authorization");
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        String token = (String) redisUtil.get(tokenKey);
        String userId = (String) redisUtil.get(tokenKey + "userId");
        if (userId != null) {
            //redis中获取权限
            List<String> permissionValueList = (List<String>) redisUtil.get(userId);
            if (permissionValueList == null || permissionValueList.size() == 0) {
                return null;
            }
            Collection<GrantedAuthority> authority = new ArrayList<>();
            for (String permissionValue : permissionValueList) {
                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permissionValue);
                authority.add(auth);
            }
            return new UsernamePasswordAuthenticationToken(userId, token, authority);
        }
        return null;
    }

}
