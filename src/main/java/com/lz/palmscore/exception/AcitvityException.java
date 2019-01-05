package com.lz.palmscore.exception;

import com.lz.palmscore.enums.ActivityEnum;

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
