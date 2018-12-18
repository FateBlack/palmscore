package com.lz.palmscore.controller;

import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 白 on 2018/12/16.
 */

@RestController
@RequestMapping("/wx/people")
@Slf4j
public class WxPeopleController {

    /**
     * 微信端登陆
     * @param account
     * @param password
     * @return
     */
    @PostMapping("login")
    public ResultVO peopleLogin(@RequestParam("account") String account, @RequestParam("password") String password) {

        Map map = new HashMap();

        int type = 666;
        int id = 999;
        if (account.equals("a") && password.equals("123")) {
            type = 1;
            id = 1;
        }
        if (account.equals("b") && password.equals("123")) {
            type = 2;
            id = 2;
        }

        map.put("type", type);
        map.put("id", id);

        return ResultVOUtil.success(map);
    }
}
