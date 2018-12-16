package com.lz.palmscore.vo;

/**
 * Created by 白 on 2018/12/16.
 *
 * 微信端 评委 主页
 */
public class PlayerVO {

    private Integer id; //选手主键

    private String name;//选手姓名

    private String activityName;//活动名

    private Integer score;//分数

    public PlayerVO(Integer id, String name, String activityName, Integer score) {
        this.id = id;
        this.name = name;
        this.activityName = activityName;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
