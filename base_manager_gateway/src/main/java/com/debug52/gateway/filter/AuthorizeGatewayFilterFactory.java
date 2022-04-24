package com.debug52.gateway.filter;

import com.debug52.gateway.bean.ResponseCode;
import com.debug52.gateway.service.UserService;
import com.debug52.gateway.utils.StringUtils;
import com.debug52.utils.util.RedisUtil;
import com.map.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 验证token的拦截器
 * @author debu52
 * @version 1.0
 **/
@Slf4j
@Component
    public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {
    @Autowired
    private UserService userService;
    @Autowired
    RedisUtil redisUtil;

    private static final Log logger = LogFactory.getLog(AuthorizeGatewayFilterFactory.class);

    private static final String AUTHORIZE_TOKEN = "Authorization";
    private static final String FROM = "source";

    private static final String LOGIN_PATH="/map/seed/middle/ground/system/login";
    //String username = null;

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
        logger.info("Loaded GatewayFilterFactory [Authorize]");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
        //判断是否需要拦截器认证
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                System.out.println("不需要执行拦截器");
                return chain.filter(exchange);
            }
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getPath().toString();
            System.out.println("request.getPath():"+path);
            if (path.equals(LOGIN_PATH)){
                System.out.println("登录不需要执行拦截器");
                return chain.filter(exchange);
            }
            System.out.println("AuthorizeGatewayFilterFactory开始执行拦截");

            HttpHeaders headers = request.getHeaders();
            String tokenKey = headers.getFirst(AUTHORIZE_TOKEN);
            ServerHttpResponse response = exchange.getResponse();
            System.out.println("tokenKey:"+tokenKey);
            //redis获取token
            String token = (String) redisUtil.get(tokenKey);
            //未携带token直接拦截返回
            if (StringUtils.isEmpty(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            //携带token使用JWT验证
            System.out.println(token);
            try {
                 JwtTokenUtils.builder().token(token).build().freeJwt();
            } catch (Exception e) {
                //todo token过期，但redis未失效，续期token，测试设置redis两分钟，后期按需求改
                String userId = (String) redisUtil.get(tokenKey + "userId");
                String newToken = JwtTokenUtils.builder().msg(String.valueOf(userId)).build().creatJwtToken();
                redisUtil.set(tokenKey,newToken,60*60*2);
                redisUtil.set(tokenKey + "userId",userId,60*60*2);
                Object o = redisUtil.get(tokenKey + "userId");
            }
                //拦截用户是否强制下线
            String userId = (String) redisUtil.get(tokenKey + "userId");
                if(!StringUtils.isEmpty(userId)){
                    boolean userPermission4Logout = userService.getUserPermission4Logout(Integer.parseInt(userId));
                    if(!userPermission4Logout){
                        log.info("携带token，用户角色无权限拦截器执行返回");
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return response.setComplete();
                    }
                    List<String> o = (List<String>) redisUtil.get(userId);
                    if (o!=null){
                        if (o.size()==0){
                            return buildReqsponse(response,HttpStatus.UNAUTHORIZED, ResponseCode.ILLGAL);
                        }
                    }else {
                        boolean userPermission = userService.getUserPermission(Integer.parseInt(userId));
                        if (!userPermission){
                            return buildReqsponse(response,HttpStatus.UNAUTHORIZED,ResponseCode.ILLGAL);
                        }
                    }
                }
            return chain.filter(exchange);
        };
    }
    // 设置是否开启认证
    public static class Config {

        private boolean enabled;

        public Config() { }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
    public static Mono<Void> buildReqsponse(ServerHttpResponse response, HttpStatus httpStatus, ResponseCode code) {
        response.setStatusCode(httpStatus);
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        String returnMsg = code.toString();
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(returnMsg.getBytes());
        return response.writeWith(Mono.just(bodyDataBuffer));
    }
}
