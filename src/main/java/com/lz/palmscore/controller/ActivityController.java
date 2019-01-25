package com.lz.palmscore.controller;

import com.lz.palmscore.Conventer.ActivityForm2ActivityConventer;
import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.entity.ScoreItem;
import com.lz.palmscore.enums.ActivityEnum;
import com.lz.palmscore.exception.AcitvityException;
import com.lz.palmscore.form.ActivityForm;
import com.lz.palmscore.repository.ActivityRepository;
import com.lz.palmscore.service.ActivityService;
import com.lz.palmscore.service.PeopleService;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.RankVO;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 白 on 2018/12/12.
 */

@RestController
@RequestMapping("/admin")
@Slf4j
public class ActivityController  {

    @Autowired
    ActivityService activityService;

    @Autowired
    PeopleService peopleService;


    /**
     * 活动添加 仅添加
     * @param activityForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("activity_add")
    public ResultVO ActivityAdd(@Valid ActivityForm activityForm,
                                BindingResult bindingResult,
                                HttpSession session){
        if (bindingResult.hasErrors()) {
            log.error("[添加活动]格式错误");
            return ResultVOUtil.error(ActivityEnum.ACTIVITY_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        Activity activity = ActivityForm2ActivityConventer.conventer(activityForm);

        if (activity == null) {
            log.error("[活动]创建失败");
            throw new AcitvityException(ActivityEnum.ACTIVITY_ERROR);
        }
        Integer id= (Integer) session.getAttribute("activityId");
        activity.setId(id);
        session.setAttribute("activity",activity);
        return ResultVOUtil.success();
    }
    /**
     * 默认值创建活动
     * @param session
     * @return
     */
    @PostMapping("create_id")
    public ResultVO createId(HttpSession session) {

        log.info("进入活动");
        Activity activity=new Activity();
        activity.setName("1");
        activity.setUploadTime("1");
        activity.setStartTime("1");
        activity.setScoreRule("1");
        activity.setEndTime("1");
        activity.setPassword("1");
        activity.setGroupNum(1);
        activity.setExtraRate(0.00);
        Activity activityNew=activityService.add(activity);
        if (activityNew==null){
            return ResultVOUtil.error(233, "创建活动失败");
        }
        session.setAttribute("activityId",activityNew.getId());
        return ResultVOUtil.success();
    }

    /**
     * 显示所有活动
     * @return
     */
    @GetMapping("activity_show")
    public ResultVO show() {
        List<Activity> activityList = activityService.findAll();

        return ResultVOUtil.success(activityList);
    }
    /**
     * 添加密码
     * @param password
     * @param session
     * @return
     */
    @PostMapping("password_add")
    public ResultVO addPassword(@RequestParam String password,
                                HttpSession session){
        Activity activity= (Activity) session.getAttribute("activity");

        if (password == null || password.length() <= 0) {
            return ResultVOUtil.error(ActivityEnum.PASSWORD_NULL.getCode(), ActivityEnum.PASSWORD_NULL.getMessage());
        }
        activity.setPassword(password);

        session.setAttribute("activity",activity);
        return ResultVOUtil.success();
    }
    /**
     * 所有活动及 评委 选手数据最终加入数据库
     * @param session
     * @return
     */
    @PostMapping("all_insert")
    public ResultVO addInsert(HttpSession session) {

        Activity activity = (Activity) session.getAttribute("activity");
        List<ScoreItem> scoreItemList = (List<ScoreItem>) session.getAttribute("list");
        List<Rater> raterList = (List<Rater>) session.getAttribute("raterList");
        List<Player> playerList = (List<Player>) session.getAttribute("playerList");
        List<Rater> extraRaterList = (List<Rater>) session.getAttribute("extraRaterList");

        Integer activityId = activity.getId();

        for (Player player : playerList) {
            player.setActivityId(activityId);
        }

        for (Rater rater : raterList) {
            rater.setActivityId(activityId);
        }
        for (Rater rater : extraRaterList) {
            rater.setActivityId(activityId);
        }
        activityService.allInsert(activity, scoreItemList, raterList, playerList,extraRaterList);

        return ResultVOUtil.success();
    }

    /**
     * 删除所有数据
     * @param id
     * @return
     */
    @PostMapping("activity_delete")
    public ResultVO deleteActivity(@RequestParam("index") Integer id) {
        Boolean flag = activityService.alldelete(id);
        if (flag) {
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(222,"删除失败");
    }

    /**
     * 管理元端排名显示
     * @return
     */
    @GetMapping("get_result")
    public ResultVO result() {
        List<List<String>> list = peopleService.result();
        return ResultVOUtil.success(list);
    }
}
