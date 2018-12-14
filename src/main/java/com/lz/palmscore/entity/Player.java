package com.lz.palmscore.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  private String pId;//选手教工号
  private String name;//选手姓名
  private String workplace;//单位
  private String course;//课程
  private long order;//出场顺序
  private String note;//备注
  private double totalScore;//总分
  private String password;//选手密码
  private Integer activityId;// 活动 id


  public Integer getId() {
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

  public String getWorkplace() {
    return workplace;
  }

  public void setWorkplace(String workplace) {
    this.workplace = workplace;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public Integer getActivityId() {
    return activityId;
  }

  public void setActivityId(Integer activityId) {
    this.activityId = activityId;
  }

  @Override
  public String toString() {
    return "Player{" +
            "id=" + id +
            ", pId='" + pId + '\'' +
            ", name='" + name + '\'' +
            ", workplace='" + workplace + '\'' +
            ", course='" + course + '\'' +
            ", order=" + order +
            ", note='" + note + '\'' +
            ", totalScore=" + totalScore +
            ", password='" + password + '\'' +
            '}';
  }


}
