package com.ledger.core.service;

import com.ledger.core.beans.po.UserInfo;
import com.ledger.core.beans.vo.user.UserInfoForm;
import com.ledger.core.beans.vo.user.UserLoginForm;
import com.ledger.core.beans.vo.user.UserRegisterForm;
import com.ledger.core.beans.vo.user.UserUpdateForm;

/**
 * @author pang
 * @version V1.0
 * @ClassName: UserService
 * @Package com.ledger.core.service
 * @description: 用户操作接口
 * @date 2020/1/8 18:49
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param userRegisterForm 注册表单
     * @return 注册成功与否
     */
    Boolean register(UserRegisterForm userRegisterForm);

    /**
     * 用户登录
     *
     * @param userLoginForm 登录表单
     * @return 用户信息
     */
    UserInfoForm login(UserLoginForm userLoginForm);

    /**
     * 编辑用户信息
     *
     * @param userUpdateForm 编辑后的用户信息
     * @return 更新好的用户信息
     */
    UserInfoForm editUserInfo(Long userId, UserUpdateForm userUpdateForm);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoForm getUserInfoByUserId(Long userId);
}
