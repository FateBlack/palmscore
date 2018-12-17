package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.entity.ScoreItem;
import com.lz.palmscore.repository.ActivityRepository;
import com.lz.palmscore.repository.PlayerRepository;
import com.lz.palmscore.repository.RaterRepository;
import com.lz.palmscore.repository.ScoreItemRepository;
import com.lz.palmscore.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 白 on 2018/12/12.
 */

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ScoreItemRepository scoreItemRepository;

    @Autowired
    private RaterRepository raterRepository;

    @Autowired
    private PlayerRepository playerRepository;


    @Override
    public Activity add(Activity ac) {
        Activity activity=activityRepository.save(ac);
        return activity;
    }

    @Override
    public boolean allInsert(Activity activity, List<ScoreItem> scoreItemList, List<Rater> raterList, List<Player> playerList) {

        //Collections.shuffle(raterList);

        List<List<Rater>> list = new ArrayList<>();

        //Integer groupNum = activity.getGroupNum();

        //TODO 等待删除
        int groupNum = 5;

        groupRater(raterList, groupNum);

        return false;
    }

    /**
     * 根据输入分组
     * @return
     */
    public List<List<Rater>> groupRater(List<Rater> raterList, int groupNum) {


        List<List<Rater>> list = new ArrayList<>();

        int length = raterList.size();
        if (length <= groupNum) {
            groupNum = length;
        }

        int extra = length % groupNum;
        int count = length / groupNum;


        for (int i = 1; i <= groupNum; i++) {
            List<Rater> sonList = new ArrayList<>();

            for (int j = 1; j <= count; j++) {
                int s = count * (i - 1) + j - 1;
                Rater rater = raterList.get(s);
                rater.setGroup(i);
                sonList.add(rater);
            }
            list.add(sonList);
        }

        if (extra != 0) {
            for (int i = extra; i > 0; i--) {
                Rater rater = raterList.get(length - i);
                rater.setGroup(groupNum);
                list.get(groupNum - 1).add(rater);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println("搞点"+list.get(i));
        }

        return list;
    }


}
