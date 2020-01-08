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
 * @ClassName: UserUpdateForm
 * @Package com.ledger.core.beans.vo.user
 * @description: 用户更新表单
 * @date 2020/1/8 20:20
 */
@Data
@ToString
@EqualsAndHashCode
public class UserUpdateForm {

    /**
     * 用户真实姓名
     */
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "用户网名只能包含数字、中英文、下换线")
    @Length(max = 20, message = "用户网名长度限制在20个字符以内")
    private String userRealName;
    /**
     * 用户性别
     */
    private Boolean userGender;

    /**
     * 登录密码
     */
    @Pattern(regexp = "^[0-9A-Za-z\\W]+$", message = "密码包含特殊字符")
    @Length(min = 6, max = 20, message = "密码长度限制在8-20个字符")
    private String userPassword;
}
