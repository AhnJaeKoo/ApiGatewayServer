package com.enuri.gateway.filter;

import static com.enuri.gateway.filter.FilterOrderType.PRE;
import static java.lang.String.format;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.enuri.gateway.global.Const;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
  * @description : request에 상관관계 ID를 생성하여 삽입한다.
  * @Since : 2021. 6. 17.
  * @Author : AnJaeKoo
  * @History :
  */
@Slf4j
@Component
public class CorrelationTrackingPreFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders header = request.getHeaders();

		if (!hasCorrelationId(header)) {
            request = exchange.getRequest()
                    .mutate()
                    .header(Const.CORRELATION_ID, generateCorrelationId())
                    .build();
            log.info("PreFilter : {}", header.toString());
            return chain.filter(exchange.mutate().request(request).build());
        }

		log.info("PreFilter : {}", exchange.getRequest().getHeaders().toString());
        return chain.filter(exchange);
    }

    private boolean hasCorrelationId(HttpHeaders header) {
        return header.containsKey(Const.CORRELATION_ID);
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

	@Override
	public int getOrder() {
	    return PRE.getOrder();
	}
}