package com.lz.palmscore.controller;

import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.MarkPageVO;
import com.lz.palmscore.vo.PlayerVO;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 白 on 2018/12/16.
 */

@RestController
@RequestMapping("/wx/rater")
@Slf4j
public class WxRaterController {

    /**
     * 评委主页 只显示已评分的选手，并给出活动实时评分密码
     * @param id 评委主键
     * @return
     */
    @GetMapping("index")
    public ResultVO raterIndex(@RequestParam("rater_id") Integer id) {

        List<PlayerVO> list = new ArrayList<>();
        list.add(new PlayerVO(66, "老李", "教师大赛A", 1));
        list.add(new PlayerVO(77, "老王", "教师大赛B", 2));
        list.add(new PlayerVO(89, "老赵", "教师大赛C", 5));

        Map map = new HashMap();
        map.put("list", list);
        map.put("activity_password", 123123);

        return ResultVOUtil.success(map);
    }

    /**
     * 单个评委评分记录页,显示已评分和未评分选手
     * @param id
     * @return
     */
    @GetMapping("score_list")
    public ResultVO scoreList(@RequestParam("rater_id") Integer id) {

        List<PlayerVO> list = new ArrayList<>();
        list.add(new PlayerVO(66, "老李", "教师大赛A", 1,1));
        list.add(new PlayerVO(77, "老王", "教师大赛B", 2,1));

        list.add(new PlayerVO(12, "飞飞", "教师大赛C", 3,2));
        list.add(new PlayerVO(45, "白", "教师大赛C", 4,2));

        list.add(new PlayerVO(89, "老赵", "教师大赛C", 5,1));

        Map map = new HashMap();
        map.put("list", list);
        map.put("activity_password", 123123);

        return ResultVOUtil.success(map);
    }

    /**
     * 评委打分页面
     * @return
     */
    @GetMapping("mark_page")
    public ResultVO markPage() {

        List<MarkPageVO> markPageVOList = new ArrayList<>();
        markPageVOList.add(new MarkPageVO("教案", 0.5));
        markPageVOList.add(new MarkPageVO("现场", 0.3));
        markPageVOList.add(new MarkPageVO("技术", 0.2));

        return ResultVOUtil.success(markPageVOList);
    }






}
