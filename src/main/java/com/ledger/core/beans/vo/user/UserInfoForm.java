package com.ledger.core.beans.vo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author pang
 * @version V1.0
 * @ClassName: UserInfoForm
 * @Package com.ledger.core.beans.vo
 * @description: 用户信息展示
 * @date 2020/1/8 18:50
 */
@Data
@ToString
@EqualsAndHashCode
public class UserInfoForm {
    public static final String MAN = "帅哥";
    public static final String WOMAN = "美女";
    /**
     * 用户真实姓名
     */
    private String userRealName;
    /**
     * 用户性别
     */
    private String userGender;

    /**
     * 用户登录时候的token值，如果没有则删除该项目
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;
}
