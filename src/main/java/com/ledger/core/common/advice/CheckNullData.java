package com.ledger.core.common.advice;

import com.ledger.core.common.exception.UserActionException;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.common.rest.ResponseJSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: CheckNullData
 * @Package xyz.ireview.core.advice
 * @description: 检查响应包中的数据是否为空
 * @date 2019/12/17 16:48
 */
@ControllerAdvice
public class CheckNullData implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof ResponseJSON) {
            ResponseJSON responseJSON = (ResponseJSON) body;
            // 如果是链表
            if (responseJSON.getData() instanceof List) {
                if (((List) responseJSON.getData()).isEmpty()) {
                    throw new UserActionException(ResponseEnum.NOT_FOUND);
                }
            }
            // 如果是实体
            if (responseJSON.getData() == null || responseJSON.getData().toString().equals("null")) {
                throw new UserActionException(ResponseEnum.NOT_FOUND);
            }
        }
        return body;
    }
}
