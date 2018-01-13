package com.bmwu.file;

import com.bmwu.utils.ExcelUtilWithXSSF;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: add something here
 * User: 麦口
 * Date: 18/1/10
 */
public class FileUtils {

    @Test
    public void parseAll() throws Exception {

        // 列出所有id文件
        List<String> idFiles = new ArrayList<>();

        // 列出相对应的ip文件
        List<String> ipFiles = new ArrayList<>();

        // 获取id所对应的内容
        Map<String, String> maps = getMaps(idFiles);

        for (int i = 0; i <4; i++) {

            List<String> ids = ExcelUtilWithXSSF.getIds(idFiles.get(i));

            List<String> ips = ExcelUtilWithXSSF.getIps(ipFiles.get(i));

            String logPath = ""; // log文件所在的目录
            File f = new File(logPath);
            File[] files = f.listFiles();
            int count = 0;
            for (File file :
                 files) {
                count += parse(file, ips, ids, maps, ipFiles.get(i).substring(ipFiles.get(i).indexOf("idsips") +7, ipFiles.get(i).indexOf("idsips") + 10));
            }
            System.out.println("全日志匹配到记录数：" + count);
        }
    }

    private Map<String, String> getMaps(List<String> idFiles) {
        Map<String, String> maps = new HashMap<>();

        idFiles.forEach(idFile -> {
            try {
                maps.putAll(ExcelUtilWithXSSF.getIdMap(idFile));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        });

        return maps;

    }

    private int parse(File file, List<String> ips, List<String> ids, Map<String, String> maps, String name) throws IOException {

        FileReader reader = new FileReader(file.getAbsolutePath());

        BufferedReader br = new BufferedReader(reader);

        String str = null;
        int count = 0;

        List<String> matchedIds = new ArrayList<>();

        while((str = br.readLine()) != null) {
            for (String ip:
                 ips) {
                if (str.contains(ip) && str.contains("/module/mod_expense/edit.asp") && !str.contains("FormCode=217")) {

                    String matchedStr = str.substring(0, str.indexOf(ip) + ip.length());
                    String matchedStr2 = matchedStr.toLowerCase();
                    String id = matchedStr.substring(matchedStr2.indexOf("id") +3 , matchedStr2.lastIndexOf('-') -1);

                    if (ids.contains(id)) {
                        matchedIds.add(matchedStr + " - " + maps.get(id));
                        count++;
                    }
                }
            }
        }

        FileWriter writer = new FileWriter("/Users/longkan/git/Java/src/main/resources/match-"+name+".log",true);
        BufferedWriter bw = new BufferedWriter(writer);

        matchedIds.forEach(id -> {
            try {
                bw.write(id + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        bw.close();
        writer.close();

        FileWriter writer2 = new FileWriter("/Users/longkan/git/Java/src/main/resources/matchcount-"+name+".log",true);
        BufferedWriter bw2 = new BufferedWriter(writer2);
        bw2.write(file.getName() + "匹配到的记录个数:" + count + "\n");
        bw2.close();
        writer2.close();

        br.close();
        reader.close();
        return count;
    }
}
