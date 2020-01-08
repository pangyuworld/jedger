package com.ledger.core.beans.vo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author pang
 * @version V1.0
 * @ClassName: UserLoginForm
 * @Package com.ledger.core.beans.vo
 * @description: 用户登录用表单
 * @date 2020/1/8 18:53
 */
@Data
@ToString
@EqualsAndHashCode
public class UserLoginForm {
    /**
     * 登录名
     */
    @Pattern(regexp = "^\\w+$", message = "用户名只能包含数字、字母、下换线")
    @Length(max = 20, message = "用户网名长度限制在20个字符以内")
    @NotNull(message = "用户名不能为空")
    private String userName;
    /**
     * 登录密码
     */
    @Pattern(regexp = "^[0-9A-Za-z\\W]+$", message = "密码包含特殊字符")
    @Length(min = 6, max = 20, message = "密码长度限制在8-20个字符")
    @NotNull(message = "密码不能为空")
    private String userPassword;
}
