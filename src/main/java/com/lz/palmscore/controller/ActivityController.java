package com.lz.palmscore.controller;

import com.lz.palmscore.Conventer.ActivityForm2ActivityConventer;
import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.enums.ActivityEnum;
import com.lz.palmscore.exception.AcitvityException;
import com.lz.palmscore.form.ActivityForm;
import com.lz.palmscore.form.LoginForm;
import com.lz.palmscore.service.ActivityService;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
/**
 * Created by 白 on 2018/12/12.
 */

@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController  {
    @Autowired
    ActivityService activityService;


    @PostMapping("activity_add")
    public ResultVO ActivityAdd(@Valid ActivityForm activityForm,
                                BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.error("[添加活动]格式错误");
            throw new AcitvityException(ActivityEnum.ACTIVITY_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        Activity activity = ActivityForm2ActivityConventer.conventer(activityForm);

        Activity activityResult = activityService.add(activity);

        if (activityResult == null) {

            log.error("[活动]创建失败");
            throw new AcitvityException(ActivityEnum.ACTIVITY_ERROR);
        }
        return ResultVOUtil.success();
    }

}
