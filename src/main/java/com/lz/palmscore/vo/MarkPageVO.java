package com.lz.palmscore.vo;

/**
 * Created by 白 on 2018/12/19.
 * 评委打分页面
 */
public class MarkPageVO {
    private Integer id;//评分项主键
    private String name;//评分项
    private double rate;//占比
    private Integer fileUpload;//是否需要文件上传 1:需要  2:不需要
    private Double score; //分数


    public MarkPageVO(Integer id, String name, double rate, Integer fileUpload) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.fileUpload = fileUpload;
    }

    public Integer getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(Integer fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
