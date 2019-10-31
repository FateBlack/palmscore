package com.lz.palmscore.controller;

/**
 * Created by 白 on 2018/12/12.
 */

import com.lz.palmscore.Conventer.PlayerForm2PlayerConverter;
import com.lz.palmscore.Conventer.RaterForm2RaterConverter;
import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.enums.FileEnum;
import com.lz.palmscore.enums.PeopleEnum;
import com.lz.palmscore.enums.PersonTypeEnum;
import com.lz.palmscore.enums.SessionEnum;
import com.lz.palmscore.exception.FileException;
import com.lz.palmscore.form.PlayerForm;
import com.lz.palmscore.form.RaterForm;
import com.lz.palmscore.service.PeopleService;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.RankVO;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
            return ResultVOUtil.error(FileEnum.FILE_FORMATES_ERROR.getMessage());
        }

        List<Rater> raterList = null;
        List<Player> playerList = null;

        try {
            if (type.equals("raterFile")) {
                raterList = peopleService.batchInputRater(fileName, file,1);
                if (raterList == null || raterList.isEmpty()) {
                    return ResultVOUtil.error(FileEnum.FILE_UPLOAD_ERROR.getMessage());
                }
                session.setAttribute("raterList", raterList);
                return ResultVOUtil.success(raterList);
            }

            // 额外评委
            if (type.equals("extraRaterFile")) {
                raterList = peopleService.batchInputRater(fileName, file,3);

                if (raterList == null || raterList.isEmpty()) {
                    return ResultVOUtil.error(FileEnum.FILE_UPLOAD_ERROR.getMessage());
                }
                session.setAttribute("extraRaterList", raterList);
                return ResultVOUtil.success(raterList);
            }

            if (type.equals("playerFile")){
                playerList = peopleService.batchInputPlayer(fileName, file);

                if (playerList == null || playerList.isEmpty()) {
                    return ResultVOUtil.error(FileEnum.FILE_UPLOAD_ERROR.getMessage());
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

    @PostMapping("/delete_item")
    public ResultVO deleteItem(@RequestParam("index") int index, @RequestParam("type") String type,
                               HttpSession session) {
        try {
            if (type.equals("rater")) {
                List<Rater> raterList = (List<Rater>) session.getAttribute(SessionEnum.RATER_LIST.getName());
                raterList.remove(index);
                session.setAttribute("raterList",raterList);
            }
            if (type.equals("extraRater")) {
                List<Rater> raterList = (List<Rater>) session.getAttribute(SessionEnum.EXTRA_RATER_List.getName());
                System.out.println("删除额外选手");
                raterList.remove(index);
                session.setAttribute("extraRaterList",raterList);
            }

            if (type.equals("player")) {
                List<Player> playerList = (List<Player>) session.getAttribute(SessionEnum.PLAYER_List.getName());
                playerList.remove(index);
                session.setAttribute("playerList",playerList);
            }
        } catch (Exception e) {
            log.error("[删除评委或选手]失败,index={}", index);
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

    /**
     * 单个添加评委信息
     * @param raterForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("add_rater")
    public ResultVO add(@RequestParam("category") Integer category,@Valid RaterForm raterForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }

        Rater rater = RaterForm2RaterConverter.conventer(raterForm);
        List<Rater> raterList=null;
        if(category== PersonTypeEnum.RATER.getType()){
            raterList = (List<Rater>) session.getAttribute(SessionEnum.RATER_LIST.getName());
            if (raterList == null) {
                raterList = new ArrayList<>();
            }
            rater.setCategory(PersonTypeEnum.RATER.getType());
            raterList.add(rater);
            session.setAttribute(SessionEnum.RATER_LIST.getName(),raterList);
        }
        if (category==PersonTypeEnum.EXTRA_RATER.getType()){
            raterList = (List<Rater>) session.getAttribute(SessionEnum.EXTRA_RATER_List.getName());
            if (raterList == null) {
                raterList = new ArrayList<>();
            }
            rater.setCategory(PersonTypeEnum.EXTRA_RATER.getType());
            log.info(rater.toString());
            raterList.add(rater);
            session.setAttribute(SessionEnum.EXTRA_RATER_List.getName(),raterList);
        }
        return ResultVOUtil.success(raterList);
    }

    /**
     * 单个添加选手信息
     * @param form
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("add_player")
    public ResultVO add(@Valid PlayerForm form,
                        BindingResult bindingResult,
                        HttpSession session) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        Player player = PlayerForm2PlayerConverter.conventer(form);
        List<Player> playerList = (List<Player>) session.getAttribute(SessionEnum.PLAYER_List.getName());

        if (playerList == null) {
            playerList = new ArrayList<>();
        }
        playerList.add(player);
        session.setAttribute(SessionEnum.PLAYER_List.getName(),playerList);
        return ResultVOUtil.success(playerList);
    }

    /**
     * 修改评委
     * @param index
     * @param raterForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("rater_edit")
    public ResultVO raterEdit(@RequestParam int index,
                              @RequestParam("category") Integer category,
                                  @Valid RaterForm raterForm,
                                  BindingResult bindingResult,
                                  HttpSession session){
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        List<Rater> raterList = (List<Rater>) session.getAttribute(SessionEnum.RATER_LIST.getName());
        if(category==PersonTypeEnum.EXTRA_RATER.getType()){
            raterList = (List<Rater>) session.getAttribute(SessionEnum.EXTRA_RATER_List.getName());
        }
            raterList.get(index).setRId(raterForm.getRid());
            raterList.get(index).setName(raterForm.getName());
            raterList.get(index).setJob(raterForm.getJob());
            raterList.get(index).setWorkplace(raterForm.getWorkplace());
        if(category==PersonTypeEnum.RATER.getType()){
            session.setAttribute(SessionEnum.RATER_LIST.getName(),raterList);
        }
        if(category==PersonTypeEnum.EXTRA_RATER.getType()){
            session.setAttribute(SessionEnum.EXTRA_RATER_List.getName(),raterList);
        }
        return  ResultVOUtil.success(raterList);
    }

    /**
     * 选手修改
     * @param index
     * @param playerForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("player_edit")
    public ResultVO raterEdit(@RequestParam int index,
                              @Valid PlayerForm playerForm,
                              BindingResult bindingResult,
                              HttpSession session){
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        List<Player> playerList = (List<Player>) session.getAttribute(SessionEnum.PLAYER_List.getName());

        playerList.get(index).setPId(playerForm.getPid());
        playerList.get(index).setName(playerForm.getName());
        playerList.get(index).setWorkplace(playerForm.getWorkplace());
        playerList.get(index).setCourse(playerForm.getCourse());
        playerList.get(index).setOrders(playerForm.getOrders());
        playerList.get(index).setGroups(playerForm.getGroups());

        session.setAttribute(SessionEnum.PLAYER_List.getName(),playerList);
        return  ResultVOUtil.success(playerList);

    }

    /**
     * 抽签
     * @return
     */
    @GetMapping("drawlots")
    public ResultVO drawLots() {
      /*  peopleService.drawLots();*/
        List<Player> list=peopleService.drawlots();
        return ResultVOUtil.success(list);
    }

    /**
     * 评委抽签
     * @return
     */
    @GetMapping("reDrawlots")
    public ResultVO reDrawlots() {
        List<Rater> list=peopleService.reDrawlots();
        return ResultVOUtil.success(list);
    }


    //  额外评委 加入数据库
    @PostMapping("saveExtraRater")
    public ResultVO extraRate(@RequestParam("rate") String rate,
                              HttpSession session) {
        Double extraRate = Double.parseDouble(rate); //额外评委评分占比
        List<Rater> extraRaterList = (List<Rater>) session.getAttribute(SessionEnum.EXTRA_RATER_List.getName());
        peopleService.saveExtraRater(extraRate, extraRaterList);
        return ResultVOUtil.success();
    }

}
