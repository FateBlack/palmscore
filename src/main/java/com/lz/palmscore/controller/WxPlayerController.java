package com.lz.palmscore.controller;

import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.PlayerFile;
import com.lz.palmscore.service.ActivityService;
import com.lz.palmscore.service.PeopleService;
import com.lz.palmscore.service.PlayerService;
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

    @Autowired
    PlayerService playerService;

    @Autowired
    ActivityService activityService;
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
    public ResultVO fileUpload(@RequestParam("id") Integer id,@RequestParam("filepath") String filePath ){
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

        Player player=playerService.findById(id);
        List<Activity> activityList=activityService.findAll();
        int a=activityList.size()-1;
        String uploadTime=activityList.get(a).getUploadTime();

        PlayerInfoVO playerInfoVO = new PlayerInfoVO(player.getId(), player.getName(), player.getWorkplace(), player.getCourse(),1);

        List<PlayerFile> fileList =playerService.findFileById(id);

        List<String> list=new ArrayList<>();
        for(int i=0;i<fileList.size();i++){
            list.add(fileList.get(i).getFilePath());
        }
        playerInfoVO.setFileList(list);
        return ResultVOUtil.success(playerInfoVO);
    }
}
