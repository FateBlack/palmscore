package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.*;
import com.lz.palmscore.repository.*;
import com.lz.palmscore.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 白 on 2018/12/12.
 */

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private PlayerScoreitemRepository playerScoreitemRepository;

    @Autowired
    private RaterRepository raterRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GroupInfoRepository groupInfoRepository;

    @Autowired
    private PlayerFileRepository playerFileRepository;

    @Autowired
    private RaterScoreRepository raterScoreRepository;

    @Autowired
    ScoreItemRepository scoreItemRepository;

    @Override
    public Activity add(Activity ac) {
        Activity activity=activityRepository.save(ac);
        return activity;
    }

    @Transactional
    @Override
    public boolean allInsert(Activity activity, List<ScoreItem> scoreItemList, List<Rater> raterList, List<Player> playerList) {
        Collections.shuffle(raterList);  //随机评委
        Integer groupNum = activity.getGroupNum();

        Collections.shuffle(playerList);// 提前进行选手随机抽签
        List<List<Player>> groupList = new ArrayList<>(); //存储 各组 选手集合
        List<Player> finalPlayerList = new ArrayList<>();  // 选手随机后存储的集合

        for (int i = 0; i < groupNum; i++) {
            groupList.add(new ArrayList<>());
        }

        for (Player player : playerList) {
            int orders = groupList.get(player.getGroups()-1).size() + 1;//根据组中size+1 作为该选手顺序
            player.setOrders(orders);
            groupList.get(player.getGroups()-1).add(player);
        }

        for (int i = 0; i < groupList.size(); i++) {
            finalPlayerList.addAll(groupList.get(i));
        }

        //对评委随机分组及获取，和获取和各组的信息，注意，额外评委也加入
        Map<String,Object> map =   groupRater(raterList, groupNum);
        List<Rater> finalRaters = (List<Rater>) map.get("finalList");
        List<GroupInfo> groupInfoList = (List<GroupInfo>) map.get("groupInfoList");

        // 由于自带 JPA 的批量插入为单条循环 ，故采用
        String sqlP = "INSERT INTO player(p_id,name,workplace,course,orders,groups,activity_id)" +
                " VALUES (:pId,:name,:workplace,:course,:orders,:groups,:activityId)";

        String sqlR = "INSERT INTO rater(r_id,name,job,workplace,activity_id,groups)" +
                " VALUES (:rId,:name,:job,:workplace,:activityId,:groups)";

        String sqlS = "INSERT INTO score_item(activity_id,name,rate,note,file_upload)" +
                " VALUES (:activityId,:name,:rate,:note,:fileUpload)";

        String sqlG = "INSERT INTO group_info(group_name,rater_count)" +
                " VALUES (:groupName,:raterCount)";

        Boolean flag = false;
        try {
            SqlParameterSource[] beanSourcesP = SqlParameterSourceUtils.createBatch(finalPlayerList.toArray());
            SqlParameterSource[] beanSourcesR = SqlParameterSourceUtils.createBatch(finalRaters.toArray());
            SqlParameterSource[] beanSourcesS = SqlParameterSourceUtils.createBatch(scoreItemList.toArray());
            SqlParameterSource[] beanSourcesG = SqlParameterSourceUtils.createBatch(groupInfoList.toArray());

            namedParameterJdbcTemplate.batchUpdate(sqlP, beanSourcesP);
            namedParameterJdbcTemplate.batchUpdate(sqlR, beanSourcesR);
            namedParameterJdbcTemplate.batchUpdate(sqlS, beanSourcesS);
            namedParameterJdbcTemplate.batchUpdate(sqlG, beanSourcesG);
            activityRepository.save(activity);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 查询所有activity
     * @return
     */
    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    /**
     * 通过id删除所有活动
     * @param id
     * @return
     */
    @Transactional
    @Override
    public boolean alldelete(Integer id) {
        boolean flag=false;
    try{
        activityRepository.deleteAll();
        raterRepository.deleteAll();
        playerFileRepository.deleteAll();
        raterRepository.deleteAll();
        playerScoreitemRepository.deleteAll();
        scoreItemRepository.deleteAll();
        raterScoreRepository.deleteAll();
        playerRepository.deleteAll();
        groupInfoRepository.deleteAll();
        flag=true;
    }catch (Exception e){
    }
    return flag;
}


    /**
     * 根据输入分组  返回 随机分组后的评委集合 和 各组信息
     *
     * @return
     */
    public Map<String, Object> groupRater(List<Rater> raterList, int groupNum) {
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

        List<GroupInfo> groupInfoList = new ArrayList<>();//各组信息集合

        List<Rater> finalList = new ArrayList<>(); //评委最终集合

        for (int i = 0; i < list.size(); i++) {
            finalList.addAll(list.get(i));
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setGroupName(i + 1);
            groupInfo.setRaterCount(list.get(i).size());
            groupInfoList.add(groupInfo);
        }
        Map<String, Object> map = new HashMap();
        map.put("groupInfoList", groupInfoList); //各组信息
        map.put("finalList", finalList);//评委最终集合

        return map;
    }
}
