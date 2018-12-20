package com.lz.palmscore.entity;

import javax.persistence.Entity;

@Entity
public class RaterScore {
    private int i;
    private int raterId;
    private int playerId;
    private Double score;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getRaterId() {
        return raterId;
    }

    public void setRaterId(int raterId) {
        this.raterId = raterId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
