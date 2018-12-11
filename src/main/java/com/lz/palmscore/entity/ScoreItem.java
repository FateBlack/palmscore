package com.lz.palmscore.entity;


import javax.persistence.Id;

public class ScoreItem {

  @Id
  private Integer id;
  private long activityId;//活动id
  private String name;//评分项
  private double rate;//占比
  private String note;//备注
  public long getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public long getActivityId() {
    return activityId;
  }

  public void setActivityId(long activityId) {
    this.activityId = activityId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

}
