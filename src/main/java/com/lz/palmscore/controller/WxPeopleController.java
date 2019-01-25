package com.lz.palmscore.controller;

        import com.lz.palmscore.entity.Player;
        import com.lz.palmscore.entity.PlayerScoreitem;
        import com.lz.palmscore.entity.Rater;
        import com.lz.palmscore.repository.PlayerScoreitemRepository;
        import com.lz.palmscore.service.PeopleService;
        import com.lz.palmscore.service.PlayerService;
        import com.lz.palmscore.util.ResultVOUtil;
        import com.lz.palmscore.vo.RankVO;
        import com.lz.palmscore.vo.ResultVO;
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

    @Autowired
    PlayerScoreitemRepository playerScoreitemRepository;

    @Autowired
    PlayerService playerService;

    /**
     * 微信端登陆
     * @param account
     * @param password
     * @return
     */
    @PostMapping("login")
    public ResultVO peopleLogin(@RequestParam("types")Integer type,@RequestParam("account") String account,
                                @RequestParam("password") String password) {

        Map map = new HashMap();
        //
        if(type==1){//评委
            List<Rater> list=peopleService.rlogin(account,password);
            if (list == null || list.isEmpty()) {
                return ResultVOUtil.error(111, "登陆失败");
            }
            map.put("types", 1);
            map.put("id", list.get(0).getId());
            map.put("groups",list.get(0).getGroups());
            map.put("activity_id", list.get(0).getActivityId());
            return ResultVOUtil.success(map);
        }
        if(type==2){
            List<Player> list=peopleService.plogin(account,password);
            if(list==null||list.isEmpty()){
                return ResultVOUtil.error(111, "登陆失败");
            }
            map.put("types", 2);
            map.put("id", list.get(0).getId());
            map.put("groups",list.get(0).getGroups());
            map.put("activity_id", list.get(0).getActivityId());
            return ResultVOUtil.success(map);
        }
        return ResultVOUtil.error(444,"登陆失败");
    }

    /**
     * 微信端 评分结果排名
     * @param groups 组别
     * @return
     */
    @GetMapping("rank")
    public ResultVO rank(@RequestParam("groups") Integer groups) {
        List<RankVO> rankList = new ArrayList<>();
        List<Player> playerList = playerService.findByGroups(groups);

        try {
            int i=playerList.size();
            for(int a=0;a<i;a++){
                rankList.add(new RankVO(playerList.get(a).getOrders(), playerList.get(a).getName(), playerList.get(a).getTotalScore(), a+1));
            }
        } catch (Exception e) {
            return ResultVOUtil.error(233, "评分报错");
        }


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
    public ResultVO password_edit(@RequestParam("types") int type, @RequestParam("id") int id,
                                  @RequestParam("password") String password, @RequestParam("rePassword") String rePassword) {
        if (type == 1) { //评委

            Rater rater1 = peopleService.updateRaterPassword(id,password,rePassword);
            if (rater1 == null || rater1.getId() == null) {
                return ResultVOUtil.error(844, "原始密码错误");
            }
        }
        if (type == 2) {//选手
            Player player1 = peopleService.updatePlayerPassword(id,password,rePassword);
            return ResultVOUtil.error(844, "原始密码错误");
        }
        return ResultVOUtil.success();

    }


    /**
     * 选手详细得分情况  评委方面
     *
     * @param playerId
     * @return
     */
    @GetMapping("score_info")
    public ResultVO scoreInfo(@RequestParam("player_id") Integer playerId,
                              @RequestParam("rater_id") Integer raterId) {

        List<PlayerScoreitem> playerScoreitems =
                playerScoreitemRepository.findByPlayerIdAndRaterId(playerId, raterId);
        return ResultVOUtil.success(playerScoreitems);
    }

}
