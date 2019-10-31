package com.lz.palmscore.enums;

/**
 * @author LiZhenJiang
 * @date 2019-11-01
 */
public enum MarkTypeEnum {
    MARK(1,"已评分"),
    NOT_MARK(2,"未评分")
    ;
    private Integer type;
    private String desc;

    MarkTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }
}
