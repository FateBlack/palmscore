package com.lz.palmscore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/score_item")
@Slf4j
public class ScoreItemController {
    @GetMapping({"/login","/"})
    public ModelAndView login(){
        return new ModelAndView("/admin/login");
    }
}
