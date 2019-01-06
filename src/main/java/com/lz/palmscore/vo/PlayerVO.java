package com.lz.palmscore.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by 白 on 2018/12/16.
 *
 * 微信端 评委 主页
 */
public class PlayerVO {

    private Integer id; //选手主键

    private String name;//选手姓名

    private String activityName;//活动名

    private Integer order;      // 出场顺序

    @JsonProperty("score_state")
    private Integer scoreState;//评分状态 1 已经评分,2未评分

    private Integer ifFileScore; // 教案是否已经打分

    public PlayerVO() {
    }

    public PlayerVO(Integer id, String name, String activityName, Integer order) {
        this.id = id;
        this.name = name;
        this.activityName = activityName;
        this.order = order;
    }

    public PlayerVO(Integer id, String name, String activityName, Integer order, Integer scoreState) {
        this.id = id;
        this.name = name;
        this.activityName = activityName;
        this.order = order;
        this.scoreState = scoreState;
    }

    public Integer getScoreState() {
        return scoreState;
    }

    public void setScoreState(Integer scoreState) {
        this.scoreState = scoreState;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public Integer getIfFileScore() {
        return ifFileScore;
    }

    public void setIfFileScore(Integer ifFileScore) {
        this.ifFileScore = ifFileScore;
    }
}
