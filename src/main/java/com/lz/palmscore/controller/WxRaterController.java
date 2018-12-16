package com.lz.palmscore.controller;

import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.AcitvityVO;
import com.lz.palmscore.vo.PlayerVO;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 白 on 2018/12/16.
 */

@RestController
@RequestMapping("/wx/rater")
@Slf4j
public class WxRaterController {

    /**
     * 评委主页
     * @return
     */
    @GetMapping("index")
    public ResultVO raterIndex() {

        List<PlayerVO> list = new ArrayList<>();
        list.add(new PlayerVO(1, "老李", "教师大赛A", 66));
        list.add(new PlayerVO(1, "老王", "教师大赛B", 0));
        list.add(new PlayerVO(1, "老赵", "教师大赛C", 66));

        return ResultVOUtil.success(list);
    }
}
