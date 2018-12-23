package com.lz.palmscore.service;


import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.entity.ScoreItem;

import java.util.List;

/**
 * Created by 白 on 2018/12/12.
 */
public interface ActivityService{
    Activity add(Activity ac);

    boolean allInsert(Activity activity, List<ScoreItem> scoreItemList, List<Rater> raterList, List<Player> playerList);

    List<Activity> findAll();

    boolean alldelete(Integer id);


}
