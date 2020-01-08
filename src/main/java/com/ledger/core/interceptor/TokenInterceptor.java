package com.ledger.core.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ledger.core.common.exception.UserActionException;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.util.redis.RedisTool;
import com.ledger.core.util.token.Token;
import com.ledger.core.util.token.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pang
 * @version V1.0
 * @ClassName: TokenInterceptor
 * @Package com.pang.mall.interceptor
 * @description: token拦截器
 * @date 2019/11/11 22:28
 */
@Component
@Order
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RedisTool redis;
    @Autowired
    private TokenUtil tokenUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("拦截到请求,requestPath={}", request.getRequestURI());
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Token token = handlerMethod.getMethodAnnotation(Token.class);
        // 如果不存在token注解，则通过认证
        if (token == null) {
            log.debug("拦截到的请求方法上不包含token注解，执行请求");
            return super.preHandle(request, response, handler);
        }
        // 如果存在token注解，则需要认证
        // 从Cookie中取出token
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.debug("无认证信息,requestPath={}", request.getRequestURI());
            throw new UserActionException("无认证信息", ResponseEnum.NOT_LOGIN);
        }
        Map<String, String> cookieMap = new HashMap<>();
        for (Cookie cookie : cookies) {
            cookieMap.put(cookie.getName(), cookie.getValue());
        }
        log.debug("将cookie保存为一个map,CookieMap={}", cookieMap);
        String tokenStr = cookieMap.get("token");
        log.debug("拦截到的请求方法上包含token，进行验证,token={}", tokenStr);
        if (tokenStr == null || tokenStr.isEmpty()) {
            log.debug("无认证信息,requestPath={}", request.getRequestURI());
            throw new UserActionException("无认证信息", ResponseEnum.NOT_LOGIN);
        }
        // 从redis中取出token
        Map<String, Object> info = null;
        try {
            info = redis.getHash(tokenStr);
        } catch (Exception e) {
            log.error("出错", e);
        }
        if (info != null && !info.isEmpty()) {
            request.setAttribute("userId", info.get("value"));
            log.debug("redis中存在token，认证通过,userId={}", info.get("value"));
            return super.preHandle(request, response, handler);
        }
        log.debug("redis中未找到token信息，尝试解析token={}", tokenStr);
        // 开始认证，如果认证出错，则直接抛出异常
        DecodedJWT decodedJWT = tokenUtil.decodedJWT(tokenStr);
        // 认证通过，将token内保存的信息添加到请求中
        request.setAttribute("userId", decodedJWT.getAudience());
        log.debug("token认证通过,userId={}", decodedJWT.getAudience());
        // 这里将token保存到redis中，提高后续性能
        redis.setHash(tokenStr, "value", decodedJWT.getAudience());
        long ttl = decodedJWT.getExpiresAt().getTime() - decodedJWT.getIssuedAt().getTime();
        redis.expire(tokenStr, ttl);
        log.debug("将token添加到redis中，token={},userId={},ttl={}", tokenStr,
                decodedJWT.getAudience(), ttl);
        // 执行正常的操作
        return super.preHandle(request, response, handler);
    }
}
