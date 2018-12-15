package com.lz.palmscore.enums;

/**
 * Created by 白 on 2018/12/13.
 */
public enum PeopleEnum {
    DELETE_ERROR(401,"删除失败")
    ;

    private Integer code;
    private String message;


    PeopleEnum(Integer code, String message) {
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
