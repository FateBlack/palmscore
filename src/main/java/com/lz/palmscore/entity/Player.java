package com.lz.palmscore.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Player {

  @Id
  private Integer id;
  private String pId;
  private String name;
  private String job;
  private String course;
  private long order;
  private String note;
  private double totalScore;


  public long getId() {
    return id;
  }

  public void setId(Integer id) {
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
