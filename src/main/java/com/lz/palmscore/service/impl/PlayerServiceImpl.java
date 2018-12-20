package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.PlayerFile;
import com.lz.palmscore.repository.PlayerFileRepository;
import com.lz.palmscore.repository.PlayerRepository;
import com.lz.palmscore.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerFileRepository playerFileRepository;
    /**
     * 通过id查询player
     * @param id
     * @return
     */
    @Override
    public Player findById(Integer id) {
        return playerRepository.getOne(id);
    }

    /**
     * 通过id查询到文件
     * @param id
     * @return
     */
    @Override
    public List<PlayerFile> findFileById(Integer id) {
        return playerFileRepository.findAllByPlayerId(id);
    }
}
