package com.lz.palmscore.enums;

/**
 * @author LiZhenJiang
 * @date 2019-11-01
 *
 */
public enum  UploadTypeEnum {

    UPLOAD(1,"已上传"),
    NOT_UPLOAD(2,"未上传")
    ;
    private Integer type;
    private String desc;

    UploadTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }
}
