package com.lz.palmscore.repository;

import com.lz.palmscore.entity.PlayerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerFileRepository extends JpaRepository<PlayerFile,Integer> {

    List<PlayerFile> findAllByPlayerId(Integer id);

    void deletePlayerFilesByPlayerId(List<Integer> player_id);
    /*@Modifying
    @Query("delete from player_file p where p.player_id in (?1)")
    void deleteBatch(List<Integer> ids);*/

}
