package com.lz.palmscore.enums;

/**
 * Created by 白 on 2018/12/13.
 */
public enum FileEnum {

    EXCEL_UPLOAD_FAIL(301,"excel文件上传失败"),
    ROW_EMPTY(302,"excel该行为空"),
    File_FORMATES_ERROR(303,"文件格式错误")
    ;

    private Integer code;
    private String message;


    FileEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
