package com.lz.palmscore.enums;

/**
 * Created by 白 on 2018/12/13.
 */
public enum PeopleEnum {
    DELETE_ERROR(501,"删除失败"),
    PARAM_ERROR(502,"提交参数有误"),
    PLAYERS_EMPTY(503, "选手列表为空"),
    RATERS_EMPTY(504, "评委列表为空"),
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
