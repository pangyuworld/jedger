package com.ledger.core.common.rest;

import java.io.Serializable;

/**
 * @author pang
 * @version V1.0
 * @ClassName: ResponseJSON
 * @Package xyz.ireview.core.rest
 * @description: 统一restful返回格式
 * @date 2019/12/16 13:23
 */
public class ResponseJSON<T> implements Serializable {
    private static final long serialVersionUID = -5748259064188564259L;
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 返回说明
     */
    private String message;
    /**
     * 返回码
     */
    private Integer status;
    /**
     * 数据
     */
    private T data;

    public ResponseJSON(T data, ResponseEnum responseInformation) {
        this(responseInformation.getSuccess(),
                data,
                responseInformation.getStatus(),
                responseInformation.getMessage());
    }


    public ResponseJSON(Boolean success, T data, Integer status, String message) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public ResponseJSON<T> setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseJSON<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ResponseJSON<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseJSON<T> setData(T data) {
        this.data = data;
        return this;
    }
}
