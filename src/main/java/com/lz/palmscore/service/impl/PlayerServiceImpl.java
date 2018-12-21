package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.*;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

        Player player = new Player();
        player.setId(playerFileList.get(0).getPlayerId());
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
        return playerRepository.findByGroupsOrderByTotalScore(groups);
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
        Date uploadTime = null;

        try {
            uploadTime = sdf.parse(activity.getUploadTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (uploadTime.before(date)) {
            acitvityVO.setState(4); //超过截至时间

            return acitvityVO;
        }

        Player player = playerRepository.findById(playerId).get();
        Integer file_upload = player.getFileUpload();

        if (file_upload == 2) {
            acitvityVO.setState(2); //文件未上传
        } else {
            acitvityVO.setState(1); //文件已上传
        }

        return acitvityVO;
    }

    @Transactional
    @Override
    public Boolean updatefile(List<PlayerFile> playerFileList) {

        List<Integer> playerIds = new ArrayList<>();
        for (PlayerFile playerFile : playerFileList) {
            playerIds.add(playerFile.getPlayerId());
        }

        playerFileRepository.deletePlayerFilesByPlayerId(playerIds);

        String sql = "INSERT INTO player_file(player_id,file_path)" +
                " VALUES (:playerId,:filePath)";
        Boolean flag = false;
        try{
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
            Double score = scoreItem.getRate() / totalScore;
            ps.setScore(score);

            playerScoreitems.add(ps);
        }


        return playerScoreitems;
    }

}
