package com.lz.palmscore.enums;

/**
 * @author LiZhenJiang
 * @date 2019-11-01
 *
 * 选手类型
 */
public enum PersonTypeEnum {
    RATER(1,"评委类型"),
    PLAYER(2,"选手类型"),
    EXTRA_RATER(3,"额外评委")
    ;
    private Integer type;
    private String desc;

    PersonTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public Integer getType() {
        return type;
    }
}
