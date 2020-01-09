package com.ledger.core.common.handler;

import com.ledger.core.common.exception.ParameterBadException;
import com.ledger.core.common.exception.TokenTimeOutException;
import com.ledger.core.common.exception.UserActionException;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.common.rest.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pang
 * @version V1.0
 * @ClassName: ExceptionHandler
 * @Package xyz.ireview.core.handler
 * @description: 全局统一异常处理
 * @date 2019/12/17 16:55
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserActionException.class)
    public ResponseJSON<String> userActionHandler(UserActionException e, HttpServletResponse response) {
        log.warn("捕获到用户操作异常，exception={}", e.getExceptionType(), e);
        switch (e.getExceptionType()) {
            case NOT_FOUND:
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return new ResponseJSON<>(e.getMessage(), e.getExceptionType());
            default:
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return new ResponseJSON<>(e.getMessage(), e.getExceptionType());
        }
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseJSON<String> parameterBadHandler(ConstraintViolationException e, HttpServletResponse response) {
        log.warn("捕获到参数异常，exception={}", e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseJSON<>(e.getMessage(), ResponseEnum.BAD_REQUEST);
    }

    @ExceptionHandler(value = ParameterBadException.class)
    public ResponseJSON<String> parameterBadHandler(ParameterBadException e, HttpServletResponse response) {
        log.warn("捕获到参数异常，exception={}", e.getMessage(), e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseJSON<>(e.getMessage(), ResponseEnum.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseJSON<String> parameterBadHandler(MethodArgumentNotValidException e, HttpServletResponse response) {
        //按需重新封装需要返回的错误信息
        List<Map<String, String>> errMsgList = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            Map<String, String> errMsg = new HashMap<>(4);
            errMsg.put("errMsg", error.getDefaultMessage());
            errMsg.put("errField", error.getField());
            errMsgList.add(errMsg);
        }
        log.warn("捕获到参数异常，exception={}", errMsgList, e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseJSON<>(errMsgList.toString(), ResponseEnum.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseJSON<String> httpRequestMethodNotSupportedHandler(HttpRequestMethodNotSupportedException e, HttpServletResponse response) {
        log.warn("请求方法异常，exception={}", e.getMessage(), e);
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        return new ResponseJSON<>("不支持的请求方法", ResponseEnum.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = TokenTimeOutException.class)
    public ResponseJSON<String> tokenTimeOutHandler(TokenTimeOutException e, HttpServletResponse response) {
        log.warn("token过期，请重新登录,param={}", e.getToken(), e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseJSON<>("token过期，请重新登录", ResponseEnum.NOT_LOGIN);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseJSON<String> otherExceptionsHandler(Exception e, HttpServletResponse response) {
        log.warn("发生其他异常,异常类型={}", e.getClass(), e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseJSON<>("系统发生未知异常，请联系网站管理员", ResponseEnum.SYSTEM_ERROR);
    }
}
