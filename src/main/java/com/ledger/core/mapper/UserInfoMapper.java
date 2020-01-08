package com.ledger.core.mapper;

import com.ledger.core.beans.po.UserInfo;

/**
 * @author pang
 * @version V1.0
 * @ClassName: UserInfoMapper
 * @Package com.ledger.core.mapper
 * @description: 用户数据库操作接口
 * @date 2020/1/8 18:43
 */
public interface UserInfoMapper {
    /**
     * 用户注册
     *
     * @param userInfo 用户信息
     * @return 注册成功返回1
     */
    Integer register(UserInfo userInfo);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfo getUserInfoByUserId(Long userId);

    /**
     * 根据用户名获取用户登录信息
     *
     * @param userName 用户名
     * @return 用户登录信息
     */
    UserInfo getUserInfoByUserName(String userName);

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return 不报错就返回1
     */
    Integer editUserInfo(UserInfo userInfo);
}
