package com.debug52.gateway.utils;



import com.debug52.gateway.bean.DeleteType;
import com.debug52.gateway.bean.User;
import com.debug52.gateway.bean.dao.LogDO;
import com.debug52.gateway.mapper.LogMapper;
import com.debug52.gateway.mapper.UserMapper;
import com.debug52.utils.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Date;

@Slf4j
@Component
public class AddLogUtils {
    @Autowired
    UserMapper userMapper;
    @Autowired
    LogMapper logMapper;
    @Autowired
    RedisUtil redisUtil;

    private static final String AUTHORIZE_TOKEN = "Authorization";
    private static final String USER_AGENT="User-Agent";

    private static final String FROM = "source";
    @Async("taskExecutor")
    public void addLog(ServerWebExchange exchange, ServerHttpRequest request, HttpHeaders headers) {
        String tokenKey = headers.getFirst(AUTHORIZE_TOKEN);
        String userAgent = headers.getFirst(USER_AGENT);
        ServerHttpResponse response = exchange.getResponse();
        String source = headers.getFirst(FROM);
        String userId = (String) redisUtil.get(tokenKey + "userId");
        LogDO logDO = LogDO.builder()
                .createBy(userId)
                .createDate(new Date())
                .ip(request.getRemoteAddress().getHostString())
                .isDelete(DeleteType.NOT_DELETE.getIndex())
                .uri(request.getPath().toString())
                .requestMethod(request.getMethodValue())
                .userAgent(userAgent)
                .build();
        if (userId!=null){
            User user = userMapper.selectByUserId(Integer.parseInt(userId));
            logDO.setUsername(user.getUsername());
        }
        try {
            logMapper.insert(logDO);
        } catch (Exception e) {
            log.error("日志插入异常",e);
        }
    }
}
