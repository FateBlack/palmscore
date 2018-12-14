package com.lz.palmscore.service;

import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by 白 on 2018/12/12.
 */
public interface PeopleService {
    List<Rater> batchInputRater(String fileName, MultipartFile file,Integer activityId) throws Exception;

    List<Player> batchInputPlayer(String fileName, MultipartFile file,Integer activityId) throws Exception;
}
