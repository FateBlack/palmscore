package com.lz.palmscore.service;

import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.entity.ScoreItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by 白 on 2018/12/12.
 */
public interface PeopleService {
    List<Rater> batchInputRater(String fileName, MultipartFile file) throws Exception;

    List<Player> batchInputPlayer(String fileName, MultipartFile file) throws Exception;

    Rater findById(int id);

    Player findById2(int id);

    Rater updateById(Rater rater);

    Player updateById2(Player player);

    /**
     * 抽签
     * @return
     */
    List<List<Player>> drawLots();
}
