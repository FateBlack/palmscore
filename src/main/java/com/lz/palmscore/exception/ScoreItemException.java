package com.lz.palmscore.exception;


import com.lz.palmscore.enums.ScoreItemEnum;

public class ScoreItemException extends RuntimeException {
        private Integer code;
        public Integer getCode() {
            return code;
        }

    public ScoreItemException(ScoreItemEnum scoreItemEnum) {
        super(scoreItemEnum.getMessage());
        this.code = scoreItemEnum.getCode();
    }
    public ScoreItemException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
