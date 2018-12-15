package com.lz.palmscore.controller;

/**
 * Created by 白 on 2018/12/12.
 */

import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.enums.ActivityEnum;
import com.lz.palmscore.enums.FileEnum;
import com.lz.palmscore.enums.PeopleEnum;
import com.lz.palmscore.exception.AcitvityException;
import com.lz.palmscore.exception.FileException;
import com.lz.palmscore.service.PeopleService;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
     * @param type    评委 或 选手文件
     * @param session
     * @return
     */
    @PostMapping("/batch_input")
    public ResultVO batchInput(@RequestParam("file") MultipartFile file,
                               @RequestParam("type") String type,
                               HttpSession session) throws FileException {
        System.out.println("进入页面");

        String fileName = file.getOriginalFilename();
        Integer activityId = (Integer) session.getAttribute("activityId");

        //判断文件格式
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return ResultVOUtil.error(FileEnum.FILE_FORMATES_ERROR.getCode(), FileEnum.FILE_FORMATES_ERROR.getMessage());
        }


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

                if (raterList == null || raterList.isEmpty()) {
                    return ResultVOUtil.error(FileEnum.FILE_UPLOAD_ERROR.getCode(),
                            FileEnum.FILE_UPLOAD_ERROR.getMessage());
                }

                session.setAttribute("raterList", raterList);

                /*List<Rater> test = (List<Rater>) session.getAttribute("raterList");
                log.info("测试 test={}", test);*/


                return ResultVOUtil.success(raterList);

            }



            playerList = peopleService.batchInputPlayer(fileName, file,activityId);

            if (playerList == null || playerList.isEmpty()) {
                return ResultVOUtil.error(FileEnum.FILE_UPLOAD_ERROR.getCode(),
                        FileEnum.FILE_UPLOAD_ERROR.getMessage());
            }

            session.setAttribute("playerList", playerList);
            return ResultVOUtil.success(playerList);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }


    /**
     * 删除 评委或选手 单条数据
     * @param index   页面遍历 对象数组的索引
     * @param type    删除类型
     * @param session
     * @return
     */
    @PostMapping("/delete_item")
    public ResultVO deleteItem(@RequestParam("index") int index, @RequestParam("type") String type,
                               HttpSession session) {

        try {
            if (type.equals("rater")) {
                List<Rater> raterList = (List<Rater>) session.getAttribute("raterList");
                raterList.remove(index);
            }

            if (type.equals("player")) {
                List<Player> playerList = (List<Player>) session.getAttribute("playerList");
                playerList.remove(index);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//
        List<Player> playerList = (List<Player>) session.getAttribute("playerList");
//        System.out.println(playerList);
        return ResultVOUtil.success();
    }

}
