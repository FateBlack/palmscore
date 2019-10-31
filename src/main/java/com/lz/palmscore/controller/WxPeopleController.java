package com.lz.palmscore.controller;

        import com.lz.palmscore.entity.Player;
        import com.lz.palmscore.entity.PlayerScoreitem;
        import com.lz.palmscore.entity.Rater;
        import com.lz.palmscore.enums.PersonTypeEnum;
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
        if (type == PersonTypeEnum.RATER.getType()) {//评委
            List<Rater> list = peopleService.rlogin(account, password);
            if (list == null || list.isEmpty()) {
                return ResultVOUtil.error("登陆失败");
            }
            Rater rater = list.get(0);
            map.put("types", PersonTypeEnum.RATER.getType());
            map.put("id", rater.getId());
            map.put("groups", rater.getGroups());
            map.put("activity_id", rater.getActivityId());
            return ResultVOUtil.success(map);
        }
        if (type == PersonTypeEnum.PLAYER.getType()) {
            List<Player> list = peopleService.plogin(account, password);
            if (list == null || list.isEmpty()) {
                return ResultVOUtil.error("登陆失败");
            }
            Player player = list.get(0);
            map.put("types", PersonTypeEnum.PLAYER.getType());
            map.put("id", player.getId());
            map.put("groups", player.getGroups());
            map.put("activity_id", player.getActivityId());
            return ResultVOUtil.success(map);
        }
        return ResultVOUtil.error("登陆失败");
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
            for(int j=0;j<i;j++){
                rankList.add(new RankVO(playerList.get(j).getOrders(), playerList.get(j).getName(), playerList.get(j).getTotalScore(), j+1));
            }
        } catch (Exception e) {
            return ResultVOUtil.error("评分报错");
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
        if (type == PersonTypeEnum.RATER.getType()) { //评委
            Rater rater = peopleService.updateRaterPassword(id,password,rePassword);
            if (rater == null || rater.getId() == null) {
                return ResultVOUtil.error( "原始密码错误");
            }
        }
        if (type == PersonTypeEnum.PLAYER.getType()) {//选手
            Player player = peopleService.updatePlayerPassword(id,password,rePassword);
            return ResultVOUtil.error("原始密码错误");
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
