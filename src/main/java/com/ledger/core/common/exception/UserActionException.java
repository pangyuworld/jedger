package com.ledger.core.common.exception;


import com.ledger.core.common.rest.ResponseEnum;

/**
 * @author pang
 * @version V1.0
 * @ClassName: NotFountException
 * @Package xyz.ireview.core.exception
 * @description: 用户操作异常
 * @date 2019/12/17 16:49
 */
public class UserActionException extends RuntimeException {
    /**
     * 异常类型
     */
    private ResponseEnum exceptionType;

    /**
     * 默认构造，系统异常
     */
    public UserActionException() {
        this("用户操作异常。", ResponseEnum.SYSTEM_ERROR);
    }

    /**
     * 带参数构造异常
     *
     * @param exceptionType 异常类型
     */
    public UserActionException(ResponseEnum exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    /**
     * 自定义异常信息的带参数构造
     *
     * @param message       异常信息
     * @param exceptionType 异常类型
     */
    public UserActionException(String message, ResponseEnum exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public ResponseEnum getExceptionType() {
        return exceptionType;
    }
}
