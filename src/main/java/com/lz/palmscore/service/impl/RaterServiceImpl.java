package com.lz.palmscore.service.impl;

import com.lz.palmscore.dao.RaterScoreDao;
import com.lz.palmscore.entity.*;
import com.lz.palmscore.form.MarkForm;
import com.lz.palmscore.form.MarkItem;
import com.lz.palmscore.form.MarkOneForm;
import com.lz.palmscore.repository.*;
import com.lz.palmscore.service.RaterService;
import com.lz.palmscore.vo.PlayerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    PlayerRepository playerRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private GroupInfoRepository groupInfoRepository;

    @Autowired
    private  PlayerScoreitemRepository playerScoreitemRepository;

    @Autowired
    private RaterScoreDao raterScoreDao;

    @Autowired
    private PlayerScoreitemRepository playerScoreitemRepository;
    /**
     * 微信端 评委id 查询出该评委下已打分选手
     * @param id
     * @return
     */
    @Override
    public Map listPlayer(Integer id) {

        Map map = new HashMap();


        List<Activity> activityList = activityRepository.findAll();
        Activity activity = activityList.get(activityList.size() - 1);

        map.put("activity_password", activity.getPassword());

        List<RaterScore> raterScoreList = raterScoreRepository.findByRaterId(id);

        if (raterScoreList.size() == 0) {
            return map;
        }

        List<Integer> itemIds = new ArrayList<>();
        for (RaterScore raterScore : raterScoreList) {
            itemIds.add(raterScore.getPlayerId());
        }


        List<Player> playerList = null;
        List<PlayerVO> playerVOList = null;

        String sql = "SELECT  *  FROM player WHERE id IN(:itemIds)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("itemIds", itemIds);

        playerList =
                namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<Player>(Player.class));


        playerVOList = new ArrayList<>();
        for (Player player : playerList) {
            PlayerVO playerVO = new PlayerVO();
            playerVO.setId(player.getId());
            playerVO.setActivityName(activity.getName());
            playerVO.setName(player.getName());
            playerVO.setOrder(player.getOrders());

            playerVOList.add(playerVO);
        }

        map.put("list", playerVOList);

        return map;
    }

    /**
     * 打分  插入 rater_score 和 批量插入player_scoreitem
     * 并查询 rater_score中打分数量 对比 group_info中评委数，判断是否计算总分
     * @param markForm
     */
    @Override
    @Transactional
    public void mark(MarkForm markForm,Integer groups) {

        Integer playerId = markForm.getPlayerId();
        Integer raterId = markForm.getRaterId();

        RaterScore raterScore = new RaterScore();
        raterScore.setPlayerId(playerId);
        raterScore.setRaterId(raterId);
        raterScore.setScore(markForm.getScore());

        raterScoreRepository.save(raterScore);


        Integer length = markForm.getScoreItem().size();

        List<PlayerScoreitem> playerScoreitemList = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            PlayerScoreitem ps = new PlayerScoreitem();
            ps.setRaterId(raterId);
            ps.setPlayerId(playerId);
            ps.setItemName(markForm.getItemName().get(i));
            ps.setScore(markForm.getScoreItem().get(i));

            playerScoreitemList.add(ps);
        }

        try {
            String sql = "INSERT INTO player_scoreitem(player_id,rater_id,score,item_name)" +
                    " VALUES (:playerId,:raterId,:score,:itemName)";

            SqlParameterSource[] beanSources = SqlParameterSourceUtils.createBatch(playerScoreitemList.toArray());
            namedParameterJdbcTemplate.batchUpdate(sql, beanSources);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<GroupInfo> groupInfoList = groupInfoRepository.findByGroupName(groups);
        Integer raterCount = groupInfoList.get(0).getRaterCount();

        //判断是否所有评委已经打分完毕
        List<RaterScore> raterScoreList = raterScoreRepository.findByPlayerId(playerId);
        if (raterScoreList == null || raterScoreList.isEmpty()) {
            return;
        }

        if (raterScoreList.size() == raterCount) {

            //取出评委的分
            raterScoreList=raterScoreDao.searchAllByPlayerIdAndCategory(playerId,1);
            //排序
            Collections.sort(raterScoreList, new Comparator<RaterScore>() {
                @Override
                public int compare(RaterScore o1, RaterScore o2) {
                    return o1.getScore().compareTo(o2.getScore());
                }

            });

            Double sum = 0D;
            for (int i = 1; i < raterScoreList.size() - 1; i++) {
                sum = sum + raterScoreList.get(i).getScore();
            }
            Double score = sum / (raterScoreList.size() - 2);
            Double totalScore=score;

            //取出额外评委的分
            List<RaterScore> extraRaterScoreList=raterScoreDao.searchAllByPlayerIdAndCategory(playerId,3);

            if (extraRaterScoreList!=null){
                //排序
                Collections.sort(extraRaterScoreList, new Comparator<RaterScore>() {
                    @Override
                    public int compare(RaterScore o1, RaterScore o2) {
                        return o1.getScore().compareTo(o2.getScore());
                    }
                });
                Double sum1 = 0D;

                for (int i = 1; i < extraRaterScoreList.size() - 1; i++) {
                    sum1 = sum1+ extraRaterScoreList.get(i).getScore();
                }
                Double extraRaterScore = sum / (extraRaterScoreList.size() - 2);

                //取出额外评委所占比率
                List<Activity> activityList=activityRepository.findAll();
                Double rate=activityList.get(0).getExtraRate();
                 totalScore=score*(1-rate)+extraRaterScore*rate;
            }

            Player player = new Player();
            player.setId(playerId);
            player.setTotalScore(totalScore);

            playerRepository.addTotalScore(totalScore,playerId);
        }

    }

    /**
     * 评委打分界面，显示已打分和未打分的选手
     * @param raterId
     * @param groups
     * @return
     */
    @Override
    public Map scoreList(Integer raterId, Integer groups) {

        List<Activity> activityList = activityRepository.findAll();
        Activity activity = activityList.get(activityList.size() - 1);

        List<Player> playerList = playerRepository.findByGroups(groups);

        List<RaterScore> raterScoreList = raterScoreRepository.findByRaterId(raterId);

        List<Integer> ids = new ArrayList<>();
        for (RaterScore rs : raterScoreList) {
            ids.add(rs.getPlayerId());
        }

        List<PlayerScoreitem> playerScoreitemList = playerScoreitemRepository.searchByRaterIdGroupByPlayerId(raterId);

//        String sql = "select * from player p where p.id in (SELECT player_id FROM rater_score rs where rs.rater_id=:raterId); ";
//
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("raterId", raterId);
//        List<Player> haveList =
//                namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<Player>(Player.class));


        List<PlayerVO> playerVOList = new ArrayList<>();

        String activityName = activity.getName();

        for (Player player : playerList) {
            PlayerVO playerVO = new PlayerVO();
            playerVO.setId(player.getId());
            playerVO.setName(player.getName());
            playerVO.setActivityName(activityName);
            playerVO.setOrder(player.getOrders());
            playerVO.setScoreState(2);

            Integer player_id = player.getId();
            for (Integer id : ids) {
                if (player_id == id) {
                    playerVO.setScoreState(1);
                }
            }

            for (PlayerScoreitem ps : playerScoreitemList) {
                playerVO.setIfFileScore(2);
                if (player_id == ps.getPlayerId()) {
                    playerVO.setIfFileScore(1);
                }
            }
            playerVOList.add(playerVO);
        }

        Map map = new HashMap();
        map.put("list", playerVOList);
        map.put("activity_password", activity.getPassword());

        return map;
    }

    /**
     *添加一个教案得分
     * @param markOneForm
     */
    @Override
    public void markone(MarkOneForm markOneForm) {
        PlayerScoreitem playerScoreitem=new PlayerScoreitem();

        playerScoreitem.setScore(markOneForm.getScore());
        playerScoreitem.setPlayerId(markOneForm.getPlayerId());
        playerScoreitem.setRaterId(markOneForm.getRaterId());
        playerScoreitem.setItemName(markOneForm.getItemName());

        playerScoreitemRepository.save(playerScoreitem);
    }

}
