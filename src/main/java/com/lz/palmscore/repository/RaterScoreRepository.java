package com.lz.palmscore.repository;

import com.lz.palmscore.entity.RaterScore;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RaterScoreRepository extends JpaRepository<RaterScore,Integer> {
//    @Query("SELECT player_id FROM rater_score WHERE rater_id= ?1")
//    List<Integer> findPidById(Integer id);

//    List<RaterScore> findAllByRaterId(Integer raterId);

}
