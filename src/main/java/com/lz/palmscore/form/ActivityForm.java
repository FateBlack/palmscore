package com.lz.palmscore.form;

import javax.validation.constraints.NotEmpty;

/**
 * Created by 白 on 2018/12/12.
 */
public class ActivityForm {

    @NotEmpty(message = "活动名必填")
    private String name;//活动名

    @NotEmpty(message = "开始日期必填")
    private String startTime;//开始日期

    @NotEmpty(message = "结束日期必填")
    private String endTime;//结束日期

    @NotEmpty(message = "提交截止时间必填")
    private String uploadTime;//提交截止时间

    @NotEmpty(message = "评分细则必填")
    private String scoreRule;//评分细则

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

    public String getScoreRule() {
        return scoreRule;
    }

    public void setScoreRule(String scoreRule) {
        this.scoreRule = scoreRule;
    }
}
