package com.lz.palmscore.controller;

import com.lz.palmscore.entity.Admin;
import com.lz.palmscore.enums.AdminEnum;
import com.lz.palmscore.exception.AdminException;
import com.lz.palmscore.form.LoginForm;
import com.lz.palmscore.service.AdminService;

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
 * Created by 白 on 2018/12/11.
 */

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * 进入登陆界面
     * @return
     */
    @GetMapping({"/login","/"})
    public ModelAndView login(){
        return new ModelAndView("/admin/login");
    }

    /**
     * 管理员登陆
     * @param loginForm
     * @param bindingResult
     * @param request
     * @return
     */
    @PostMapping("/admin_login")
    public ResultVO AdminLogin(@Valid LoginForm loginForm,
                          BindingResult bindingResult,
                          HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            log.error("[登陆]格式错误");
            throw new AdminException(AdminEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        Admin loginAdmin = new Admin();
        loginAdmin.setAccount(loginForm.getAccount());
        loginAdmin.setPassword(loginForm.getPassword());

        Admin admin = adminService.login(loginAdmin);

        if (admin==null){
            log.error("[登陆]账号密码错误");
            throw new AdminException(AdminEnum.LOGIN_FAIL);
        }

        request.getSession().setAttribute("admin",admin);
        return ResultVOUtil.success();
    }


    /**
     * 进入管理员主页
     * @param session
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(HttpSession session,Map<String,Object> map){

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null){
            log.error("[管理员主页]请先登陆");
        }
        map.put("admin",admin);
        return new ModelAndView("/admin/index");
    }



}
