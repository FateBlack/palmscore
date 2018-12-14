package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.ScoreItem;
import com.lz.palmscore.repository.ScoreItemRepository;
import com.lz.palmscore.service.ScoreItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ScoreItemService")
public class ScoreItemServiceImpl implements ScoreItemService {
    @Autowired
    ScoreItemRepository scoreItemRepository;

    @Override
    public ScoreItem add(ScoreItem scoreItem) {
        return scoreItemRepository.save(scoreItem);
    }
}
