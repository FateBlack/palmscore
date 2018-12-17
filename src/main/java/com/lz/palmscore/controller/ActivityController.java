package com.lz.palmscore.controller;

import com.lz.palmscore.Conventer.ActivityForm2ActivityConventer;
import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.enums.ActivityEnum;
import com.lz.palmscore.exception.AcitvityException;
import com.lz.palmscore.form.ActivityForm;
import com.lz.palmscore.service.ActivityService;
import com.lz.palmscore.util.ResultVOUtil;
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

    /**
     * 活动添加
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
        session.setAttribute("activity",activity);
        return ResultVOUtil.success();
    }

    /**
     * 默认值创建活动
     * @param session
     * @return
     */
    @GetMapping("createId")
    public ModelAndView createId(HttpSession session) {
        Activity activity=new Activity();
        activity.setName("1");
        activity.setUploadTime("1");
        activity.setStartTime("1");
        activity.setScoreRule("1");
        activity.setEndTime("1");
        Activity activityNew=activityService.add(activity);
        if (activityNew==null){
            System.out.println("嗯哼？错了");
        }
        session.setAttribute("activityId",activityNew.getId());
        return  new ModelAndView("/admin/activity");
    }
    @PostMapping("show")
    public ResultVO show( HttpSession session){
         Activity activity= (Activity) session.getAttribute("activity");
         List<ScoreItem> list= (List<ScoreItem>) session.getAttribute("list");
       // Map<> map=new HashMap<>();
        Map map=new HashMap();
        map.put("activity",activity);
        map.put("list",list);
        return ResultVOUtil.success(map);
    }
}
