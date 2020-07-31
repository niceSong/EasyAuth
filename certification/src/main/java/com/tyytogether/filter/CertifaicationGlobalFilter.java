package com.tyytogether.filter;

import com.tyytogether.Enums.ExceptionEnum;
import com.tyytogether.Enums.HeaderEnum;
import com.tyytogether.exception.EasyAuthException;
import com.tyytogether.jwt.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import com.tyytogether.properties.CertificationProperties;
import reactor.core.publisher.Mono;
import com.tyytogether.repository.RedisRepository;

/**
 * 这个拦截器的功能：拦截请求头中的token，做安全处理和token解析，
 * 并将解析后的token信息放入请求头
 *
 *  @author tyytogether
* */
public class CertifaicationGlobalFilter implements GlobalFilter {

    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private CertificationProperties certificationProperties;

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers= request.getHeaders();

        // 判断 header 是否含有 tokenInfo 字段。
        // 如果有，说明请求头被注入登陆信息,报错提醒，安全处理。
        if(headers.get(HeaderEnum.INNER_HEADER_TOKEN.getInfo()) != null){
            throw new EasyAuthException(ExceptionEnum.HEADER_MANUAL_INJECT_EX);
        }

        // 判断并处理token
        String token = headers.get(certificationProperties.getHeaderTokenKey()).get(0);
        if(StringUtils.isEmpty(token)){
            throw new EasyAuthException(ExceptionEnum.NO_TOKEN_KEY);
        }else {
            String tokenInfo = jwtTools.parseToken(token);
            // 判断 tokenInfo 是否存在redis中。因为token有超时时间
            String tokenInRedis = redisRepository.get(tokenInfo);
            if(tokenInRedis == null || tokenInRedis != token){
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
}
