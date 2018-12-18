package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.Player;
import com.lz.palmscore.entity.Rater;
import com.lz.palmscore.enums.FileEnum;
import com.lz.palmscore.exception.FileException;
import com.lz.palmscore.service.PeopleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 白 on 2018/12/12.
 */

@Service
@Slf4j
public class PeopleServiceImpl implements PeopleService {


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
