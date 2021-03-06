package com.lz.palmscore.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Created by 白 on 2018/12/15.
 */
public class PlayerForm {
    @NotEmpty(message = "选手教工号必填")
    private String pid;//选手教工号

    @NotEmpty(message = "选手姓名必填")
    private String name;//选手姓名

    @NotEmpty(message = "单位必填")
    private String workplace;//单位

    @NotEmpty(message = "课程必填")
    private String course;//课程

    private Integer orders;//出场顺序

    private Integer groups;//所属组


    /**
     * 测试使用
     */


    /**
     * 测试使用
     */
    public PlayerForm() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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


    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }
}
