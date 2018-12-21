package com.lz.palmscore.controller;

import com.lz.palmscore.entity.Activity;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.PlayerFile;
import com.lz.palmscore.entity.PlayerScoreitem;
import com.lz.palmscore.form.FileFathForm;
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

import javax.validation.Valid;
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
     *
     * @param playerId
     * @return
     */
    @GetMapping("index")
    public ResultVO playerIndex(@RequestParam("player_id") Integer playerId) {

        AcitvityVO acitvityVO = playerService.index(playerId);
        List<AcitvityVO> acitvityVOList = new ArrayList<>();
        acitvityVOList.add(acitvityVO);

        return ResultVOUtil.success(acitvityVOList);
    }

    /**
     * 选手文件上传
     * @param fileFathForm
     * @return
     */
    @PostMapping("file_upload")
    public ResultVO fileUpload(@Valid FileFathForm fileFathForm) {


        List<PlayerFile> playerFileList = new ArrayList<>();
        List<String> filePath = fileFathForm.getFilepath();


        for (int i = 0; i < filePath.size(); i++) {
            PlayerFile pf = new PlayerFile();
            pf.setFilePath(filePath.get(i));
            pf.setPlayerId(fileFathForm.getId());

            playerFileList.add(pf);
        }

        Boolean b = playerService.savefile(playerFileList);
        if (b) {
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(323, "失败");
    }


    /**
     * 选手文件修改
     * @param fileFathForm
     * @return
     */
    @PostMapping("file_update")
    public ResultVO fileUpdate(@Valid FileFathForm fileFathForm) {


        List<PlayerFile> playerFileList = new ArrayList<>();
        List<String> filePath = fileFathForm.getFilepath();


        for (int i = 0; i < filePath.size(); i++) {
            PlayerFile pf = new PlayerFile();
            pf.setFilePath(filePath.get(i));
            pf.setPlayerId(fileFathForm.getId());

            playerFileList.add(pf);
        }

        Boolean b = playerService.updatefile(playerFileList);
        if (b) {
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(323, "失败");
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

    /**
     * 选手 分数详情
     * @param player_id
     * @return
     */
    @GetMapping("score_info")
    public ResultVO scoreInfo(@RequestParam("player_id") Integer player_id) {

        List<PlayerScoreitem> playerScoreitems = playerService.scoreInfo(player_id);

        return ResultVOUtil.success(playerScoreitems);
    }
}
