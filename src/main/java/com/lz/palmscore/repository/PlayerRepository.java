package com.lz.palmscore.repository;

import com.lz.palmscore.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 白 on 2018/12/17.
 */
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findByGroups(int groups);

    List<Player> findAllByPIdAndPassword(String pId, String password);

}
