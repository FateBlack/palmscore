package com.lz.palmscore.controller;

import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.AcitvityVO;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 白 on 2018/12/16.
 */

@RestController
@RequestMapping("/wx/player")
@Slf4j
public class WxPlayerController {

    /**
     * 选手主页
     * @return
     */
    @GetMapping("index")
    public ResultVO playerIndex() {

        List<AcitvityVO> list = new ArrayList<>();
        list.add(new AcitvityVO(1, "教师大赛", "2018-12-15", "2018-12-28",
                "2018-12-14", 1));

        list.add(new AcitvityVO(2, "教师大赛B", "2018-12-1", "2018-12-3",
                "2018-12-14", 2));

        list.add(new AcitvityVO(3, "教师大赛C", "2018-12-15", "2018-12-16",
                "2018-12-14", 2));

        return ResultVOUtil.success(list);
    }

    /**
     * 选手文件上传
     * @param id 选手主键
     * @return
     */
    @PostMapping("file_upload")
    public ResultVO fileUpload(@RequestParam("id") Integer id,@RequestParam("file") MultipartFile file) {


        return ResultVOUtil.success();
    }



}
