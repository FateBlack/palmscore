package com.lz.palmscore.controller;

/**
 * Created by 白 on 2018/12/12.
 */

import com.lz.palmscore.Conventer.PlayerForm2PlayerConverter;
import com.lz.palmscore.Conventer.RaterForm2RaterConverter;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.enums.ActivityEnum;
import com.lz.palmscore.enums.FileEnum;
import com.lz.palmscore.enums.PeopleEnum;
import com.lz.palmscore.exception.AcitvityException;
import com.lz.palmscore.exception.FileException;
import com.lz.palmscore.form.PlayerForm;
import com.lz.palmscore.form.RaterForm;
import com.lz.palmscore.service.PeopleService;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.ResultVO;
import com.sun.deploy.nativesandbox.NativeSandboxBroker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String fileName = file.getOriginalFilename();

        //判断文件格式
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return ResultVOUtil.error(FileEnum.FILE_FORMATES_ERROR.getCode(), FileEnum.FILE_FORMATES_ERROR.getMessage());
        }

        /**
         *  测试 数据 1，待删除
         */

        List<Rater> raterList = null;
        List<Player> playerList = null;

        try {

            if (type.equals("raterFile")) {
                raterList = peopleService.batchInputRater(fileName, file);

                if (raterList == null || raterList.isEmpty()) {
                    return ResultVOUtil.error(FileEnum.FILE_UPLOAD_ERROR.getCode(),
                            FileEnum.FILE_UPLOAD_ERROR.getMessage());
                }

                session.setAttribute("raterList", raterList);
                return ResultVOUtil.success(raterList);
            }

            if (type.equals("playerFile")){
                playerList = peopleService.batchInputPlayer(fileName, file);

                if (playerList == null || playerList.isEmpty()) {
                    return ResultVOUtil.error(FileEnum.FILE_UPLOAD_ERROR.getCode(),
                            FileEnum.FILE_UPLOAD_ERROR.getMessage());
                }

                session.setAttribute("playerList", playerList);
                return ResultVOUtil.success(playerList);
            }


        } catch (Exception e) {
            log.error("[评委或选手excel文件上传]文件读取异常,fileName={}",fileName);
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


    @DeleteMapping("/delete_item")
    public ResultVO deleteItem(@RequestParam("index") int index, @RequestParam("type") String type,
                               HttpSession session) {

        try {
            if (type.equals("rater")) {
                List<Rater> raterList = (List<Rater>) session.getAttribute("raterList");

                log.info("测试删除, raterList={}", raterList);
                raterList.remove(index);
            }

            if (type.equals("player")) {
                List<Player> playerList = (List<Player>) session.getAttribute("playerList");
                playerList.remove(index);
            }

        } catch (Exception e) {
            log.error("[删除评委或选手]索引越界,index={}", index);
            e.printStackTrace();
        }

        return ResultVOUtil.success();
    }


    @PostMapping("add_rater")
    public ResultVO add(@Valid RaterForm raterForm, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(PeopleEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        Rater rater = RaterForm2RaterConverter.conventer(raterForm);

        List<Rater> raterList = (List<Rater>) session.getAttribute("raterList");

        log.info("测试添加, raterList={}", raterList);

        if (raterList == null) {
            raterList = new ArrayList<>();
        }

        raterList.add(rater);
        return ResultVOUtil.success(rater);
    }


    @PostMapping("add_player")
    public ResultVO add(@Valid PlayerForm form, BindingResult bindingResult, HttpSession session) {

        System.out.println(form);

        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(PeopleEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        Player player = PlayerForm2PlayerConverter.conventer(form);

        List<Player> playerList = (List<Player>) session.getAttribute("raterList");

        log.info("测试添加, playerList={}", playerList);

        if (playerList == null) {
            playerList = new ArrayList<>();
        }

        playerList.add(player);


        return ResultVOUtil.success(player);
    }

    @GetMapping("test")
    public ResultVO test() {

        Map map = new HashMap();
        List<Rater> raterList = new ArrayList<>();
        raterList.add(new Rater("123", "sd", "dd", "pp"));
        raterList.add(new Rater("22", "a", "b", "c"));
        raterList.add(new Rater("33", "d", "e", "f"));

        map.put("player", new Player("666", "ppp", 999));

        map.put("list", raterList);

        map.put("staut", 1);

        return ResultVOUtil.success(map);
    }


}
