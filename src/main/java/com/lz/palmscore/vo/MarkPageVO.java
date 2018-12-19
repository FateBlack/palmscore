package com.lz.palmscore.vo;

/**
 * Created by 白 on 2018/12/19.
 * 评委打分页面
 */
public class MarkPageVO {
    private String name;//评分项
    private double rate;//占比

    public MarkPageVO(String name, double rate) {
        this.name = name;
        this.rate = rate;
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
}
