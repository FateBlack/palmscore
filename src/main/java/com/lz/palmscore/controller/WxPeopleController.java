package com.lz.palmscore.controller;

import com.lz.palmscore.util.ResultVOUtil;
import com.lz.palmscore.vo.RankVO;
    import com.lz.palmscore.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        if (account.equals("b") && password.equals("123")){
            type = 2;
            id = 2;
        }

        map.put("type", type);
        map.put("id", id);
        map.put("groups", 3);

        return ResultVOUtil.success(map);
    }

    /**
     * 微信端 评分结果排名
     * @param groups 组别
     * @return
     */
    @GetMapping("rank")
    public ResultVO rank(@RequestParam("groups") String groups) {
        List<RankVO> rankList = new ArrayList<>();
        rankList.add(new RankVO(3, "老牛头", 88.4, 1));
        rankList.add(new RankVO(6, "八百", 77.9, 2));
        rankList.add(new RankVO(1, "分发", 66.89, 3));
        rankList.add(new RankVO(2, "胖胖", 55.22, 4));

        return ResultVOUtil.success(rankList);
    }

}
