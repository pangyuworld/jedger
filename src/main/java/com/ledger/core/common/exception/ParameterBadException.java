package com.ledger.core.common.exception;

/**
 * @author pang
 * @version V1.0
 * @ClassName: ParameterBadException
 * @Package xyz.ireview.core.exception
 * @description: 参数异常错误
 * @date 2019/12/17 19:50
 */
public class ParameterBadException extends RuntimeException{
    public ParameterBadException(){
        this("参数错误");
    }
    public ParameterBadException(String message) {
        super(message);
    }
}
