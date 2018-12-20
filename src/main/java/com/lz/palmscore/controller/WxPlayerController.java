package com.lz.palmscore.controller;

import com.lz.palmscore.entity.PlayerFile;
import com.lz.palmscore.service.PeopleService;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.AcitvityVO;
import com.lz.palmscore.vo.PlayerInfoVO;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 白 on 2018/12/16.
 */

@RestController
@RequestMapping("/wx/player")
@Slf4j
public class WxPlayerController {

    @Autowired
    PeopleService peopleService;

    /**
     * 选手主页 player_id选手主键
     * @param id
     * @return
     */
    @GetMapping("index")
    public ResultVO playerIndex(@RequestParam("player_id") Integer id) {

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
    public ResultVO fileUpload(@RequestParam("id") Integer id,@RequestParam("filepath") String filePath ) {
        PlayerFile pf=new PlayerFile();
        pf.setFilePath(filePath);
        pf.setPlayerId(id);
        PlayerFile playerFile = peopleService.savefile(pf);
        if(playerFile==null){
        }
        return ResultVOUtil.success();

    }
    /**
     *  选手信息
     * @param id 选手主键
     * @return
     */
    @GetMapping("player_info")
    public ResultVO playerInfo(@RequestParam("id") Integer id) {

        PlayerInfoVO playerInfoVO = new PlayerInfoVO(2, "风清扬", "教研室", "C语言",1);
        List<String> fileList = new ArrayList<>();
        fileList.add("http://h.hiphotos.baidu.com/image/pic/item/902397dda144ad340668b847d4a20cf430ad851e.jpg");
        fileList.add("http://b.hiphotos.baidu.com/image/pic/item/359b033b5bb5c9ea5c0e3c23d139b6003bf3b374.jpg");
        fileList.add("http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg");
        fileList.add("http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg");

        playerInfoVO.setFileList(fileList);

        return ResultVOUtil.success(playerInfoVO);
    }
}
