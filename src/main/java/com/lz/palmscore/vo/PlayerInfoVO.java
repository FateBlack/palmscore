package com.lz.palmscore.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by 白 on 2018/12/18.
 */
public class PlayerInfoVO {

    private Integer id; //选手主键
    private String name;//选手姓名
    private String workplace;//单位
    private String course;//课程
    private Integer state;//是否截至上传
    private String job;


    @JsonProperty("file_list")
    private List<String> fileList; //文件集合

    public PlayerInfoVO(Integer id, String name, String workplace, String course, Integer state,String job) {
        this.id = id;
        this.name = name;
        this.workplace = workplace;
        this.course = course;
        this.state = state;
        this.job=job;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
