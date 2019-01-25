package com.lz.palmscore.repository;

import com.lz.palmscore.entity.ScoreItem;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreItemRepository extends JpaRepository<ScoreItem,Integer> {

    List<ScoreItem> findByFileUpload(Integer fileUpload);



}
