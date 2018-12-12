package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.repository.ActivityRepository;
import com.lz.palmscore.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 白 on 2018/12/12.
 */

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Activity add(Activity ac) {
        Activity activity=activityRepository.save(ac);
        return activity;
    }
}
