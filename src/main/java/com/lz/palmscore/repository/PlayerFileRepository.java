package com.lz.palmscore.repository;

import com.lz.palmscore.entity.PlayerFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerFileRepository extends JpaRepository<PlayerFile,Integer> {

    List<PlayerFile> findAllByPlayerId(Integer id);

}
