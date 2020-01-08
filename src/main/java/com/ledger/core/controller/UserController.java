package com.ledger.core.controller;

import com.ledger.core.beans.vo.user.UserInfoForm;
import com.ledger.core.beans.vo.user.UserLoginForm;
import com.ledger.core.beans.vo.user.UserRegisterForm;
import com.ledger.core.beans.vo.user.UserUpdateForm;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.common.rest.ResponseJSON;
import com.ledger.core.service.UserService;
import com.ledger.core.util.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pang
 * @version V1.0
 * @ClassName: UserController
 * @Package com.ledger.core.controller
 * @description: 用户Api
 * @date 2020/1/8 19:05
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseJSON<Boolean> register(@RequestBody @Valid UserRegisterForm userRegisterForm) {
        Boolean result = userService.register(userRegisterForm);
        log.debug("用户注册,userRegisterForm={},result={}", userRegisterForm, result);
        return new ResponseJSON<>(result, ResponseEnum.REGISTER_SUCCESS);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseJSON<UserInfoForm> login(@RequestBody @Valid UserLoginForm userLoginForm) {
        UserInfoForm userInfoForm = userService.login(userLoginForm);
        log.debug("用户登录,userLoginForm={},result={}", userLoginForm, userInfoForm != null);
        return new ResponseJSON<>(userInfoForm, ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseJSON<UserInfoForm> editUserInfo(@RequestBody @Valid UserUpdateForm userUpdateForm, @RequestAttribute Long userId) {
        UserInfoForm userInfoForm = userService.editUserInfo(userId, userUpdateForm);
        log.debug("更新用户信息,userInfoForm={}", userInfoForm);
        return new ResponseJSON<>(userInfoForm, ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(method = RequestMethod.GET)
    public ResponseJSON<UserInfoForm> getUserInfo(@RequestAttribute Long userId) {
        UserInfoForm userInfoForm = userService.getUserInfoByUserId(userId);
        log.debug("获取自己的用户信息,userInfoForm={}", userInfoForm);
        return new ResponseJSON<>(userInfoForm, ResponseEnum.SUCCESS_OPTION);
    }
}
