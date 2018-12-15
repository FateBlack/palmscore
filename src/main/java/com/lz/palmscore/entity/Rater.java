package com.lz.palmscore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rater {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  private String rId;//评委教工号
  private String name;//评委姓名
  private String workplace;//工作单位
  private String job;//职务
  private long activityId;//所属活动id


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getRId() {
    return rId;
  }

  public void setRId(String rId) {
    this.rId = rId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }


  public String getWorkplace() {
    return workplace;
  }

  public void setWorkplace(String workplace) {
    this.workplace = workplace;
  }


  public long getActivityId() {
    return activityId;
  }

  public void setActivityId(long activityId) {
    this.activityId = activityId;
  }

  @Override
  public String toString() {
    return "Rater{" +
            "id=" + id +
            ", rId='" + rId + '\'' +
            ", name='" + name + '\'' +
            ", job='" + job + '\'' +
            ", workplace='" + workplace + '\'' +
            ", activityId=" + activityId +
            '}';
  }

  public Rater(String rId, String name, String workplace, String job) {
    this.rId = rId;
    this.name = name;
    this.workplace = workplace;
    this.job = job;
  }

  public Rater() {
  }
}
