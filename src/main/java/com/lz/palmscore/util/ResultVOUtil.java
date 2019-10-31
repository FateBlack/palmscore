package com.lz.palmscore.util;

import com.lz.palmscore.vo.ResultVO;

/**
 * Created by 白 on 2018/12/11.
 */
public class ResultVOUtil {
    private static final int ERROR = -1024;
    private static final int SUCCESS = 0;
    private static final String successMsg = "SUCCESS";
    private static final String errorMsg = "FAIL";


    public static ResultVO success(Object object){
        return new ResultVO(SUCCESS, successMsg, object);
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(String msg){
        return new ResultVO(ERROR, msg);
    }

    public static ResultVO error(){
        return new ResultVO(ERROR, errorMsg);
    }

}
