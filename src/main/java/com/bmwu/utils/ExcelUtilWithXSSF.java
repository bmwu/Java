package com.bmwu.utils;

import com.bmwu.file.model.Content;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 得到Excel，并解析内容  对2007及以上版本 使用XSSF解析
 * User: 麦口
 * Date: 18/1/13
 */
public class ExcelUtilWithXSSF {

    public static void setValues(String path, List<Content> contents) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row0 = sheet.createRow(0);
        Cell cell0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
        cell0.setCellValue("日期");

        Cell cell01 = row0.createCell(1, Cell.CELL_TYPE_STRING);
        cell01.setCellValue("时间段");

        Cell cell02 = row0.createCell(2, Cell.CELL_TYPE_STRING);
        cell02.setCellValue("访问地址");

        Cell cell03 = row0.createCell(3, Cell.CELL_TYPE_STRING);
        cell03.setCellValue("IP");

        Cell cell04 = row0.createCell(4, Cell.CELL_TYPE_STRING);
        cell04.setCellValue("内容");

        int rowCnt = 1;
        for (Content content:
             contents) {
            Row row = sheet.createRow(rowCnt);
            Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
            cell.setCellValue(content.getDate());

            Cell cell1 = row.createCell(1, Cell.CELL_TYPE_STRING);
            cell1.setCellValue(content.getTime());

            Cell cell2 = row.createCell(2, Cell.CELL_TYPE_STRING);
            cell2.setCellValue(content.getUrl());

            Cell cell3 = row.createCell(3, Cell.CELL_TYPE_STRING);
            cell3.setCellValue(content.getIp());

            Cell cell4 = row.createCell(4, Cell.CELL_TYPE_STRING);
            cell4.setCellValue(content.getContent());
            rowCnt++;
        }

//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//            wb.write(os);
//        } catch (IOException e){
//            e.printStackTrace();
//        }
        File file = new File(path);//Excel文件生成后存储的位置。
        OutputStream fos  = null;
        try {
            fos = new FileOutputStream(file);
            wb.write(fos);
//            os.close();
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 得到Excel，并解析内容  对2007及以上版本 使用XSSF解析
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void getExcelAsFile(String file) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
//        //1.得到Excel常用对象
//        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("d:/FTP/new1.xls"));
//        //2.得到Excel工作簿对象
//        HSSFWorkbook wb = new HSSFWorkbook(fs);

        InputStream ins = null;
        Workbook wb = null;
        ins=new FileInputStream(new File(file));
        //ins= ExcelService.class.getClassLoader().getResourceAsStream(filePath);
        wb = WorkbookFactory.create(ins);
        ins.close();


        //3.得到Excel工作表对象
        Sheet sheet = wb.getSheetAt(0);
        //总行数
        int trLength = sheet.getLastRowNum();
        //4.得到Excel工作表的行
        Row row = sheet.getRow(0);
        //总列数
        int tdLength = row.getLastCellNum();
        //5.得到Excel工作表指定行的单元格
//        Cell cell = row.getCell((short)1);
        //6.得到单元格样式
//        CellStyle cellStyle = cell.getCellStyle();

        for(int i=5;i<trLength;i++){
            //得到Excel工作表的行
            Row row1 = sheet.getRow(i);
            for(int j=0;j<tdLength;j++){
                //得到Excel工作表指定行的单元格
                Cell cell1 = row1.getCell(j);
                /**
                 * 为了处理：Excel异常Cannot get a text value from a numeric cell
                 * 将所有列中的内容都设置成String类型格式
                 */
                if(cell1!=null){
                    cell1.setCellType(Cell.CELL_TYPE_STRING);
                }

                //获得每一列中的值
                System.out.print(cell1+"                   ");
            }
            System.out.println();
        }

        //将修改后的数据保存
        OutputStream out = new FileOutputStream(file);
        wb.write(out);
    }

