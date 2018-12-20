package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.RaterScore;
import com.lz.palmscore.repository.RaterRepository;
import com.lz.palmscore.repository.RaterScoreRepository;
import com.lz.palmscore.service.RaterService;
import com.lz.palmscore.vo.PlayerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 白 on 2018/12/20.
 */
@Service
public class RaterServiceImpl implements RaterService {


    @Autowired
    RaterScoreRepository raterScoreRepository;
    /**
     * 微信端 评委id 查询出该评委下选手
     * @param id
     * @return
     */
    @Override
    public List<PlayerVO> listPlayer(Integer id) {

//        List<RaterScore> raterScoreList = raterScoreRepository.findById(id);

        return null;
    }

}
