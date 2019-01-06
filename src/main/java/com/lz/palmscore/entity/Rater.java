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
  private Integer groups;//所属组别
  private String password;//评委登录密码
  private Integer category;// 评委类型,默认1:普通评委，3:额外评委

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getGroups() {
    return groups;
  }

  public void setGroups(Integer groups) {
    this.groups = groups;
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

  public Integer getCategory() {
    return category;
  }

  public void setCategory(Integer category) {
    this.category = category;
  }

  @Override
  public String toString() {
    return "Rater{" +
            "id=" + id +
            ", rId='" + rId + '\'' +
            ", name='" + name + '\'' +
            ", workplace='" + workplace + '\'' +
            ", job='" + job + '\'' +
            ", activityId=" + activityId +
            ", groups=" + groups +
            ", password='" + password + '\'' +
            '}';
  }


}
