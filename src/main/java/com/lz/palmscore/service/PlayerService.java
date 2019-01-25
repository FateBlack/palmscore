package com.lz.palmscore.service;

import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.PlayerFile;
import com.lz.palmscore.entity.PlayerScoreitem;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.vo.AcitvityVO;

import java.util.List;

public interface PlayerService {
    Player findById(Integer id);

    List<PlayerFile> findFileById(Integer id);

    Boolean savefile(List<PlayerFile> playerFileList);

    List<Player> findByGroups(Integer groups);

    AcitvityVO index(Integer playerId);

    Boolean updatefile(List<PlayerFile> playerFileList);

    List<PlayerScoreitem> scoreInfo(Integer player_id);

}
