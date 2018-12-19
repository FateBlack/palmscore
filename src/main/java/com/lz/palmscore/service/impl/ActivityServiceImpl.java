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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Activity add(Activity ac) {
        Activity activity=activityRepository.save(ac);
        return activity;
    }

    @Transactional
    @Override
    public boolean allInsert(Activity activity, List<ScoreItem> scoreItemList, List<Rater> raterList, List<Player> playerList) {

        Collections.shuffle(raterList);

        List<List<Rater>> list = new ArrayList<>();

        Integer groupNum = activity.getGroupNum();
        //TODO 等待删除
//        int groupNum = 5;

        List<Rater> finalRaters =   groupRater(raterList, groupNum);


        String sqlP = "INSERT INTO player(p_id,name,workplace,course,orders,groups,activity_id)" +
                " VALUES (:pId,:name,:workplace,:course,:orders,:groups,:activityId)";

        String sqlR = "INSERT INTO rater(r_id,name,job,workplace,activity_id,groups)" +
                " VALUES (:rId,:name,:job,:workplace,:activityId,:groups)";

        String sqlS = "INSERT INTO score_item(activity_id,name,rate,note,file_upload)" +
                " VALUES (:activityId,:name,:rate,:note,:fileUpload)";

        Boolean flag = false;
        try {
            SqlParameterSource[] beanSourcesP = SqlParameterSourceUtils.createBatch(playerList.toArray());
            SqlParameterSource[] beanSourcesR = SqlParameterSourceUtils.createBatch(raterList.toArray());
            SqlParameterSource[] beanSourcesS = SqlParameterSourceUtils.createBatch(scoreItemList.toArray());

            namedParameterJdbcTemplate.batchUpdate(sqlP, beanSourcesP);
            namedParameterJdbcTemplate.batchUpdate(sqlR, beanSourcesR);
            namedParameterJdbcTemplate.batchUpdate(sqlS, beanSourcesS);
            activityRepository.save(activity);

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }



        return flag;
    }



    /**
     * 根据输入分组
     * @return
     */
    public List<Rater> groupRater(List<Rater> raterList, int groupNum) {


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
                rater.setGroups(i);
                sonList.add(rater);
            }
            list.add(sonList);
        }


        List<Rater> extraList = new ArrayList<>();
        if (extra != 0) {
            for (int i = extra; i > 0; i--) {
                Rater rater = raterList.get(length - i);
                extraList.add(rater);
            }
        }

        for (int i = 0; i < extra; i++) {
            extraList.get(i).setGroups(i + 1);
            list.get(i).add(extraList.get(i));
        }


        List<Rater> finalList = new ArrayList<>();

//        for (int i = 0; i < list.size(); i++) {
//            System.out.println("搞点"+list.get(i));
//            finalList.addAll(list.get(i));
//        }
//
//
//        System.out.println("最终" + finalList);

        return finalList;
    }


}
