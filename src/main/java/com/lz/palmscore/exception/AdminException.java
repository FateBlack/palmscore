package com.lz.palmscore.exception;

import com.lz.palmscore.enums.AdminEnum;

/**
 * Created by 白 on 2018/12/12.
 */
public class AdminException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public AdminException(AdminEnum AdminEnum) {
        super(AdminEnum.getMessage());
        this.code =AdminEnum.getCode();
    }

    public AdminException(Integer code,String msg){
        super(msg);
        this.code = code;
    }
}
