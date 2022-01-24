package com.enuri.gateway.filter;

import static com.enuri.gateway.filter.FilterOrderType.POST;

import java.util.Objects;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.enuri.gateway.global.Const;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
  * @description : 결과 Header에 상관관계 ID를 전달한다.
  * @Since : 2021. 6. 17.
  * @Author : AnJaeKoo
  * @History :
  */
@Slf4j
@Component
public class CorrelationTrackingPostFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders header = exchange.getRequest().getHeaders();
        exchange.getResponse().getHeaders().add(Const.CORRELATION_ID, getCorrelationId(header));

        log.info("PostFilter : {}", exchange.getRequest().getHeaders().toString());
        return chain.filter(exchange);
    }

    private String getCorrelationId(HttpHeaders header){
        return Objects.requireNonNull(header.get(Const.CORRELATION_ID)).iterator().next();
    }

    @Override
    public int getOrder() {
        return POST.getOrder();
    }
}
