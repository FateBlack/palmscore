package com.lz.palmscore.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {

  @Id
  private Integer id;
  private String account;//账户
  private String password;//密码


  public long getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
