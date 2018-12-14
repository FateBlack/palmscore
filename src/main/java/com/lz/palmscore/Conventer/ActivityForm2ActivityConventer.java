package com.lz.palmscore.Conventer;

import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.form.ActivityForm;

/**
 * Created by 白 on 2018/12/12.
 */

/**
 *  表单内容转 Activity
 */
public class ActivityForm2ActivityConventer {

    public static Activity conventer(ActivityForm activityForm) {

        Activity activity = new Activity();

        activity.setName(activityForm.getName());
        activity.setStartTime(activityForm.getStartTime());
        activity.setEndTime(activityForm.getEndTime());

        activity.setScoreRule(activityForm.getScoreRule());
        activity.setUploadTime(activityForm.getUploadTime());
        activity.setFileUpload(activityForm.getFileUpload());
        return activity;
    }

}
