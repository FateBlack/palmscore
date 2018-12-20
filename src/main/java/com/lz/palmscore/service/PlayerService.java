package com.lz.palmscore.service;

import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.PlayerFile;

import java.util.List;

public interface PlayerService {
    Player findById(Integer id);

    List<PlayerFile> findFileById(Integer id);
}
