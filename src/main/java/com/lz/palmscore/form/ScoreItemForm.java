package com.lz.palmscore.form;

import javax.validation.constraints.NotEmpty;

public class ScoreItemForm {
    /** 评分项**/
    @NotEmpty(message = "姓名不能为空")
    private String name;
    /** 占比**/
    @NotEmpty(message = "比率不能为空")
    private double rate;

    private String note;

    public String getName() {
        return name;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
