package com.lz.palmscore.interceptor;

import com.lz.palmscore.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 白 on 2018/12/12.
 */

/**
 *  管理员是否登陆拦截器
 */
@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {


    /**
     * 当拦截请求重复且过多时，可放开下列方法进行配置
     */
/*
    private boolean doLoginInterceptor(String path,String basePath){
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<>();
        //设置不进行登录拦截的路径：登录注册和验证码
        //notLoginPaths.add("/");
        notLoginPaths.add("/admin/login");

        if(notLoginPaths.contains(path)) return false;
        return true;
    }*/

    /*
     * 进入controller层之前拦截请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

/*        String basePath = request.getContextPath();
        String path = request.getRequestURI();
//      log.info("basePath:"+basePath);
//      log.info("path:"+path);

        if(!doLoginInterceptor(path, basePath) ){//是否进行登陆拦截
            return true;
        }*/

        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        if(admin==null){
            log.info("尚未登录，跳转到登录界面");
            response.sendRedirect(request.getContextPath()+"/admin/login");

            return false;
        }
        return true;
    }

    /*
     * 处理请求完成后视图渲染之前的处理操作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /*
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
