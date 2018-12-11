package com.lz.palmscore.controller;

import com.lz.palmscore.form.LoginForm;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by 白 on 2018/12/11.
 */

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    public ResultVO login(@Valid LoginForm loginForm,
                          BindingResult bindingResult,
                          HttpServletRequest request){


        if (bindingResult.hasErrors()) {
        }



        return ResultVOUtil.success();
    }

}
