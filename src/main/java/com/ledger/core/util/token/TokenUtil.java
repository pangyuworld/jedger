package com.ledger.core.util.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ledger.core.common.exception.TokenTimeOutException;
import com.ledger.core.common.exception.UserActionException;
import com.ledger.core.common.rest.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author pang
 * @version V1.0
 * @ClassName: TokenUtil
 * @Package xyz.ireview.oauth.token
 * @description: token工具类
 * @date 2020/1/6 19:21
 */
@Component
@Slf4j
public class TokenUtil {
    @Value("${token.ttl}")
    private Long tokenTTL;

    /**
     * 生成token令牌
     *
     * @param userId 要生成令牌的用户id
     * @return token令牌字符串
     */
    public String createJWT(Long userId) {
        // 生成当前时间戳
        long nowMillis = System.currentTimeMillis();
        // 生成token的时间
        Date now = new Date(nowMillis);
        // 过期时间
        Date expiresDate = new Date(nowMillis + tokenTTL);
        log.debug("生成token令牌,userId={},now={},exp={}", userId, now, expiresDate);
        // 生成token
        String token = JWT.create()
                // 设置受众
                .withAudience(String.valueOf(userId))
                // 设置颁发时间
                .withIssuedAt(now)
                // 设置过期时间
                .withExpiresAt(expiresDate)
                // 签发，参数为签发算法和秘钥
                .sign(Algorithm.HMAC256(String.valueOf(tokenTTL)));
        return token;
    }

    /**
     * 解析token令牌
     *
     * @param token 要验证的token令牌
     * @return 验证通过的token令牌信息
     */
    public DecodedJWT decodedJWT(String token) {
        if (token == null) {
            throw new UserActionException("无认证信息", ResponseEnum.NOT_LOGIN);
        }
        log.debug("解析token令牌,token={}", token);
        DecodedJWT decode = null;
        try {
            decode = JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw e;
        }
        long expiresDate = decode.getExpiresAt().getTime();
        if (expiresDate < System.currentTimeMillis()) {
            throw new TokenTimeOutException(token);
        }
        log.debug("解析token令牌成功,token={},decodeInfo={}", token, decode);
        return decode;
    }
}
