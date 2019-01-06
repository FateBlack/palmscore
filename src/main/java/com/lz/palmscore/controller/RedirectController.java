package com.lz.palmscore.controller;

import com.lz.palmscore.entity.Admin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by 白 on 2018/12/29.
 *
 *
 *  整合主页 无附加内容的页面跳转
 *
 */

@RestController
@RequestMapping("/admin")
public class RedirectController {


    /**
     * 进入登陆界面
     *
     * @return
     */
    @GetMapping({"/login", "/"})
    public ModelAndView login() {
        return new ModelAndView("admin/login");
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }

    @GetMapping("/myActivity")
    public ModelAndView myActivity() {
        return new ModelAndView("admin/myActivity");
    }

    @GetMapping("/drawlots")
    public ModelAndView drawlots() {
        return new ModelAndView("admin/drawlots");
    }

    @GetMapping("/countdown")
    public ModelAndView countdown() {
        return new ModelAndView("admin/countdown");
    }

    @GetMapping("/result")
    public ModelAndView result() {
        return new ModelAndView("admin/result");
    }

    @GetMapping("/repassword")
    public ModelAndView repassword() {
        return new ModelAndView("admin/repassword");
    }

    @GetMapping("/activity")
    public ModelAndView activity() {
        return new ModelAndView("admin/activity");
    }

    @GetMapping("/competitor")
    public ModelAndView competitor() {
        return new ModelAndView("admin/competitor");
    }

    @GetMapping("/judge")
    public ModelAndView judge() {
        return new ModelAndView("admin/judge");
    }

    @GetMapping("/rejudge")
    public ModelAndView rejudge() {
        return new ModelAndView("admin/rejudge");
    }

}
