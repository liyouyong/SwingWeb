package com.autotest.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by z1245 on 10/27/2017.
 */
public class ExcelUtil {

    public static void insertExcel(int row, int column, String text, XSSFSheet sheet) {

        XSSFRow xssfRow = null;
        XSSFCell xssfCell = null;

        if(sheet.getRow(row-1) == null) {
            xssfRow = sheet.createRow(row-1);
        }
        if(xssfRow.getCell(column-1) == null) {
            xssfCell = xssfRow.createCell(column-1);
        }
        xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);
        xssfCell.setCellValue(text);
    }

    public static void shiftColumn(XSSFSheet sheet,int startColumn,int endColumn, int iStartRow, int iLastRow, int moveCol){
        XSSFCell cellNew = null;
        XSSFCell cellPer = null;
        XSSFRow rowCurr = null;
        if(moveCol > 0) {
            for (int j = iStartRow - 1; j <= iLastRow - 1; j++) {
                rowCurr = sheet.getRow(j);
                for (int k = endColumn; k >= startColumn; k--) {
                    cellNew = rowCurr.createCell(k - 1 + moveCol);
                    cellPer = rowCurr.getCell(k - 1);
                    if (cellPer == null) {
                        continue;
                    }
                    cellNew.setCellStyle(cellPer.getCellStyle());
                    cellNew.setCellValue(cellPer.getStringCellValue());
                    cellPer.setCellValue("");
                }
            }
        }

        if(moveCol < 0) {
            for (int j = iStartRow - 1; j <= iLastRow - 1; j++) {
                rowCurr = sheet.getRow(j);
                for (int k = startColumn; k <= endColumn; k++) {
                    cellNew = rowCurr.createCell(k - 1 + moveCol);
                    cellPer = rowCurr.getCell(k - 1);
                    switch (cellPer.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            cellNew.setCellType(XSSFCell.CELL_TYPE_STRING);
                            cellPer.setCellType(XSSFCell.CELL_TYPE_STRING);
                            if (cellPer == null) {
                                continue;
                            }
                            cellNew.setCellValue(cellPer.getStringCellValue());
                            cellNew.setCellStyle(cellPer.getCellStyle());
                            cellPer.setCellValue("");
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            cellNew.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                            cellPer.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                            if (cellPer == null) {
                                continue;
                            }
                            cellNew.setCellValue(cellPer.getNumericCellValue());
                            cellNew.setCellStyle(cellPer.getCellStyle());
                            cellPer.setCellValue("");
                            break;
                        default: break;
                    }
                }
            }

        }
    }

    public static void insertColoum(List<String> list, int insertCol, int firstRow, int lastRow, XSSFSheet sheet) {
        int j = 0;
        for(int i=firstRow; i<=lastRow; i++) {
            modifyCell(sheet, list.get(j),i, insertCol);
            j += 1;
        }
    }

    public static void deleteColoumContent(int firstRow, int lastRow, int firstColoum, int lastColoum, XSSFSheet sheet) {
        for (int i=firstRow-1; i<=lastRow-1; i++) {
            XSSFRow row = sheet.getRow(i);
            for(int j=firstColoum-1; j<=lastColoum-1; j++) {
                XSSFCell cell = row.getCell(j);
                cell.setCellValue("");
            }
        }
    }


    public static void mergeCells(int firstRow, int lastRow, int fistCol, int lastCol, XSSFSheet sheet, String text) {
        XSSFRow row = sheet.createRow(firstRow -1);
        XSSFCell cell = row.createCell(fistCol -1);
        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(text);
        sheet.addMergedRegion(new CellRangeAddress(firstRow-1,lastRow-1,fistCol-1,lastCol-1));
    }

    public static void modifyCell(XSSFSheet sheet, String content, int rowNum, int colNum) {
        XSSFRow row = sheet.getRow(rowNum-1);
        XSSFCell cell = row.getCell(colNum-1);
        /*cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);*/
        cell.setCellValue(content);
    }

    public static void deleteRow(XSSFSheet sheet, int rowNum) {
        int lastRowNum = sheet.getLastRowNum();
        sheet.shiftRows(rowNum-1, lastRowNum, -1);
    }

    public static Workbook createWb(String filePath) throws IOException {
        if(filePath.trim().toLowerCase().endsWith("xls")) {
            return new HSSFWorkbook(new FileInputStream(filePath)) ;
        } else if(filePath.trim().toLowerCase().endsWith("xlsx")) {
            return new XSSFWorkbook(new FileInputStream(filePath)) ;
        } else {
            throw new IllegalArgumentException("不支持除：xls/xlsx以外的文件格式!!!") ;
        }
    }

    public static final Sheet getSheet(Workbook wb , String sheetName) {
        return wb.getSheet(sheetName) ;

    }

    public static final Sheet getSheet(Workbook wb ,int index) {
        return wb.getSheetAt(index) ;
    }

    public static void createTrackQA(String filePath,String[] args){
        XSSFWorkbook workbook = null;
        try {
            workbook = (XSSFWorkbook) createWb(filePath);
            XSSFSheet sheet = (XSSFSheet)ExcelUtil.getSheet(workbook, "sheet1");
            List<String> list = Arrays.asList(args[0],args[1],args[2],args[3],args[4],args[5],args[6],args[7], args[8]);
            ExcelUtil.deleteColoumContent(2,10, 3,3, sheet);
            ExcelUtil.shiftColumn(sheet, 4, 7, 2,10 , -1);
            ExcelUtil.insertColoum(list,7, 2, 10, sheet);
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.flush();
            System.out.println("生成文件成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        String patronRating ="0%";
        String itamdinRating ="0%";
        String loyaltyRating ="0%";
        String creditControlRating ="0%";
        String promotionRating = "0%";
        String all ="0%";
        String someUI ="0%";
        String allUI ="0%";
        String filePath = "D:\\Users\\z1115\\1.xlsx";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(new Date());
        all = String.valueOf((int) Math.round((Double.valueOf(patronRating.replace("%","")) +
                Double.valueOf(itamdinRating.replace("%","")) +
                Double.valueOf(loyaltyRating.replace("%","")) +
                Double.valueOf(creditControlRating.replace("%","")) +
                Double.valueOf(promotionRating.replace("%","")))/5)) + "%";
        String[] ages = {date,patronRating,itamdinRating,loyaltyRating,creditControlRating,promotionRating,all,someUI,allUI};
        ExcelUtil.createTrackQA(filePath,ages);
    }

}
