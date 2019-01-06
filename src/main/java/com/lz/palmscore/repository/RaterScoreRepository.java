package com.lz.palmscore.repository;

import com.lz.palmscore.entity.RaterScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RaterScoreRepository extends JpaRepository<RaterScore,Integer> {
    List<RaterScore> findByRaterId(Integer raterId);

    List<RaterScore> findByPlayerId(Integer playerId);

//    @Modifying
//    @Query("update Player w set w.totalScore = :totalScore where w.id = :id")
//    void searchByPlayerIdAndCategory(@Param("totalScore") Double totalScore, @Param("id") Integer id);

//    @Modifying
//    @Query("SELECT rs.id,rs.raterId,rs.playerId,rs.score FROM Rater r INNER JOIN RaterScore rs WHERE r.id = rs.raterId AND rs.playerId = :playerId AND r.category=:category")
//    List<RaterScore> searchByPlayerIdAndCategory(@Param("playerId") Integer playerId, @Param("category") Integer category);

}
