package com.lz.palmscore.form;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ScoreItemForm {
    /** 评分项**/
    @NotEmpty(message = "姓名不能为空")
    private String name;
    /** 占比**/
    @NotNull(message = "比率不能为空")
    private double rate;
    /** 备注**/
    private String note;
    /**是否上传**/
    @Value("1")
    private Integer fileUpload;

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

    public Integer getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(Integer fileUpload) {
        this.fileUpload = fileUpload;
    }
}
