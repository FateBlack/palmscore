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
    private double score;

    List<MarkItem> markItems;

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<MarkItem> getMarkItems() {
        return markItems;
    }

    public void setMarkItems(List<MarkItem> markItems) {
        this.markItems = markItems;
    }
}
