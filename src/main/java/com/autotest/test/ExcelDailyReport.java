package com.autotest.test;

import com.autotest.driver.impl.WebAPI;
import com.autotest.model.DailyIssue;
import com.autotest.utils.ExcelUtil;
import com.autotest.utils.FileUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z1245 on 11/30/2017.
 */
public class ExcelDailyReport extends WebAPI {

    List<String> problemFeature = new ArrayList<>();
    List<DailyIssue> list = new ArrayList<DailyIssue>();

    ExcelDailyReport() {
        super();
    }

    @BeforeTest
    public void setUp(){
        String sharedPath = FileUtil.createDir("reports");
        setSharedPath(sharedPath);
    }

    @Test(priority =1, description ="Login local Jenkins")
    public void test1(){
        startBrowser("chromedriver"); //
        get("http://10.58.1.46:8080"); //
        sendKeys("//input[@id='j_username']","0","techrefresh_readonly"); //
        sendKeys("//input[@name='j_password']","0","1234pass"); //
        click("//button[@id='yui-gen1-button']","0"); //
    }

    @Test(priority = 2, description = "get performance cucumber result")
    public void test2() {
        get("http://10.58.1.46:8080/job/selenium-test-fullNightly/"); //
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/a","0"); //
        click("//a[contains(text(),      'Cucumber reports')]", "0");
        sleep("5"); //
        click("//*[text()='Status']", "0");
    }

    @Test(priority = 3, description = "get fail feature")
    public void test3() {
        for(int i=1;;i++) {
            if (getText(ExcelUtil.tableBodyCellElement("tablesorter", i, 12), "0", null).equals("Failed")) {
                problemFeature.add(getText(ExcelUtil.tableBodyCellElement("tablesorter", i, 1), "0", null));
            } else {
                System.out.println(i);
                break;
            }
        }
        System.out.println(problemFeature);
    }

    @Test(priority = 4, description = "get performance cucumber result")
    public void test4(){
        quit(); //
    }

    @Test(priority = 5, description ="read the excel")
    public void test5() {
        XSSFWorkbook xssfWorkbook = null;
        try {
            InputStream is = new FileInputStream("D:\\Users\\z1115\\Projects\\reports\\dailyIssue.xlsx");
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DailyIssue dailyIssue = null;

        if(xssfWorkbook!=null){
            // Read the Sheet
            for (int numSheet = 0; numSheet < 1; numSheet++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(xssfWorkbook.getSheetIndex("20171130"));
                if (xssfSheet == null) {
                    continue;
                }
                // Read the Row
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null && !ExcelUtil.getValue(xssfRow.getCell(0)).equals("")) {
                        dailyIssue = new DailyIssue();
                        XSSFCell item = xssfRow.getCell(0);
                        XSSFCell feature = xssfRow.getCell(1);
                        XSSFCell type = xssfRow.getCell(2);
                        XSSFCell description = xssfRow.getCell(3);
                        XSSFCell remark = xssfRow.getCell(4);
                        dailyIssue.setItem(ExcelUtil.getValue(item));
                        dailyIssue.setFeature(ExcelUtil.getValue(feature));
                        dailyIssue.setType(ExcelUtil.getValue(type));
                        dailyIssue.setDescription(ExcelUtil.getValue(description));
                        dailyIssue.setRemark(ExcelUtil.getValue(remark));
                        list.add(dailyIssue);
                        System.out.println(dailyIssue.toString());
                    }
                }
            }

        }
        /*System.out.println(JSON.toJSONString(list));*/

    }

    @Test(priority = 6, description = "add excel sheet")
    public void test6() throws Exception {
        List<DailyIssue> newdailyIssues = new ArrayList<>();
        XSSFWorkbook workbook = (XSSFWorkbook)ExcelUtil.createWb("D:\\Users\\z1115\\Projects\\reports\\dailyIssue.xlsx");
        ExcelUtil.newSheet(workbook, "D:\\Users\\z1115\\Projects\\reports\\dailyIssue.xlsx", ExcelUtil.getSheetDate());
        XSSFSheet sheet = (XSSFSheet)ExcelUtil.getSheet(workbook, ExcelUtil.getSheetDate());
        sheet.autoSizeColumn((short)0);
        System.out.println(problemFeature.size());
        System.out.println(list.size());
        for (int i=0; i<problemFeature.size(); i++) {
            for (int j=0; j<list.size(); j++) {
                if(problemFeature.get(i).trim().equals(list.get(j).getFeature().trim())) {
                    newdailyIssues.add(list.get(j));
                    System.out.println(list.get(j));
                    break;
                }
            }
            if(!newdailyIssues.contains(new DailyIssue("", problemFeature.get(i), "", "", ""))) {
                newdailyIssues.add(new DailyIssue("", problemFeature.get(i), "", "", ""));
            }
        }

        System.out.println(newdailyIssues);
        for  ( int  i  =   0 ; i  <  newdailyIssues.size()  -   1 ; i ++ )   {
            for  ( int  j  =  newdailyIssues.size()  -   1 ; j  >  i; j -- )   {
                if  (newdailyIssues.get(j).getFeature().equals(newdailyIssues.get(i).getFeature()))   {
                    newdailyIssues.remove(j);
                }
            }

        }
        System.out.println("------------------------------");
        System.out.println(newdailyIssues);
        ExcelUtil.dropDownList(sheet,"D:\\Users\\z1115\\Projects\\reports\\dailyIssue.xlsx");
        CellStyle cellStyle = workbook.createCellStyle();
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        XSSFFont xssfFont = workbook.createFont();
        xssfFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.index);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(xssfFont);
        ExcelUtil.insertExcel(1,1, "Item", sheet,cellStyle);
        ExcelUtil.insertExcel(1,2, "Feature", sheet,cellStyle);
        ExcelUtil.insertExcel(1,3, "Failure type", sheet,cellStyle);
        ExcelUtil.insertExcel(1,4, "description", sheet,cellStyle);
        ExcelUtil.insertExcel(1,5, "Defect ID/Fix State", sheet,cellStyle);
        for(int i=0; i<newdailyIssues.size(); i++) {
            XSSFRow xssfRow = sheet.createRow(i+1);
            for(int j=0; j<5; j++) {
                XSSFCell xssfCell = xssfRow.createCell(j);
                switch (j) {
                    case 0:xssfCell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);xssfCell.setCellStyle(cellStyle2);sheet.autoSizeColumn((short)j); xssfCell.setCellValue(i+1);break;
                    case 1:xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);sheet.setColumnWidth((short)j,100*256); xssfCell.setCellValue(newdailyIssues.get(i).getFeature());break;
                    case 2:xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);sheet.autoSizeColumn((short)j);xssfCell.setCellStyle(cellStyle2); xssfCell.setCellValue(newdailyIssues.get(i).getType());break;
                    case 3:xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);sheet.setColumnWidth((short)j,70*256); xssfCell.setCellValue(newdailyIssues.get(i).getDescription());break;
                    case 4:xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);sheet.autoSizeColumn((short)j);xssfCell.setCellStyle(cellStyle2); xssfCell.setCellValue(newdailyIssues.get(i).getRemark());break;
                    default:break;
                }
            }
        }
        FileOutputStream fos = new FileOutputStream("D:\\Users\\z1115\\Projects\\reports\\dailyIssue.xlsx");
        workbook.write(fos);
        fos.flush();
        System.out.println("生成文件成功");
        fos.close();


    }

}
