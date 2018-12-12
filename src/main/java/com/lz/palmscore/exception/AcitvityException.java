package com.lz.palmscore.exception;

import com.lz.palmscore.enums.ActivityEnum;

/**
 * Created by 白 on 2018/12/12.
 */
public class AcitvityException extends RuntimeException{

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public AcitvityException(ActivityEnum activityEnum) {
        super(activityEnum.getMessage());
        this.code =activityEnum.getCode();
    }

    public AcitvityException(Integer code,String msg){
        super(msg);
        this.code = code;
    }
}
