package com.lz.palmscore.repository;

import com.lz.palmscore.entity.PlayerScoreitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 白 on 2018/12/20.
 */
public interface PlayerScoreitemRepository extends JpaRepository<PlayerScoreitem, Integer> {
    List<PlayerScoreitem> findByPlayerId(Integer playerId);
}
