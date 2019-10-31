package com.lz.palmscore.controller;

import com.lz.palmscore.entity.Admin;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.enums.AdminEnum;
import com.lz.palmscore.enums.SessionEnum;
import com.lz.palmscore.exception.AdminException;
import com.lz.palmscore.form.LoginForm;
import com.lz.palmscore.service.AdminService;

import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
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
     * 管理员登陆
     *
     * @param loginForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/admin_login")
    public ResultVO adminLogin(@Valid LoginForm loginForm,
                               BindingResult bindingResult,
                               HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.error("[管理员登陆]格式错误");
            throw new AdminException(AdminEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        Admin loginAdmin = new Admin();
        loginAdmin.setAccount(loginForm.getAccount());
        loginAdmin.setPassword(loginForm.getPassword());

        Admin admin = adminService.login(loginAdmin);
        if (admin == null) {
            log.error("[管理员登陆]账号密码错误");
            throw new AdminException(AdminEnum.LOGIN_FAIL);
        }
        session.setAttribute(SessionEnum.ADMIN.getName(), admin);
        return ResultVOUtil.success();
    }


    /**
     * 修改密码
     *
     * @param password
     * @param rePassword
     * @param session
     * @return
     */
    @PostMapping("pass_edit")
    public ResultVO editpas(@RequestParam("password") String password,
                            @RequestParam("rePassword") String rePassword,
                            HttpSession session) {
        Admin admin = (Admin) session.getAttribute(SessionEnum.ADMIN.getName());
        int id = admin.getId();
        Admin a = adminService.getPassById(id);
        String realPas = a.getPassword();
        if (realPas.equals(password)) {//密码填写正确
            admin.setPassword(rePassword);
            Admin adminCurrent = adminService.edit(admin);
            System.out.println(admin.getPassword()+" "+admin.getAccount());
            if (adminCurrent != null) {
                session.setAttribute(SessionEnum.ADMIN.getName(), admin);
                return ResultVOUtil.success();
            }else {//修改失败
                return ResultVOUtil.error(AdminEnum.PASSWORD_FAIL.getMessage());
            }
        }//密码错误
        return ResultVOUtil.error(AdminEnum.PASSWORD_ERROR.getMessage());
    }


    /**
     * 用户登出 销毁session
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public ResultVO logout(HttpSession session) {
        session.invalidate();
        return ResultVOUtil.success();
    }
}
