package com.lz.palmscore.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lz.palmscore.entity.Player;

import java.util.List;

/**
 * Created by 白 on 2018/12/16.
 *
 * 微信端 选手主页
 *
 */
public class AcitvityVO {

    private Integer id;

    private String name;//活动名

    private String startTime;//开始日期

    private String endTime;//结束日期

    private String uploadTime;//提交截止时间

    private Integer state;

    public AcitvityVO() {
    }

    public AcitvityVO(Integer id, String name, String startTime, String endTime, String uploadTime, Integer state) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.uploadTime = uploadTime;
        this.state = state;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
