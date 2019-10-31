package com.lz.palmscore.enums;

/**
 * @author LiZhenJiang
 * @date 2019-11-01
 *
 * session
 */
public enum SessionEnum {
    ACTIVITY("activity","活动主体"),
    ACTIVITY_ID("activityId","活动id"),
    ADMIN("admin","管理员"),
    PERSON_LIST("list","评委选手通用列表"),
    RATER_LIST("raterList","评委列表"),
    EXTRA_RATER_List("extraRaterList","额外评委列表"),
    PLAYER_List("playerList","选手列表")
    ;
    private String name;
    private String desc;

    SessionEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }
}
