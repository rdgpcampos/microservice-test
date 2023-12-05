package com.gateway.demo.filters;

import java.util.List;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Server;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {
    
    public static final String CORRELATION_ID = "rdgpcampos-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
        return requestHeaderList != null? requestHeaderList.stream().findFirst().get() : null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}
