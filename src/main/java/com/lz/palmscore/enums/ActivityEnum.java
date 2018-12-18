package com.lz.palmscore.enums;

/**
 * Created by 白 on 2018/12/12.
 */
public enum ActivityEnum {
    ACTIVITY_ERROR(201,"活动创建失败"),

    ACTIVITY_ID_NOF_FOUND(207,"活动id未取得"),

    PASSWORD_NULL(205,"评委打分密码必填"),

    ACTIVITY_EMPTY(208,"活动属性为空"),

    SCORE_ITEM_EMPTY(209, "活动细则为空"),
    ACTIVITY_INSERT_ERROR(210, "活动所有内容插入数据库失败"),
    ;
    private Integer code;
    private String message;

    ActivityEnum(Integer code, String message) {
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
