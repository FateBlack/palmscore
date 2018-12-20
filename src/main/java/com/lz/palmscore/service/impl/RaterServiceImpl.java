package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.RaterScore;
import com.lz.palmscore.repository.ActivityRepository;
import com.lz.palmscore.repository.RaterScoreRepository;
import com.lz.palmscore.service.RaterService;
import com.lz.palmscore.vo.PlayerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 白 on 2018/12/20.
 */
@Service
public class RaterServiceImpl implements RaterService {


    @Autowired
    RaterScoreRepository raterScoreRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * 微信端 评委id 查询出该评委下选手
     * @param id
     * @return
     */
    @Override
    public Map listPlayer(Integer id) {

        List<Activity> activityList = activityRepository.findAll();
        Activity activity = activityList.get(activityList.size() - 1);

        List<RaterScore> raterScoreList = raterScoreRepository.findAllByRaterId(id);
        List<Integer> itemIds = new ArrayList<>();
        for (RaterScore raterScore : raterScoreList) {
            itemIds.add(raterScore.getPlayerId());
        }

        String sql = "SELECT  *  FROM player WHERE id IN(:itemIds)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("itemIds", itemIds);

        List<Player> playerList =
                namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<Player>(Player.class));

        List<PlayerVO> playerVOList = new ArrayList<>();
        for (Player player : playerList) {
            PlayerVO playerVO = new PlayerVO();
            playerVO.setActivityName(activity.getName());
            playerVO.setName(player.getName());
            playerVO.setOrder(player.getOrders());
            if (player.getTotalScore() != 0) {
                playerVO.setScoreState(1);
            }else {
                playerVO.setScoreState(2);
            }
            playerVOList.add(playerVO);
        }

        Map map = new HashMap();
        map.put("list", playerVOList);
        map.put("activity_password", activity.getPassword());
        return map;
    }

}
