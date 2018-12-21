package com.lz.palmscore.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ScoreItem {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  private long activityId;//活动id
  private String name;//评分项
  private Double rate;//占比
  private String note;//备注
  private Integer fileUpload;//是否需要上传文件

  public Integer getId() {

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


  public Double getRate() {
    return rate;
  }

  public void setRate(Double rate) {
    this.rate = rate;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

    public Integer getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(Integer fileUpload) {
        this.fileUpload = fileUpload;
    }

  @Override
  public String toString() {
    return "ScoreItem{" +
            "id=" + id +
            ", activityId=" + activityId +
            ", name='" + name + '\'' +
            ", rate=" + rate +
            ", note='" + note + '\'' +
            ", fileUpload=" + fileUpload +
            '}';
  }
}
