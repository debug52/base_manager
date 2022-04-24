package com.debug52.spring.security.filter;

import com.debug52.spring.security.bean.vo.UserVO;
import com.debug52.spring.security.userprovider.mapper.SecrityUserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.debug52.spring.security.bean.SecurityUser;
import com.debug52.spring.security.bean.User;
import com.debug52.spring.security.bean.UserDetail;
import com.debug52.utils.JwtTokenUtils;
import com.debug52.utils.util.R;
import com.debug52.utils.util.RedisUtil;
import com.debug52.utils.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    SecrityUserMapper secrityUserMapper;

    private RedisTemplate redisTemplate;
    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    //1 获取表单提交用户名和密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        //获取表单提交数据
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                    new ArrayList<>()));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    //2 认证成功调用的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        //认证成功，得到认证成功之后用户信息
        SecurityUser user = (SecurityUser) authResult.getPrincipal();
        if (user.getId() == null) {
            ResponseUtil.out(response, R.error().message("用户不存在"));
        }
        if (user.getCurrentUserInfo().getState() != 1) {
            ResponseUtil.out(response, R.error().message("用户已被停用"));
        }
        if (!user.getRoleState().contains(1)) {
            ResponseUtil.out(response, R.error().message("用户已被停用"));
        }
        if (user.getPermissionValueList().size() == 0 || user.getPermissionValueList() == null) {
            ResponseUtil.out(response, R.error().message("用户无权限，请授权后使用"));
        }
        String token = JwtTokenUtils.builder().msg(String.valueOf(user.getId())).build().creatJwtToken();
        //权限放入缓存
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        redisUtil.set(user.getId().toString(), user.getPermissionValueList());
        List<String> o = (List<String>) redisUtil.get(user.getId().toString());
        //todo 将token和userid放入redis，测试设置两分钟，后期根据需求修改
        redisUtil.set(token, token, 60 * 60 * 2);
        redisUtil.set(token + "userId", user.getId().toString(), 60 * 60 * 2);
        Object o1 = redisUtil.get(token + "userId");
        UserDetail userDetail = user.getUserDetail();
        UserVO userVO = UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRoleNameList().get(0))
                .tel(userDetail.getTel())
                .imgUrl(userDetail.getImgUrl())
                .company(userDetail.getCompany())
                .name(userDetail.getName())
                .token(token)
                .isSuperAdministrator(user.getIsSuperAdministrator())
                .region(user.getRegion())
                .build();

        ResponseUtil.out(response, R.ok().data("data", userVO));
    }

    //3 认证失败调用的方法
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        ResponseUtil.out(response, R.error());
    }
}
