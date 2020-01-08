package com.ledger.core.util.password;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author pang
 * @version V1.0
 * @ClassName: PasswordUtil
 * @Package xyz.ireview.oauth.password
 * @description: 密码加密解密工具
 * @date 2020/1/6 13:31
 */
@Component
@Slf4j
public class PasswordUtil {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 加密
     *
     * @param password 要加密的密码
     * @return 加密后的密码
     */
    public String encode(String password) {
        String encodedPassword = ENCODER.encode(password);
        log.debug("加密密码,password={},encodedPassword={}", password, encodedPassword);
        return encodedPassword;
    }

    /**
     * 验证匹配
     *
     * @param rawPassword     原始密码（用户传入）
     * @param encodedPassword 加密后的密码（存储在数据库中）
     * @return 是否匹配
     */
    public Boolean match(String rawPassword, String encodedPassword) {
        boolean isMatch = ENCODER.matches(rawPassword, encodedPassword);
        log.debug("验证密码是否匹配,password={},encodedPassword={},isMatch={}", rawPassword, encodedPassword, isMatch);
        return isMatch;
    }

}
