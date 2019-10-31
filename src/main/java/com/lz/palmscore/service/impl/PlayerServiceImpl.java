package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.*;
import com.lz.palmscore.enums.UploadTypeEnum;
import com.lz.palmscore.repository.ActivityRepository;
import com.lz.palmscore.repository.PlayerFileRepository;
import com.lz.palmscore.repository.PlayerRepository;
import com.lz.palmscore.repository.ScoreItemRepository;
import com.lz.palmscore.service.PlayerService;
import com.lz.palmscore.vo.AcitvityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerFileRepository playerFileRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private ScoreItemRepository scoreItemRepository;

    /**
     * 通过id查询player
     * @param id
     * @return
     */
    @Override
    public Player findById(Integer id) {
        return playerRepository.getOne(id);
    }

    /**
     * 通过id查询到文件
     * @param id
     * @return
     */
    @Override
    public List<PlayerFile> findFileById(Integer id) {
        return playerFileRepository.findAllByPlayerId(id);
    }

    /**
     * 批量存储文件路径
     * @param playerFileList
     * @return
     */
    @Transactional
    @Override
    public Boolean savefile(List<PlayerFile> playerFileList) {

        String sql = "INSERT INTO player_file(player_id,file_path)" +
                " VALUES (:playerId,:filePath)";
        Boolean flag = false;

        Player player = playerRepository.findById(playerFileList.get(0).getPlayerId()).get();
        player.setFileUpload(1);

        try{
            SqlParameterSource[] beanSources = SqlParameterSourceUtils.createBatch(playerFileList.toArray());
            namedParameterJdbcTemplate.batchUpdate(sql,beanSources);

            playerRepository.save(player);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有成绩排序
     * @param groups
     * @return
     */
    @Override
    public List<Player> findByGroups(Integer groups) {
        List<Player> playerList = playerRepository.findByGroupsAndTotalScoreNotNull(groups);
        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o2.getTotalScore().compareTo(o1.getTotalScore());
            }
        });
        return playerList;

    }

    /**
     * 选手主页
     * @param playerId
     * @return
     */
    @Override
    public AcitvityVO index(Integer playerId) {
        List<Activity> activityList = activityRepository.findAll();
        Activity activity = activityList.get(activityList.size() - 1);

        Activity activityA = activityList.get(activityList.size() - 1);

        AcitvityVO acitvityVO = new AcitvityVO();

        acitvityVO.setId(activityA.getId());
        acitvityVO.setName(activityA.getName());
        acitvityVO.setStartTime(activityA.getStartTime());
        acitvityVO.setEndTime(activityA.getEndTime());
        acitvityVO.setUploadTime(activityA.getUploadTime());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date endTime = null;

        try {
            endTime = sdf.parse(activity.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (endTime.before(date)) {
            acitvityVO.setState(3); //活动已结束
            return acitvityVO;
        }

        Player player = playerRepository.findById(playerId).get();
        Integer file_upload = player.getFileUpload();

        if (file_upload == UploadTypeEnum.UPLOAD.getType()) {
            acitvityVO.setState(file_upload); //文件未上传
        } else {
            acitvityVO.setState(UploadTypeEnum.NOT_UPLOAD.getType()); //文件已上传
        }

        return acitvityVO;
    }

    @Transactional
    @Override
    public Boolean updatefile(List<PlayerFile> playerFileList) {

        String sql = "INSERT INTO player_file(player_id,file_path)" +
                " VALUES (:playerId,:filePath)";
        Boolean flag = false;
        try{
            playerFileRepository.deleteBatch(playerFileList.get(0).getPlayerId());
            SqlParameterSource[] beanSources = SqlParameterSourceUtils.createBatch(playerFileList.toArray());
            namedParameterJdbcTemplate.batchUpdate(sql,beanSources);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<PlayerScoreitem> scoreInfo(Integer player_id) {
        Player player = playerRepository.findById(player_id).get();
        List<ScoreItem> scoreItemList = scoreItemRepository.findAll();
        List<PlayerScoreitem> playerScoreitems = new ArrayList<>();

        Double totalScore = player.getTotalScore();
        for (ScoreItem scoreItem : scoreItemList) {
            PlayerScoreitem ps = new PlayerScoreitem();
            Double score =   totalScore*scoreItem.getRate();
            BigDecimal b = new BigDecimal(score);
            score = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            ps.setScore(score);
            playerScoreitems.add(ps);
        }
        return playerScoreitems;
    }



}
