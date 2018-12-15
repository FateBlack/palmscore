package com.lz.palmscore.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by 白 on 2018/12/12.
 */
public class RaterForm implements Serializable {

    @NotEmpty(message = "评委教工号必填")
    private String rid;//评委教工号

    @NotEmpty(message = "评委姓名必填")
    private String name;//评委姓名

    @NotEmpty(message = "职务必填")
    private String job;//职务

    @NotEmpty(message = "工作单位必填")
    private String workplace;//工作单位


    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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
