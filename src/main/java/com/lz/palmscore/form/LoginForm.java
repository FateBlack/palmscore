package com.lz.palmscore.form;

import javax.validation.constraints.NotEmpty;

/**
 * Created by 白 on 2018/12/11.
 */
public class LoginForm{

    /** 管理员账户*/
    @NotEmpty(message = "账户必填")
    private String account;

    /** 管理员密码*/
    @NotEmpty(message = "密码必填")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