    public static List<String> getIps(String file) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {

        InputStream ins = null;
        Workbook wb = null;
        ins=new FileInputStream(new File(file));
        wb = WorkbookFactory.create(ins);
        ins.close();

        //3.得到Excel工作表对象
        Sheet sheet = wb.getSheetAt(0);
        //总行数
        int trLength = sheet.getLastRowNum();
        //4.得到Excel工作表的行
        Row row = sheet.getRow(0);
        //总列数
        int tdLength = row.getLastCellNum();

        List<String> ips = new ArrayList<>();

        for(int i=1;i<trLength;i++){
            //得到Excel工作表的行
            Row row1 = sheet.getRow(i);
            for(int j=0;j<tdLength;j++){
                //得到Excel工作表指定行的单元格
                Cell cell1 = row1.getCell(j);
                /**
                 * 为了处理：Excel异常Cannot get a text value from a numeric cell
                 * 将所有列中的内容都设置成String类型格式
                 */
                if(cell1!=null){
                    cell1.setCellType(Cell.CELL_TYPE_STRING);
                }

                //获得每一列中的值
                if (ips.contains(cell1.getStringCellValue())) {
                    continue;
                }

                ips.add(cell1.getStringCellValue());
            }
        }

        return ips;
    }

    public static List<String> getIds(String file) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {

        InputStream ins = null;
        Workbook wb = null;
        ins=new FileInputStream(new File(file));
        wb = WorkbookFactory.create(ins);
        ins.close();

        //3.得到Excel工作表对象
        Sheet sheet = wb.getSheetAt(0);
        //总行数
        int trLength = sheet.getLastRowNum();
        //4.得到Excel工作表的行
        Row row = sheet.getRow(0);
        //总列数
        int tdLength = row.getLastCellNum();

        List<String> ids = new ArrayList<>();

        for(int i=1;i<trLength;i++) {
            //得到Excel工作表的行
            Row row1 = sheet.getRow(i);

            //得到Excel工作表指定行的单元格
            Cell cell1 = row1.getCell(0);
            /**
             * 为了处理：Excel异常Cannot get a text value from a numeric cell
             * 将所有列中的内容都设置成String类型格式
             */
            if(cell1!=null){
                cell1.setCellType(Cell.CELL_TYPE_STRING);
            }

            //获得每一列中的值
            if (ids.contains(cell1.getStringCellValue())) {
                continue;
            }

            ids.add(cell1.getStringCellValue());
        }

        return ids;
    }

    public static Map<String, String> getIdMap(String file) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {

        InputStream ins = null;
        Workbook wb = null;
        ins=new FileInputStream(new File(file));
        wb = WorkbookFactory.create(ins);
        ins.close();

        //3.得到Excel工作表对象
        Sheet sheet = wb.getSheetAt(0);
        //总行数
        int trLength = sheet.getLastRowNum();
        //4.得到Excel工作表的行
        Row row = sheet.getRow(0);
        //总列数
        int tdLength = row.getLastCellNum();

        Map<String, String> maps = new HashMap<>();

        for(int i=1;i<trLength;i++){
            //得到Excel工作表的行
            Row row1 = sheet.getRow(i);
            /**
             * 为了处理：Excel异常Cannot get a text value from a numeric cell
             * 将所有列中的内容都设置成String类型格式
             */
            Cell cell = row1.getCell(0);
            if (cell!=null){
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            String id = row1.getCell(0).getStringCellValue();

            for(int j=1;j<tdLength;j++){
                //得到Excel工作表指定行的单元格
                Cell cell1 = row1.getCell(j);
                /**
                 * 为了处理：Excel异常Cannot get a text value from a numeric cell
                 * 将所有列中的内容都设置成String类型格式
                 */
                if(cell1!=null){
                    cell1.setCellType(Cell.CELL_TYPE_STRING);
                }

                //获得每一列中的值
                if (maps.containsKey(id)) {
                    String value = maps.get(id);
                    int indexof = cell1.getStringCellValue().indexOf('.');
                    if (j == 4 && indexof > 0 && (cell1.getStringCellValue().length() > indexof + 3)) {
                        value += ("," + cell1.getStringCellValue().substring(0, indexof + 3));
                    } else {
                        value += ("," + cell1.getStringCellValue());
                    }
                    maps.put(id, value);
                } else {
                    maps.put(id, cell1.getStringCellValue());
                }
            }
        }

        return maps;
    }

}
