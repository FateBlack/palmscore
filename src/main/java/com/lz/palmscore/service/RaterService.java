package com.lz.palmscore.service;

import com.lz.palmscore.form.MarkForm;
import com.lz.palmscore.form.MarkOneForm;
import com.lz.palmscore.vo.PlayerVO;

import java.util.List;
import java.util.Map;

/**
 * Created by 白 on 2018/12/20.
 */
public interface RaterService {

    Map listPlayer(Integer id);

    void mark(MarkForm markForm,Integer groups);

    Map scoreList(Integer raterId, Integer groups);

    void markone(MarkOneForm markOneForm);

    int ifMarkAhead(Integer playerId, Integer raterId);
}
