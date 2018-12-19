package com.lz.palmscore.controller;

        import com.lz.palmscore.entity.Player;
        import com.lz.palmscore.entity.Rater;
        import com.lz.palmscore.service.PeopleService;
        import com.lz.palmscore.util.ResultVOUtil;
        import com.lz.palmscore.vo.RankVO;
        import com.lz.palmscore.vo.ResultVO;
        import jdk.internal.dynalink.linker.LinkerServices;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PeopleService peopleService;

    /**
     * 微信端登陆
     *
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
        map.put("groups", 3);

        return ResultVOUtil.success(map);
    }

    /**
     * 微信端 评分结果排名
     *
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

    /**
     * 微信端 修改密码
     *
     * @param type
     * @param id
     * @param password
     * @param rePassword
     * @return
     */
    @PostMapping("password_edit")
    public ResultVO password_edit(@RequestParam("type") int type, @RequestParam("id") int id,
                                  @RequestParam("password") String password, @RequestParam("rePassword") String rePassword) {
        if (type == 1) { //评委
            Rater rater = peopleService.findById(id);
            String realPas = rater.getPassword();
            System.out.println(realPas);
            //if (realPas==password) {//这种不对啊啊啊啊啊啊啊！！！！！
              if (!realPas.equals(password)) {//原始密码不相等
                  return ResultVOUtil.error(510,"原密码输入不正确");
            }
            rater.setPassword(rePassword);
            Rater rater1 = peopleService.updateById(rater);
        }
        if (type == 2) {//选手
            Player player = peopleService.findById2(id);
            String realPas = player.getPassword();
            if (!realPas.equals(password)) {//原始密码相等
                return ResultVOUtil.error(510,"原密码输入不正确");
            }
            player.setPassword(rePassword);
            Player player1 = peopleService.updateById2(player);
        }
        return ResultVOUtil.success();
    }
}
