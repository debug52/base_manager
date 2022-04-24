package com.debug52.gateway.bean;

public enum ResponseCode {
    //请求成功
    SUCCESS(0000, "请求成功"),
    //请求失败
    FAILED(1000, "请求失败"),
    //用户不存在
    NOT_USER(1002, "用户不存在"),
    //非法请求token
    ILLGAL(1001, "用户无权限，请授权后登录"),
    //非法签名
    ERROR_SIGN(1003, "非法签名"),
    //无访问权限
    UNAUTH(1004, "无权限");


    private Integer code;
    private String msg;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return "{\"code\":" + this.code + ",\"msg\":\"" + this.msg + "\"}";

    }
}