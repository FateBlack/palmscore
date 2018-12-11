package com.lz.palmscore.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
//选手
public class Player {

  @Id
  private long id;
  private String pId;//选手教工号
  private String name;//选手姓名
  private String job;//职务
  private String course;//课程
  private long order;//出场顺序
  private String note;//备注
  private double totalScore;//总分


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getPId() {
    return pId;
  }

  public void setPId(String pId) {
    this.pId = pId;
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


  public String getCourse() {
    return course;
  }

  public void setCourse(String course) {
    this.course = course;
  }


  public long getOrder() {
    return order;
  }

  public void setOrder(long order) {
    this.order = order;
  }


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }


  public double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(double totalScore) {
    this.totalScore = totalScore;
  }

}
