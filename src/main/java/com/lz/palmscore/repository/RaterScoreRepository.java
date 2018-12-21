package com.lz.palmscore.repository;

import com.lz.palmscore.entity.RaterScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RaterScoreRepository extends JpaRepository<RaterScore,Integer> {
    List<RaterScore> findByRaterId(Integer raterId);

    List<RaterScore> findByPlayerId(Integer playerId);

}
