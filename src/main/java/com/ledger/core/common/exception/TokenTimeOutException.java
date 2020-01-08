package com.ledger.core.common.exception;

/**
 * @author pang
 * @version V1.0
 * @ClassName: TokenTimeOutException
 * @Package com.pang.mall.utils.token
 * @description: token过期异常
 * @date 2019/11/11 11:46
 */
public class TokenTimeOutException extends RuntimeException {
    private String token;

    public TokenTimeOutException(String token) {
        this("token过期，请重新登录", token);
    }

    public TokenTimeOutException(String message, String token) {
        super(message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
