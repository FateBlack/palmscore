package com.lz.palmscore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PlayerFile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;//主键
    private Integer playerId;//选手id
    private String filePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "PlayerFile{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
