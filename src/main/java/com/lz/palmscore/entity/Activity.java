package com.lz.palmscore.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Activity {

  @Id
  private long id;
  private String name;//活动名
  private String startTime;//开始日期
  private String endTime;//结束日期
  private String uploadTime;//提交截止时间
  private String scoreRule;//评分细则

  public long getId() {
    return id;
  }

  public void setId(long id) {
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


  public String getScoreRule() {
    return scoreRule;
  }

  public void setScoreRule(String scoreRule) {
    this.scoreRule = scoreRule;
  }

}
