package com.lz.palmscore.form;

import java.util.List;

/**
 * Created by 白 on 2018/12/20.
 *
 * 打分表单
 */
public class MarkForm {
    private Integer playerId;
    private Integer raterId;
    private Double score;

    List<String> itemName;  //评分项名称
    List<Double> scoreItem; // 单个评分项分数

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getRaterId() {
        return raterId;
    }

    public void setRaterId(Integer raterId) {
        this.raterId = raterId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<Double> getScoreItem() {
        return scoreItem;
    }

    public void setScoreItem(List<Double> scoreItem) {
        this.scoreItem = scoreItem;
    }

    public List<String> getItemName() {
        return itemName;
    }

    public void setItemName(List<String> itemName) {
        this.itemName = itemName;
    }
}
