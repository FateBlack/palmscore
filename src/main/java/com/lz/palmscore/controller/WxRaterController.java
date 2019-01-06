package com.lz.palmscore.controller;

import com.lz.palmscore.dao.RaterScoreDao;
import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.RaterScore;
import com.lz.palmscore.entity.ScoreItem;
import com.lz.palmscore.form.MarkOneForm;
import com.lz.palmscore.repository.RaterRepository;
import com.lz.palmscore.repository.RaterScoreRepository;
import com.lz.palmscore.service.ActivityService;
import com.lz.palmscore.service.PeopleService;
import com.lz.palmscore.service.RaterService;
import com.lz.palmscore.service.ScoreService;
import com.lz.palmscore.form.LoginForm;
import com.lz.palmscore.form.MarkForm;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.MarkPageVO;
import com.lz.palmscore.vo.PlayerVO;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 白 on 2018/12/16.
 */

@RestController
@RequestMapping("/wx/rater")
@Slf4j
public class WxRaterController {

    @Autowired
    PeopleService peopleService;

    @Autowired
    ScoreService scoreService;

    @Autowired
    ActivityService activityService;

    @Autowired
    RaterService raterService;

    /**
     * 评委主页 只显示已评分的选手，并给出活动实时评分密码
     * @param raterId 评委主键
     * @return
     */
    @GetMapping("index")
    public ResultVO raterIndex(@RequestParam("rater_id") Integer raterId) {

        Map map = raterService.listPlayer(raterId);

        return ResultVOUtil.success(map);
    }

    /**
     * 单个评委评分记录页,显示已评分和未评分选手
     *
     * @param raterId
     * @return
     */
    @GetMapping("score_list")
    public ResultVO scoreList(@RequestParam("rater_id") Integer raterId, @RequestParam("groups") Integer groups) {

        Map map = raterService.scoreList(raterId, groups);

        return ResultVOUtil.success(map);
    }

    /**
     * 评委打分页面
     * @return
     */
    @GetMapping("mark_page")
    public ResultVO markPage() {

        List<ScoreItem> list=peopleService.getAll();
        List<MarkPageVO> markPageVOList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            ScoreItem scoreItem=list.get(i);
            markPageVOList.add(new MarkPageVO(scoreItem.getId(), scoreItem.getName(), scoreItem.getRate(), scoreItem.getFileUpload()));
        }
        return ResultVOUtil.success(markPageVOList);
    }

    /**
     * 评分按钮
     * @param markForm
     * @param bindingResult
     * @return
     */
    @PostMapping("mark")
    public ResultVO mark(@Valid MarkForm markForm,
                         BindingResult bindingResult,
                         @RequestParam("groups") Integer groups) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(233, "分数不能为空");
        }
        raterService.mark(markForm, groups);

        return ResultVOUtil.success();
    }
    /**
     * 单个评委仅打分教案
     */
      @PostMapping("mark_one")
    public ResultVO markOne(@Valid MarkOneForm markOneForm,
                            BindingResult bindingResult){
          if (bindingResult.hasErrors()) {
              return ResultVOUtil.error(233, "分数不能为空");
          }
          raterService.markone(markOneForm);
          return ResultVOUtil.success();

      }

}
