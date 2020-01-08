package com.ledger.core.beans.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author pang
 * @version V1.0
 * @ClassName: UserInfo
 * @Package com.ledger.core.beans.po
 * @description: 用户信息表
 * @date 2020/1/8 18:40
 */
@Data
@ToString
@EqualsAndHashCode
public class UserInfo {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 登录名
     */
    private String userName;
    /**
     * 登录密码
     */
    private String userPassword;
    /**
     * 用户真实姓名
     */
    private String userRealName;
    /**
     * 用户性别
     */
    private Boolean userGender;
}
