package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.*;
import com.lz.palmscore.form.MarkForm;
import com.lz.palmscore.form.MarkItem;
import com.lz.palmscore.repository.ActivityRepository;
import com.lz.palmscore.repository.PlayerScoreitemRepository;
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

    @Autowired
    private PlayerScoreitemRepository playerScoreitemRepository;
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
            playerVO.setId(player.getId());
            playerVO.setActivityName(activity.getName());
            playerVO.setName(player.getName());
            playerVO.setOrder(player.getOrders());

            playerVOList.add(playerVO);
        }

        Map map = new HashMap();
        map.put("list", playerVOList);
        map.put("activity_password", activity.getPassword());
        return map;
    }

    @Override
    public void mark(MarkForm markForm) {

        Integer playerId = markForm.getPlayerId();

        RaterScore raterScore = new RaterScore();
        raterScore.setPlayerId(playerId);
        raterScore.setRaterId(markForm.getRaterId());
        raterScore.setScore(markForm.getScore());


        raterScoreRepository.save(raterScore);

        for (MarkItem markItem : markForm.getMarkItems()) {
            PlayerScoreitem ps = new PlayerScoreitem();
            ps.setPlayerId(playerId);
            ps.setItemName(markItem.getName());
            ps.setScore(markItem.getScore());

            playerScoreitemRepository.save(ps);
        }
    }

}
