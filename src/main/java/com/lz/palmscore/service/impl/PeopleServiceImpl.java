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
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public List<Rater> batchInputRater(String fileName, MultipartFile file) throws Exception {
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
     * 抽签  实际上在创建活动时就已经随机排序，此处无需随机
     * @return
     */
    @Override
    public List<List<Player>> drawLots() {

        List<List<Player>> playerList = new ArrayList<>();

        List<Activity> activityList = activityRepository.findAll();
        Integer groupNum = activityList.get(0).getGroupNum();

        try {
            for (int i = 1; i <= groupNum; i++) {
                List<Player> list = playerRepository.findByGroups(i);
                playerList.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playerList;
    }

    /**
     *
     * 显示评委打分页面
     * @return
     */
    @Override
    public List<ScoreItem> getAll() {
        return scoreItemRepository.findAll();
    }

    /**
     * 评委登陆
     * @param account
     * @param password
     * @return
     */
    @Override
    public List<Rater> rlogin(String account, String password) {
        return raterRepository.findByRIdAndPassword( account, password);
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
     * 储存选手文件路径
     * @param pf
     * @return
     */
    @Override
    public PlayerFile savefile(PlayerFile pf) {
        return playerFileRepository.save(pf);
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


    /**
     * 通过id查询评委
     * @param id
     * @return
     */
    @Override
    public Rater findById(int id) {
        return raterRepository.getOne(id);
    }


    /**
     * 通过id查询选手
     * @param id
     * @return
     */
    @Override
    public Player findById2(int id) {
        return playerRepository.getOne(id);
    }

    /**
     * 修改评委密码
     * @param rater
     * @return
     */
    @Override
    public Rater updateById(Rater rater) {
        return raterRepository.save(rater);
    }

    /**
     * 修改评委密码
     * @param player
     * @return
     */
    @Override
    public Player updateById2(Player player) {
        return playerRepository.save(player);
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

        // 将列中的内容都设置成String类型格式 row.getCell(要设置的列数，从0开始).setCellType(Cell.CELL_TYPE_STRING);
        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);

        String pId = row.getCell(0).getStringCellValue();
        if(pId == null || pId.isEmpty()){
            pId = "0";
        }
        player.setPId(pId);

        String name = row.getCell(1).getStringCellValue();
        if(name==null || name.isEmpty()){
            name = "0";
        }
        player.setName(name);

        String workplace = row.getCell(2).getStringCellValue();
        if(workplace==null|| workplace.isEmpty()){
            workplace = "0";
        }
        player.setWorkplace(workplace);

        String course = row.getCell(3).getStringCellValue();
        if(course==null|| course.isEmpty()){
            course = "0";
        }
        player.setCourse(course);

        String groupString = row.getCell(5).getStringCellValue();
        if(groupString==null|| groupString.isEmpty()){
            groupString = "0";
        }
        Integer group = 1;

        try {
            group = Integer.parseInt(groupString);
        } catch (Exception e) {
            log.info("选手组别必须为整型数字");
        }

        player.setGroups(group);
        return  player;
    }
}
