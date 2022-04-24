package com.debug52.utils.response;

/**
 * @author debug52
 * @date 2022年04月12日 16:14
 */
public enum FailMsgEnum {

    BASE_FAIL(1001,"操作失败，请重试!"),
    EXIST_NAME(1002,"用户名已存在"),
    NO_ACCESS(1003,"用户无权限"),
    EXIST_TEL(1004,"该手机号已注册"),
    NOT_EXIST_TEL(1005,"该手机号未注册"),
    NOT_EXIST_USER(1009,"该用户未注册"),
    NOT_USE(1006,"该用户已经停用"),
    ERROR_PASSWORD(1007,"密码不正确"),
    NOT_PARAMETER(1008,"参数错误");
    private final Integer code;

    private final String desc;

    FailMsgEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
