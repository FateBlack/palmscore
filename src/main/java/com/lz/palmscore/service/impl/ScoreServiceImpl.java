package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.RaterScore;
import com.lz.palmscore.repository.RaterScoreRepository;
import com.lz.palmscore.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    RaterScoreRepository raterScoreRepository;

    @Override
    public List<Integer> findByRaterId(Integer id) {
        return raterScoreRepository.findPidById(id);
    }
}
