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
  private Integer order;//出场顺序
  private String group;//备注
  private double totalScore;//总分
  private String password;//选手登录密码
  private Integer activityId;// 活动 id



  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public Player(String pId, String name,Integer order) {
    this.pId = pId;
    this.name = name;
    this.order = order;
  }

  public Player() {
  }

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

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
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
            ", group='" + group + '\'' +
            ", totalScore=" + totalScore +
            ", password='" + password + '\'' +
            '}';
  }


}
