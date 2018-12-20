package com.lz.palmscore.repository;

import com.lz.palmscore.entity.Rater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 白 on 2018/12/17.
 */
public interface RaterRepository extends JpaRepository<Rater, Integer> {
    List<Rater> findByRIdAndPassword(String account, String password);
}
