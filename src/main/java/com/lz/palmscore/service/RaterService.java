package com.lz.palmscore.service;

import com.lz.palmscore.form.MarkForm;

import java.util.Map;

/**
 * Created by 白 on 2018/12/20.
 */
public interface RaterService {

    Map listPlayer(Integer id);

    void mark(MarkForm markForm);
}
