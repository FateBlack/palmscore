package com.lz.palmscore.repository;

import com.lz.palmscore.entity.PlayerScoreitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 白 on 2018/12/20.
 */
public interface PlayerScoreitemRepository extends JpaRepository<PlayerScoreitem, Integer> {
    List<PlayerScoreitem> findByPlayerIdAndRaterId(Integer playerId, Integer raterId);


    /**
     *  查询该评委下 教案已经打分的选手的单条分数列表
     * @param raterId
     * @return
     */
    @Modifying
    @Query("select ps from PlayerScoreitem ps where ps.raterId=:raterid group by ps.playerId")
    List<PlayerScoreitem> searchByRaterIdGroupByPlayerId(@Param("raterid") Integer raterId);
}
