package com.ledger.core.service.impl;

import com.ledger.core.beans.po.UserInfo;
import com.ledger.core.beans.vo.user.UserInfoForm;
import com.ledger.core.beans.vo.user.UserLoginForm;
import com.ledger.core.beans.vo.user.UserRegisterForm;
import com.ledger.core.beans.vo.user.UserUpdateForm;
import com.ledger.core.common.exception.UserActionException;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.mapper.UserInfoMapper;
import com.ledger.core.service.UserService;
import com.ledger.core.util.password.PasswordUtil;
import com.ledger.core.util.token.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author pang
 * @version V1.0
 * @ClassName: UserServiceImpl
 * @Package com.ledger.core.service.impl
 * @description:
 * @date 2020/1/8 18:55
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private TokenUtil tokenUtil;


    /**
     * 用户注册
     *
     * @param userRegisterForm 注册表单
     * @return 注册成功与否
     */
    @Override
    public Boolean register(UserRegisterForm userRegisterForm) {
        // 加密
        log.debug("对用户密码进行加密,userRegisterForm={}", userRegisterForm);
        userRegisterForm.setUserPassword(passwordUtil.encode(userRegisterForm.getUserPassword()));
        // 将VO转为PO
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userRegisterForm.getUserName());
        userInfo.setUserGender(userRegisterForm.getUserGender());
        userInfo.setUserPassword(userRegisterForm.getUserPassword());
        userInfo.setUserRealName(userRegisterForm.getUserRealName());
        // 插入数据库
        boolean result = false;
        try {
            result = userInfoMapper.register(userInfo) > 0;
        } catch (DuplicateKeyException e) {
            log.info("重复注册,userRegisterForm={}", userRegisterForm, e);
            throw new UserActionException(ResponseEnum.REPEAT_REGISTER);
        }
        log.debug("向数据库插入新的用户,userInfo={},result={}", userInfo, result);
        return result;
    }

    /**
     * 用户登录
     *
     * @param userLoginForm 登录表单
     * @return 用户信息
     */
    @Override
    public UserInfoForm login(UserLoginForm userLoginForm) {
        // 查用户
        UserInfo userInfo = userInfoMapper.getUserInfoByUserName(userLoginForm.getUserName());
        log.debug("从数据库查找用户,userInfo={}", userInfo);
        if (userInfo == null) {
            log.info("找不到用户,userLoginForm={}", userLoginForm);
            throw new UserActionException(ResponseEnum.NOT_MATCH);
        }
        // 找到用户,比对密码
        Boolean isMatch = passwordUtil.match(userLoginForm.getUserPassword(), userInfo.getUserPassword());
        log.debug("验证账号密码是否有效,userLoginForm={},isMatch={}", userLoginForm, isMatch);
        if (!isMatch) {
            log.info("账号密码不匹配,userLoginForm={}", userLoginForm);
            throw new UserActionException(ResponseEnum.NOT_MATCH);
        }
        // 验证通过，生成token
        String token = tokenUtil.createJWT(userInfo.getUserId());
        // 组装返回表单
        UserInfoForm userInfoForm = new UserInfoForm();
        userInfoForm.setUserGender(userInfo.getUserGender() ? UserInfoForm.MAN : UserInfoForm.WOMAN);
        userInfoForm.setUserRealName(userInfo.getUserRealName());
        userInfoForm.setUserName(userInfo.getUserName());
        userInfoForm.setToken(token);
        log.debug("登录成功,userLoginForm={}", userLoginForm);
        return userInfoForm;
    }

    /**
     * 编辑用户信息
     *
     * @param userUpdateForm 编辑后的用户信息
     * @return 更新好的用户信息
     */
    @Override
    public UserInfoForm editUserInfo(Long userId, UserUpdateForm userUpdateForm) {
        // 检查有没有密码，有密码就进行加密
        if (userUpdateForm.getUserPassword() != null) {
            userUpdateForm.setUserPassword(passwordUtil.encode(userUpdateForm.getUserPassword()));
        }
        // 将VO转换为PO
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserRealName(userUpdateForm.getUserRealName());
        userInfo.setUserGender(userUpdateForm.getUserGender());
        Boolean result = userInfoMapper.editUserInfo(userInfo) > 0;
        log.debug("更新用户信息,userInfo={},result={}", userInfo, result);
        // 将PO转换为VO
        userInfo = userInfoMapper.getUserInfoByUserId(userId);
        UserInfoForm userInfoForm = new UserInfoForm();
        userInfoForm.setUserGender(userInfo.getUserGender() ? UserInfoForm.MAN : UserInfoForm.WOMAN);
        userInfoForm.setUserRealName(userInfo.getUserRealName());
        userInfoForm.setUserName(userInfo.getUserName());
        log.debug("得到更新后的用户信息,userInfoForm={}", userInfoForm);
        return userInfoForm;
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserInfoForm getUserInfoByUserId(Long userId) {
        UserInfo userInfo = userInfoMapper.getUserInfoByUserId(userId);
        log.debug("获取单个用户信息,userInfo={}", userInfo);
        UserInfoForm userInfoForm = new UserInfoForm();
        userInfoForm.setUserGender(userInfo.getUserGender() ? UserInfoForm.MAN : UserInfoForm.WOMAN);
        userInfoForm.setUserRealName(userInfo.getUserRealName());
        userInfoForm.setUserName(userInfo.getUserName());
        return userInfoForm;
    }
}
