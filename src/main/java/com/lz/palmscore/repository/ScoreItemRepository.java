package com.lz.palmscore.repository;

import com.lz.palmscore.entity.ScoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreItemRepository extends JpaRepository<ScoreItem,Integer> {
}
