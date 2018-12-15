package com.lz.palmscore.Conventer;

import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.form.RaterForm;

/**
 * Created by 白 on 2018/12/15.
 */
public class RaterForm2RaterConverter {

    public static Rater conventer(RaterForm form) {
        Rater rater = new Rater();
        rater.setRId(form.getRid());
        rater.setName(form.getName());
        rater.setWorkplace(form.getWorkplace());
        rater.setJob(form.getJob());
        rater.setActivityId(rater.getActivityId());
        return rater;
    }
}
