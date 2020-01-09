package com.ledger.core.common.rest;

/**
 * @author pang
 * @version V1.0
 * @ClassName: ResponseEnum
 * @Package xyz.ireview.core.rest
 * @description: 返回示例
 * @date 2019/12/16 13:28
 */
public enum ResponseEnum {
    SUCCESS_OPTION(200, "操作成功", true),
    LOGIN_SUCCESS(200, "登录成功", true),
    REGISTER_SUCCESS(200, "注册成功", true),
    SYSTEM_ERROR(500, "系统异常", false),
    REPEAT_REGISTER(400, "重复注册", false),
    BAD_REQUEST(400, "参数错误", false),
    NOT_MATCH(400, "账号密码不匹配符", false),
    NOT_LOGIN(401, "没有认证信息", false),
    NOT_FOUND(404, "找不到资源", false),
    METHOD_NOT_ALLOWED(405, "Http请求方法不受支持", false),
    NOT_FOUND_SCHOOL(404, "找不到学校", false),
    FAILED_CAPTCHA(400, "验证码错误", false),
    FAILED_DELETE(400,"删除失败",false);


    ResponseEnum(Integer status, String message, Boolean success) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

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

    public Boolean getSuccess() {
        return success;
    }

    public ResponseEnum setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseEnum setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ResponseEnum setStatus(Integer status) {
        this.status = status;
        return this;
    }
}
