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
import org.springframework.web.bind.annotation.RestController;

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
     *
     * @return
     */
    @GetMapping({"/login", "/"})
    public ModelAndView login() {
        return new ModelAndView("admin/login");
    }

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
        session.setAttribute("admin", admin);
        return ResultVOUtil.success();
    }


    /**
     * 进入管理员主页
     *
     * @param session
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(HttpSession session, Map<String, Object> map) {

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            log.info("[进入管理员主页]session中不存在管理员信息");
            return new ModelAndView("/admin/login");
        }
        map.put("admin", admin);
        return new ModelAndView("/admin/index");
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
        Admin admin = (Admin) session.getAttribute("admin");
        int id = admin.getId();
        Admin a = adminService.getPassById(id);
        String realPas = a.getPassword();
        System.out.println("数据库密码--" + realPas);
        System.out.println("输入原密码--" + password);
        System.out.println("现在密码---" + rePassword);
        if (realPas.equals(password)) {//密码填写正确
            admin.setPassword(rePassword);
            Admin admin1 = adminService.edit(admin);
            System.out.println(admin.getPassword()+" "+admin.getAccount());
            if (admin1 != null) {
                session.setAttribute("admin", admin);
                return ResultVOUtil.success();
            }else {//修改失败
                return ResultVOUtil.error(AdminEnum.PASSWORD_FAIL.getCode(), AdminEnum.PASSWORD_FAIL.getMessage());
            }
        }//密码错误
        return ResultVOUtil.error(AdminEnum.PASSWORD_ERROR.getCode(), AdminEnum.PASSWORD_ERROR.getMessage());
    }
}
