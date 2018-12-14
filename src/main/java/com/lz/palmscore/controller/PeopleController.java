package com.lz.palmscore.controller;

/**
 * Created by 白 on 2018/12/12.
 */

import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.enums.ActivityEnum;
import com.lz.palmscore.exception.AcitvityException;
import com.lz.palmscore.service.PeopleService;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *  评委 和 选手
 *
 */

@RestController
@RequestMapping("/people")
@Slf4j
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    /**
     *  excel 获取excel中评委或选手数据，暂时存储到session中
     * @param file
     * @param type
     * @param session
     * @return
     */
    @PostMapping("/batch_input")
    public ResultVO batchInput(@RequestParam("file") MultipartFile file,
                               @RequestParam("type") String type,
                               HttpSession session) {
        System.out.println("进入页面");

        String fileName = file.getOriginalFilename();
        Integer activityId = (Integer) session.getAttribute("activityId");

        System.out.println("文件名"+fileName);
        /**
         *  测试 数据 1，待删除
         */
        activityId = 1;

        if (activityId == null) {
            log.info("[excel 文件上传]活动id不存在,activity={}", activityId);
            throw new AcitvityException(ActivityEnum.ACTIVITY_ID_NOF_FOUND);
        }

        List<Rater> raterList = null;
        List<Player> playerList = null;

        try {

            if (type.equals("raterFile")) {
                raterList = peopleService.batchInputRater(fileName, file, activityId);
                session.setAttribute("raterList", raterList);

                List<Rater> test = (List<Rater>) session.getAttribute("raterList");
                log.info("测试 test={}", test);

                return ResultVOUtil.success(raterList);

            }else{

                playerList = peopleService.batchInputPlayer(fileName, file,activityId);
                session.setAttribute("playList", playerList);

                return ResultVOUtil.success(playerList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }


    /**
     * 测试请求
     * @return
     */
    @GetMapping("/test")
    public ResultVO test() {

        System.out.println("进入页面");
        return ResultVOUtil.success();
    }

}
