package com.lz.palmscore.repository;

import com.lz.palmscore.entity.RaterScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaterScoreRepository extends JpaRepository<RaterScore,Integer> {
    List<Integer> findPidById(Integer id);

}
