package com.lz.palmscore.service;

import com.lz.palmscore.entity.*;
import com.lz.palmscore.vo.MarkPageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by 白 on 2018/12/12.
 */
public interface PeopleService {
    List<Rater> batchInputRater(String fileName, MultipartFile file,Integer category) throws Exception;

    List<Player> batchInputPlayer(String fileName, MultipartFile file) throws Exception;

    /**
     * 抽签
     * @return
     */
    /*List<List<Player>> drawLots();*/

    List<MarkPageVO> markPage(Integer playerId,Integer raterId);

    List<Rater> rlogin(String account, String password);

    List<Player> plogin(String account, String password);


    List<Player> findByList(List<Integer> playIdList);

    List<Player> drawlots();

    List<List<String>> result();

    List<Rater> reDrawlots();

    Player updatePlayerPassword(Integer id, String password, String rePassword);

    Rater updateRaterPassword(Integer id, String password, String rePassword);

    void saveExtraRater(Double extraRate, List<Rater> extraRaterList);
}
