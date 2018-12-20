package com.lz.palmscore.service;

import com.lz.palmscore.entity.RaterScore;

import java.util.List;

public interface ScoreService {
    List<Integer> findByRaterId(Integer id);
}
