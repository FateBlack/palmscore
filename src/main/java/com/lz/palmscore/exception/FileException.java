package com.lz.palmscore.exception;

import com.lz.palmscore.enums.FileEnum;

/**
 * Created by 白 on 2018/12/13.
 */
public class FileException extends Exception{

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public FileException(FileEnum failEnum) {
        super(failEnum.getMessage());
        this.code =failEnum.getCode();
    }

    public FileException(Integer code,String msg){
        super(msg);
        this.code = code;
    }
}
