package com.lz.palmscore.controller;

import com.lz.palmscore.entity.ScoreItem;
import com.lz.palmscore.enums.ScoreItemEnum;
import com.lz.palmscore.exception.ScoreItemException;
import com.lz.palmscore.form.ScoreItemForm;
import com.lz.palmscore.service.ScoreItemService;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/score_item")
@Slf4j
public class ScoreItemController {

    @Autowired
    ScoreItemService scoreItemService;
    /**
     * 添加
     * @param scoreItemForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("score_item_add")
    public ResultVO ScoreItemAdd(@Valid ScoreItemForm scoreItemForm,
                                 BindingResult bindingResult,
                                 HttpSession  session) {


        List<ScoreItem> list = (List<ScoreItem>) session.getAttribute("score_list");

        if(list == null){
            list = new ArrayList<>();
        }

        if (bindingResult.hasErrors()) {
            log.error("[添加活动]格式错误");
            throw new ScoreItemException(ScoreItemEnum.SCOREITEM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        ScoreItem scoreItem = new ScoreItem();
        scoreItem.setName(scoreItemForm.getName());
        scoreItem.setRate(scoreItemForm.getRate());
        if (scoreItemForm.getNote() == null) {
            scoreItem.setNote("无");
        }else{
            scoreItem.setNote(scoreItem.getNote());
        }
        int id= (int) session.getAttribute("activityId");
        scoreItem.setActivityId(id);

        if (scoreItem==null){
            log.error("[登陆]账号密码错误");
            throw new ScoreItemException(ScoreItemEnum.SCOREITEM_ERROR);
        }
        list.add(scoreItem);
        session.setAttribute("list",list);
        return ResultVOUtil.success(list);
    }
}
