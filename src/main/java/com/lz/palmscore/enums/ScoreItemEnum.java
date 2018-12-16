package com.lz.palmscore.enums;

public enum ScoreItemEnum {
    SCOREITEM_ERROR(201,"评分项格式错误"),
    CREATE_ERROR(202,"评分项添加失败")

    ;
    private Integer code;
    private String message;

    ScoreItemEnum(Integer code, String message) {
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
