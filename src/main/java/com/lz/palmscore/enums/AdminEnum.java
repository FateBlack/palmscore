package com.lz.palmscore.enums;

/**
 * Created by 白 on 2018/12/12.
 */
public enum AdminEnum {
    LOGIN_FAIL(101,"账号密码错误"),
    PARAM_ERROR(102,"格式错误")

    ;

    private Integer code;
    private String message;


    AdminEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
