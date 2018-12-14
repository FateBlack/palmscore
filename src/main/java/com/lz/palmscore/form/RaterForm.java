package com.lz.palmscore.form;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by 白 on 2018/12/12.
 */
public class RaterForm implements Serializable {

    private String rId;//评委教工号
    private String name;//评委姓名
    private String job;//职务
    private String workplace;//工作单位

    public RaterForm(String rId, String name, String job, String workplace) {
        this.rId = rId;
        this.name = name;
        this.job = job;
        this.workplace = workplace;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
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

}
