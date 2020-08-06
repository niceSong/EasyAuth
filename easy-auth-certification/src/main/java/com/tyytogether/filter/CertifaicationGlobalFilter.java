package com.tyytogether.filter;

import com.google.gson.Gson;
import com.tyytogether.Enums.ExceptionEnum;
import com.tyytogether.Enums.HeaderEnum;
import com.tyytogether.exception.EasyAuthException;
import com.tyytogether.jwt.EasyAuthJwtTools;
import com.tyytogether.user.UserBase;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import com.tyytogether.properties.CertificationProperties;
import reactor.core.publisher.Mono;
import com.tyytogether.repository.RedisRepository;

/**
 * 这个拦截器的功能：拦截请求头中的token，做安全处理和token解析，
 * 并将解析后的token信息放入请求头
 *
 * @author tyytogether
* */
public class CertifaicationGlobalFilter implements GlobalFilter {

    @Autowired
    private EasyAuthJwtTools easyAuthJwtTools;

    @Autowired
    private CertificationProperties certificationProperties;

    @Autowired
    private RedisRepository redisRepository;

    Gson gson = new Gson();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers= request.getHeaders();

        // 判断 header 是否含有 tokenInfo 字段。
        // 如果有，说明请求头被注入登陆信息,报错提醒，安全处理。
        if(headers.get(HeaderEnum.INNER_HEADER_TOKEN.getInfo()) != null){
            throw new EasyAuthException(ExceptionEnum.HEADER_MANUAL_INJECT_EX);
        }

        String token;
        // 判断并处理token
        try {
            token = headers.get(certificationProperties.getHeaderTokenKey()).get(0);
        }catch (Exception e){
            throw new EasyAuthException(ExceptionEnum.NO_TOKEN_KEY);
        }

        String tokenInfo;
        try {
            tokenInfo = easyAuthJwtTools.parseToken(token);
        }catch (Exception e){
            return chain.filter(exchange);
        }
        UserBase userBase = gson.fromJson(tokenInfo, UserBase.class);
        // 判断 tokenInfo 是否在redis中。
        // 如果没找到token或者token不相等，都直接放行，因为网关不进行认证，认证在authentication中进行。
        String tokenInRedis = redisRepository.get(userBase.getId());
        if(tokenInRedis == null || !tokenInRedis.equals(token) ){
            return chain.filter(exchange);
        }

        ServerHttpRequest newRequest = exchange.getRequest()
                .mutate()
                .header(HeaderEnum.INNER_HEADER_TOKEN.getInfo(), tokenInfo)
                .build();
        ServerWebExchange newExchange = exchange
                .mutate()
                .request(newRequest)
                .response(exchange.getResponse())
                .build();

        return chain.filter(newExchange);
    }
}
