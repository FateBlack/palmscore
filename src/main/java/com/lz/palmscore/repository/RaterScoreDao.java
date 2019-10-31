package com.lz.palmscore.repository;

import com.lz.palmscore.entity.RaterScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 白 on 2019/1/6.
 */

@Repository
public class RaterScoreDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     *  以选手id 和 评委类型，取出: 一种类型评委，对该选手的打分项-----
     * @param playerId 选手 id
     * @param category 评委类型
     * @return
     */
    public List<RaterScore> searchAllByPlayerIdAndCategory(Integer playerId,Integer category) {
        String sql = "select rs.id,rs.score from rater r INNER JOIN rater_score rs ON r.id = rs.rater_id WHERE rs.player_id = :playerid and r.category= :category;";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("playerid", playerId);
        paramMap.put("category", category);
        List<RaterScore> haveList =
                namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<RaterScore>(RaterScore.class));

        return haveList;
    }





}
