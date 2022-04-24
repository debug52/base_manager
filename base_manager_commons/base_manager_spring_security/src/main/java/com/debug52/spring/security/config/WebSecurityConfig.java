package com.debug52.spring.security.config;

import com.debug52.spring.security.filter.TokenAuthFilter;
import com.debug52.spring.security.security.TokenLogoutHandler;
import com.debug52.spring.security.security.UnauthEntryPoint;
import com.debug52.spring.security.filter.TokenLoginFilter;
import com.debug52.spring.security.security.DefaultPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DefaultPasswordEncoder defaultPasswordEncoder;
    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                             RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 配置设置
     *
     * @param http
     * @throws Exception
     */
    //设置退出的地址和token，redis操作地址
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                exceptionHandling()
                .authenticationEntryPoint(new UnauthEntryPoint())//没有权限访问
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().logout().logoutUrl("/logout")//退出路径
                .addLogoutHandler(new TokenLogoutHandler(redisTemplate)).and()
                .addFilter(new TokenLoginFilter(authenticationManager(), redisTemplate))
                .addFilter(new TokenAuthFilter(authenticationManager(), redisTemplate))
                .httpBasic();
    }

    //调用userDetailsService和密码处理
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    //不进行鉴权的访问的路径，可以直接访问
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/forgetPassWord", "/api/email/**");
    }
}

