package com.lz.palmscore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by 白 on 2018/12/21.
 *
 * 分组信息
 */
@Entity
public class GroupInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer groupName;    //分组名称
    private Integer raterCount;  // 评委数量

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupName() {
        return groupName;
    }

    public void setGroupName(Integer groupName) {
        this.groupName = groupName;
    }

    public Integer getRaterCount() {
        return raterCount;
    }

    public void setRaterCount(Integer raterCount) {
        this.raterCount = raterCount;
    }
}
