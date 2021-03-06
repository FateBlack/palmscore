package com.lz.palmscore.controller;

import com.lz.palmscore.Conventer.ActivityForm2ActivityConventer;
import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.entity.ScoreItem;
import com.lz.palmscore.enums.ActivityEnum;
import com.lz.palmscore.enums.PersonTypeEnum;
import com.lz.palmscore.enums.SessionEnum;
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
            return ResultVOUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        Activity activity = ActivityForm2ActivityConventer.conventer(activityForm);

        if (activity == null) {
            log.error("[活动]创建失败");
            return ResultVOUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        Integer id = (Integer) session.getAttribute(SessionEnum.ACTIVITY_ID.getName());
        activity.setId(id);
        session.setAttribute(SessionEnum.ACTIVITY.getName(), activity);
        return ResultVOUtil.success();
    }
    /**
     * 默认值创建活动
     * @param session
     * @return
     */
    @PostMapping("create_id")
    public ResultVO createId(HttpSession session) {
        Activity activity=new Activity();
        activity.setName(String.valueOf(PersonTypeEnum.RATER.getType()));
        Activity activityNew=activityService.add(activity);
        if (activityNew==null){
            return ResultVOUtil.error( "创建活动失败");
        }
        session.setAttribute(SessionEnum.ACTIVITY_ID.getName(), activityNew.getId());
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
        Activity activity = (Activity) session.getAttribute(SessionEnum.ACTIVITY.getName());

        if (password == null || password.length() <= 0) {
            return ResultVOUtil.error(ActivityEnum.PASSWORD_NULL.getMessage());
        }
        activity.setPassword(password);
        session.setAttribute(SessionEnum.ACTIVITY.getName(), activity);
        return ResultVOUtil.success();
    }
    /**
     * 所有活动及 评委 选手数据最终加入数据库
     * @param session
     * @return
     */
    @PostMapping("all_insert")
    public ResultVO addInsert(HttpSession session) {

        Activity activity = (Activity) session.getAttribute(SessionEnum.ACTIVITY.getName());
        List<ScoreItem> scoreItemList = (List<ScoreItem>) session.getAttribute(SessionEnum.PERSON_LIST.getName());
        List<Rater> raterList = (List<Rater>) session.getAttribute("raterList");
        List<Player> playerList = (List<Player>) session.getAttribute("playerList");

        Integer activityId = activity.getId();

        for (Player player : playerList) {
            player.setActivityId(activityId);
        }

        for (Rater rater : raterList) {
            rater.setActivityId(activityId);
        }

        activityService.allInsert(activity, scoreItemList, raterList, playerList);
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
        return ResultVOUtil.error("删除活动失败");
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
