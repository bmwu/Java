package com.bmwu.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel工具类
 * <p/>
 */
public class ExcelUtils {

    /**
     * EXCEL第一行索引
     */
    public static final int EXCEL_FIRST_ROW_INDEX = 0;

    public static final String TRUE_VALUE = "Y";

    public static final String FALSE_VALUE = "N";

    /**
     * 获取单元格字符串内容
     *
     * @param cell
     * @return
     */
    private static String getCellValue(HSSFCell cell) {
        String value = "";
        // 注意：一定要设成这个，否则可能会出现乱码
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16);

        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (date != null) {
                        value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    } else {
                        value = "";
                    }
                } else {
                    long longVal = Math.round(cell.getNumericCellValue());
                    if (Double.parseDouble(longVal + ".0") == cell.getNumericCellValue())
                        value = String.valueOf(longVal);
                    else
                        value = String.valueOf(cell.getNumericCellValue());
                    //value =cell.getStringCellValue();
                }
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                // 导入时如果为公式生成的数据则无值
                if (!cell.getCellFormula().equals("")) {
                    try {
                        value = String.valueOf(cell.getNumericCellValue());
                    } catch (Exception ex) {
                        value = cell.getStringCellValue();
                    }
                } else {
                    value = cell.getStringCellValue() + "";
                }
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                value = "";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                value = (cell.getBooleanCellValue() ? TRUE_VALUE : FALSE_VALUE);
                break;
            default:
                value = "";
        }

        return value;
    }

    /**
     * 去掉字符串右边的空格
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
    private static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }

    /**
     * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
     *
     * @param inputStream 读取数据的源Excel
     * @param headerIndex 表单头所在的索引
     * @return 读出的Excel中数据的内容
     * @throws IOException
     */
    public static List<Map<String, String>> parse(InputStream inputStream, int headerIndex) throws IOException {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        int maxcolumnIndex = 0;
        POIFSFileSystem fs = new POIFSFileSystem(inputStream);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFCell cell = null;
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            List<String> headers = new ArrayList<String>();
            for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int currentcolumnIndex = row.getLastCellNum() + 1;
                if (currentcolumnIndex > maxcolumnIndex) {
                    maxcolumnIndex = currentcolumnIndex;
                }
                Map<String, String> rows = new LinkedHashMap<String, String>();

                for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    String value = "";
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        value = getCellValue(cell);
                    }
                    if (rowIndex == headerIndex && value != null && !value.trim().equals("")) {
                        headers.add(value.trim());
                    } else {
                        if (columnIndex < headers.size()) {
                            rows.put(headers.get(columnIndex), value);
                        }
                    }
                }

                if (!isEmptyMap(rows)) {
                    result.add(rows);
                }
            }
        }
        return result;
    }

    private static boolean isEmptyMap(Map map) {
        boolean empty = map.isEmpty();
        if (!empty) {
            Collection values = map.values();
            for (Object value : values) {
                if (value != null && !value.toString().equals("")) {
                    return false;
                }
            }
            return true;
        }
        return empty;
    }

}
