package com.lz.palmscore.enums;

public enum ScoreItemEnum {
    SCOREITEM_ERROR(202,"评分项创建失败")

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
