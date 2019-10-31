package com.lz.palmscore.controller;

import com.lz.palmscore.Conventer.ScoreItemForm2ScoreItemConventer;
import com.lz.palmscore.entity.ScoreItem;
import com.lz.palmscore.enums.ScoreItemEnum;
import com.lz.palmscore.enums.SessionEnum;
import com.lz.palmscore.exception.ScoreItemException;
import com.lz.palmscore.form.ScoreItemForm;
import com.lz.palmscore.service.ScoreItemService;
import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 评分细则
 */

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
        List<ScoreItem> list = (List<ScoreItem>) session.getAttribute(SessionEnum.PERSON_LIST.getName());
        if(list == null){
            list = new ArrayList<>();
        }

        if (bindingResult.hasErrors()) {
           // log.error("[评分项]格式错误");
            return ResultVOUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        ScoreItem scoreItem=ScoreItemForm2ScoreItemConventer.conventer(scoreItemForm);
        Integer id = (Integer) session.getAttribute(SessionEnum.ACTIVITY_ID.getName());
        scoreItem.setActivityId(id);
        if (scoreItem==null){
            log.error("[评分项]添加错误");
            throw new ScoreItemException(ScoreItemEnum.SCOREITEM_ERROR);
        }
        list.add(scoreItem);
        session.setAttribute(SessionEnum.PERSON_LIST.getName(), list);
        return ResultVOUtil.success(list);
    }
    /**
     * 删除订单项
     * @param index
     * @param session
     * @return
     */
   @PostMapping("score_item_delete")
    public ResultVO ScoreItemDelete(@RequestParam int index,
                                    HttpSession  session){
       List<ScoreItem> list = (List<ScoreItem>) session.getAttribute(SessionEnum.PERSON_LIST.getName());
       list.remove(index);
       session.setAttribute(SessionEnum.PERSON_LIST.getName(), list);
       return ResultVOUtil.success();
    }

    /**
     * 修改评分项
     * @param index
     * @param scoreItemForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("score_item_edit")
    public ResultVO ScoreItemEdit(@RequestParam int index,
                                  @Valid ScoreItemForm scoreItemForm,
                                  BindingResult bindingResult,
                                  HttpSession session){
        if (bindingResult.hasErrors()) {
          //  log.error("[修改]格式错误");
            return ResultVOUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        List<ScoreItem> list = (List<ScoreItem>) session.getAttribute(SessionEnum.PERSON_LIST.getName());
        list.get(index).setName(scoreItemForm.getName());
        list.get(index).setRate(scoreItemForm.getRate());
        list.get(index).setFileUpload(scoreItemForm.getFileUpload());
        session.setAttribute(SessionEnum.PERSON_LIST.getName(), list);
        return  ResultVOUtil.success(list);

    }
}
