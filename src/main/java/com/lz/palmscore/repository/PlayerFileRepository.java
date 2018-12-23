package com.lz.palmscore.repository;

import com.lz.palmscore.entity.PlayerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlayerFileRepository extends JpaRepository<PlayerFile,Integer> {

    List<PlayerFile> findAllByPlayerId(Integer id);

//    void deletePlayerFilesByPlayerId(List<Integer> player_id);

    //    @Transactional
//    @Modifying
//    @Query("delete from PlayerFile p where p.playerId in (?1)")
//    void deleteBatch(List<Integer> ids);
//
    void deletePlayerFilesByPlayerId(Integer player_id);

    @Transactional
    @Modifying
    @Query("delete from PlayerFile p where p.playerId =?1")
    void deleteBatch(Integer playerId);


}
