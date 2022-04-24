package com.debug52.gateway.filter;

import com.debug52.gateway.utils.AddLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {
    @Autowired
    AddLogUtils addLogUtils;
    private static final Log logger = LogFactory.getLog(LogGatewayFilterFactory.class);
    public LogGatewayFilterFactory() {
        super(LogGatewayFilterFactory.Config.class);
        logger.info("Loaded GatewayFilterFactory [Authorize]");
    }
    @Override
    public GatewayFilter apply(LogGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                System.out.println("不需要执行拦截器");
                return chain.filter(exchange);
            }
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            addLogUtils.addLog(exchange, request, headers);
            return chain.filter(exchange);
        };
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }



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
}
