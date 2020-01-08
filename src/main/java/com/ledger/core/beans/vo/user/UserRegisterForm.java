package com.ledger.core.beans.vo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author pang
 * @version V1.0
 * @ClassName: UserRegisterForm
 * @Package com.ledger.core.beans.vo
 * @description: 用户注册用表单
 * @date 2020/1/8 18:52
 */
@Data
@ToString
@EqualsAndHashCode
public class UserRegisterForm {

    /**
     * 登录名
     */
    @Pattern(regexp = "^\\w+$", message = "用户名只能包含数字、字母、下换线")
    @Length(min = 6, max = 20, message = "用户名长度限制在6-20个字符")
    @NotNull(message = "用户名不能为空")
    private String userName;
    /**
     * 登录密码
     */
    @Pattern(regexp = "^[0-9A-Za-z\\W]+$", message = "密码包含特殊字符")
    @Length(min = 6, max = 20, message = "密码长度限制在8-20个字符")
    @NotNull(message = "密码不能为空")
    private String userPassword;
    /**
     * 用户真实姓名
     */
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "用户网名只能包含数字、中英文、下换线")
    @Length(max = 20, message = "用户网名长度限制在20个字符以内")
    @NotNull(message = "用户名不能为空")
    @NotEmpty
    private String userRealName;
    /**
     * 用户性别
     */
    @NotNull(message = "用性别不能为空")
    private Boolean userGender;
}
