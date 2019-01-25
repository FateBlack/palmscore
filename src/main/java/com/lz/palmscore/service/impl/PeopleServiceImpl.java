package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.*;
import com.lz.palmscore.repository.*;
import com.lz.palmscore.service.PeopleService;
import com.lz.palmscore.vo.MarkPageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 白 on 2018/12/12.
 */

@Service
@Slf4j
public class PeopleServiceImpl implements PeopleService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RaterRepository raterRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ScoreItemRepository scoreItemRepository;

    @Autowired
    private PlayerFileRepository playerFileRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private RaterScoreRepository raterScoreRepository;

    @Autowired
    private PlayerScoreitemRepository playerScoreitemRepository;

    @Autowired
    private GroupInfoRepository groupInfoRepository;

    @Override
    public List<Rater> batchInputRater(String fileName, MultipartFile file,Integer category) throws Exception {
        boolean notNull = false;
        List<Rater> raterList = new ArrayList<Rater>();


        boolean isExcel2003 = true;

        InputStream is = file.getInputStream();
        Workbook wb = null;

        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            Rater rater = new Rater();
            if (row == null){
                continue;
            }

            rater = raterExcelGet(row, rater);

            rater.setCategory(category);

            raterList.add(rater);
        }

        return raterList;
    }

    /**
     *
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public List<Player> batchInputPlayer(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        List<Player> playerList = new ArrayList<>();

        boolean isExcel2003 = true;

        InputStream is = file.getInputStream();
        Workbook wb = null;

        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            Player player = new Player();
            if (row == null){
                continue;
            }

            player = playerExcelGet(row, player);

            playerList.add(player);
        }


        return playerList;
    }


    /**
     * 选手抽签  实际上在创建活动时就已经随机排序，此处无需随机
     * @return
     */
    @Override
    public List<Player> drawlots() {
        return playerRepository.findAll();
    }

    /**
     *
     * 评委抽签
     * @return
     */
    @Override
    public List<Rater> reDrawlots() {
        return raterRepository.findAll();
    }



    /**
     *   PC 端结果通知
     * @return
     */
    @Override
    public List<List<String>> result() {

        List<List<String>> finalList = new ArrayList<>();


        //总分
        List<String> z = new ArrayList<>();
        z.add("最后得分");



        //第一行
        List<String> a = new ArrayList();
        a.add("评委号\\选手 ");

        List<Player> playerList = playerRepository.findAll();
        for (Player player : playerList) {
            //加入 选手名 ， 总分 ， 排名
            a.add(player.getName());
            z.add(String.valueOf(player.getTotalScore()));
        }
        finalList.add(a);

        //评委打分
        List<Rater> raterList = raterRepository.findAll();
        for (int i = 0; i < raterList.size(); i++) {

            List<String> b = new ArrayList<>();
            b.add(String.valueOf(i + 1));

            List<RaterScore> raterScoreList = raterScoreRepository.findByRaterId(raterList.get(i).getId());
            for (RaterScore raterScore : raterScoreList) {
                b.add(String.valueOf(raterScore.getScore()));
            }
            finalList.add(b);
        }

        List<ScoreItem> scoreItemList = scoreItemRepository.findAll();

        // 评分项
        for (ScoreItem scoreItem : scoreItemList) {
            List<String> c = new ArrayList<>();
            c.add(scoreItem.getName());

            Double rate = scoreItem.getRate();

            for (Player player : playerList) {
                Double totalScore = player.getTotalScore();

                if (totalScore != null && totalScore != 0) {
                    Double score = totalScore * scoreItem.getRate();
                    BigDecimal b = new BigDecimal(score);
                    score = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();

                    c.add(String.valueOf(score));
                }else {
                    c.add(null);
                }


            }

            finalList.add(c);
        }


        finalList.add(z);



        // 开始 添加排名
        List<String> rankList = new ArrayList<>();
        rankList.add("当前排名");

        //先按照分数排名 放入名次
        Collections.sort(playerList, new Comparator<Player>() {

            @Override
            public int compare(Player o1, Player o2) {
                Double x = o1.getTotalScore();
                Double y = o2.getTotalScore();

                if (x == null) {
                    x = 0D;
                }

                if (y == null) {
                    y = 0D;

                }
                return y.compareTo(x);
            }
        });

        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).setRank(i + 1);
        }

        //再按照 顺序排名 复原
        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getOrders().compareTo(o2.getOrders());
            }
        });

        for (Player p : playerList) {
            rankList.add(String.valueOf(p.getRank()));
        }

        finalList.add(rankList);

        return finalList;
    }



    /**
     *
     * 显示评委打分页面
     * @return
     */
    @Override
    public List<MarkPageVO> markPage(Integer playerId,Integer raterId) {

        List<ScoreItem> list = scoreItemRepository.findAll();

        List<PlayerScoreitem> playerScoreitemList = playerScoreitemRepository.findByPlayerIdAndRaterId(playerId,raterId);


        Double fileScore = null;
        if (playerScoreitemList != null && !playerScoreitemList.isEmpty()) {
            fileScore = playerScoreitemList.get(0).getScore();
        }

        List<MarkPageVO> markPageVOList = new ArrayList<>();
        for (ScoreItem scoreItem : list) {

            MarkPageVO markPageVO =
                    new MarkPageVO(scoreItem.getId(), scoreItem.getName(), scoreItem.getRate(), scoreItem.getFileUpload());

            // 填充 教案分数
            if (scoreItem.getFileUpload() == 1) {
                markPageVO.setScore(fileScore);
            }

            markPageVOList.add(markPageVO);
        }
        return markPageVOList;
    }

    /**
     * 评委提前打分页面
      * @return
     */
    @Override
    public List<MarkPageVO> markPageAhead() {
        List<ScoreItem> list = scoreItemRepository.findByFileUpload(1);
        ScoreItem scoreItem = list.get(0);

        MarkPageVO markPageVO =
                new MarkPageVO(scoreItem.getId(), scoreItem.getName(), scoreItem.getRate(), scoreItem.getFileUpload());

        List<MarkPageVO> markPageVOList = new ArrayList<>();
        markPageVOList.add(markPageVO);
        return markPageVOList;
    }

    /**
     * 评委登陆
     * @param account
     * @param password
     * @return
     */
    @Override
    public List<Rater> rlogin(String account, String password) {
        return raterRepository.findByRIdAndPassword(account, password);
    }

    /**
     *选手登陆
     * @param account
     * @param password
     * @return
     */
    @Override
    public List<Player> plogin(String account, String password) {
        return playerRepository.findByPIdAndPassword(account,password);
    }


    /**
     * 通过选手id批量查询
     * @param playIdList
     * @return
     */
    @Override
    public List<Player> findByList(List<Integer> playIdList) {
        String sql="";
        SqlParameterSource[] list = SqlParameterSourceUtils.createBatch(playIdList.toArray());
        return null;
    }





    public Player updatePlayerPassword(Integer id,String password,String rePassword) {

        Player p = playerRepository.getOne(id);
        String realPas = p.getPassword();
        if (!realPas.equals(password)) {//原始密码相等
            return null;
        }
        p.setPassword(rePassword);
        Player player1 = playerRepository.save(p);

        return player1;
    }

    //更换密码
    public Rater updateRaterPassword(Integer id,String password,String rePassword) {

        Rater r = raterRepository.getOne(id);
        String realPas = r.getPassword();
        //if (realPas==password) {//这种不对啊啊啊啊啊啊啊！！！！！
        if (!realPas.equals(password)) {//原始密码不相等
            return null;
        }
        r.setPassword(rePassword);
        Rater rater1 = raterRepository.save(r);

        return rater1;
    }

    // 添加额外评委到数据库
    @Transactional
    @Override
    public void saveExtraRater(Double extraRate, List<Rater> extraRaterList) {
        List<Activity> activityList = activityRepository.findAll();
        if (activityList.size() <= 0) {
            return;
        }

        Activity activity = activityList.get(0);
        //加入额外评委评分比
        activity.setExtraRate(extraRate);
        activityRepository.save(activity);

        Integer groupNum = activity.getGroupNum();

        int extraCount = extraRaterList.size() / groupNum;

        List<Rater> finalList = new ArrayList<>();
        for (int i = 1; i <= groupNum; i++) {
            List<Rater> raterListB = new ArrayList<>();

            for (int j = 1; j <= extraCount; j++) {
                int s = extraCount * (i - 1) + j - 1;
                Rater rater = extraRaterList.get(s);
                rater.setGroups(i);
                raterListB.add(rater);
            }
            finalList.addAll(raterListB); //直接放入最终集合
        }

        List<GroupInfo> groupInfoList = groupInfoRepository.findAll();

        // 将额外评委数量加入 各组信息中
        for (GroupInfo groupInfo : groupInfoList) {
            Integer raterCount = groupInfo.getRaterCount() + extraCount;
            groupInfo.setRaterCount(raterCount);
            groupInfoRepository.save(groupInfo);
        }

        String sqlR = "INSERT INTO rater(r_id,name,job,workplace,activity_id,groups,category)" +
                " VALUES (:rId,:name,:job,:workplace,:activityId,:groups,:category)";

        SqlParameterSource[] beanSourcesR = SqlParameterSourceUtils.createBatch(finalList.toArray());
        namedParameterJdbcTemplate.batchUpdate(sqlR, beanSourcesR);
    }




    /**
     *  获取 评委 excel 列中的数据
     * @param row
     * @param rater
     * @return
     */
    private Rater raterExcelGet(Row row,Rater rater) {

        // 将列中的内容都设置成String类型格式 row.getCell(要设置的列数，从0开始).setCellType(Cell.CELL_TYPE_STRING);
        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);

        String rId = row.getCell(0).getStringCellValue();
        if(rId == null || rId.isEmpty()){
            rId = "0";
        }
        rater.setRId(rId);

        String name = row.getCell(1).getStringCellValue();
        if(name==null || name.isEmpty()){
            name = "0";
        }
        rater.setName(name);

        String workplace = row.getCell(2).getStringCellValue();
        if(workplace==null|| workplace.isEmpty()){
            workplace = "0";
        }
        rater.setWorkplace(workplace);

        String job = row.getCell(3).getStringCellValue();
        if(job==null|| job.isEmpty()){
            job = "0";
        }
        rater.setJob(job);

        return  rater;
    }

    /**
     *  获取 选手 excel列 数据
     * @param row
     * @param player
     * @return
     */

    private Player playerExcelGet(Row row,Player player) {

        // 将列中的内容都设置成String类型格式 row.getCell(要设置的列数，0).setCellType(Cell.CELL_TYPE_STRING);
        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
        String pId = row.getCell(0).getStringCellValue();
//        if(pId == null || pId.isEmpty()){
//            pId = "0";
//        }
        player.setPId(pId);

        String name = row.getCell(1).getStringCellValue();
//        if(name==null || name.isEmpty()){
//            name = "0";
//        }
        player.setName(name);

        String workplace = row.getCell(2).getStringCellValue();
//        if(workplace==null|| workplace.isEmpty()){
//            workplace = "0";
//        }
        player.setWorkplace(workplace);

        String job = row.getCell(3).getStringCellValue();

        player.setJob(job);

        String course = row.getCell(4).getStringCellValue();
//        if(course==null|| course.isEmpty()){
//            course = "0";
//        }
        player.setCourse(course);

        row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
        String groupString = row.getCell(5).getStringCellValue();
        if(groupString==null|| groupString.isEmpty()){
            groupString = "1";
        }
        Integer group = null;

        try {
            group = Integer.parseInt(groupString);
        } catch (Exception e) {
            log.info("选手组别必须为整型数字");
        }

        player.setGroups(group);
        return  player;
    }
}
