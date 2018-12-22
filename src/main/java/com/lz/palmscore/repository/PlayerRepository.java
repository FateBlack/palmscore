package com.lz.palmscore.repository;

import com.lz.palmscore.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 白 on 2018/12/17.
 */
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findByGroups(Integer groups);

    List<Player> findByPIdAndPassword(String pId, String password);

    List<Player> findByGroupsAndTotalScoreNotNull(Integer groups);


//    @Modifying
//    @Query("update Player w set w.file_upload = :upload where w.id = :id")
//    void updateFileUpload(@Param("upload") Integer file_upload, @Param("id") Integer id);
}
