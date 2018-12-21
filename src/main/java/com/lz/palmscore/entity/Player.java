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
  private Integer orders;//出场顺序
  private Integer groups;//组别
  private Double totalScore;//总分
  private String password;//选手登录密码
  private Integer activityId;// 活动 id
  private Integer fileUpload;//是否已上传文件  1:已经 2:没有

  public Player(String pId, String name, Integer orders) {
    this.pId = pId;
    this.name = name;
    this.orders = orders;
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

  public Integer getOrders() {
    return orders;
  }

  public void setOrders(Integer orders) {
    this.orders = orders;
  }

  public Double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(Double totalScore) {
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

    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

  @Override
  public String toString() {
    return "Player{" +
            "id=" + id +
            ", pId='" + pId + '\'' +
            ", name='" + name + '\'' +
            ", workplace='" + workplace + '\'' +
            ", course='" + course + '\'' +
            ", orders=" + orders +
            ", groups=" + groups +
            ", totalScore=" + totalScore +
            ", password='" + password + '\'' +
            ", activityId=" + activityId +
            '}';
  }

  public Integer getFileUpload() {
    return fileUpload;
  }

  public void setFileUpload(Integer fileUpload) {
    this.fileUpload = fileUpload;
  }
}
