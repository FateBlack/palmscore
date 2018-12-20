package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.PlayerFile;
import com.lz.palmscore.repository.PlayerFileRepository;
import com.lz.palmscore.repository.PlayerRepository;
import com.lz.palmscore.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerFileRepository playerFileRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
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
    @Override
    public Boolean savefile(List<PlayerFile> playerFileList) {

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
}
