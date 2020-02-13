package com.bmwu.file;

import com.bmwu.file.model.Content;
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
    public void parseAll() throws IOException {
    }

    @Test
    public void parseId() throws Exception {

        // 列出所有id文件
        List<String> idFiles = new ArrayList<>();
        idFiles.add("/Users/longkan/git/Java/src/main/resources/idsips/blq-id.xlsx");
        idFiles.add("/Users/longkan/git/Java/src/main/resources/idsips/cym-id.xlsx");
        idFiles.add("/Users/longkan/git/Java/src/main/resources/idsips/lzy-id.xlsx");
        idFiles.add("/Users/longkan/git/Java/src/main/resources/idsips/mjt-id.xlsx");

        // 列出相对应的ip文件
        List<String> ipFiles = new ArrayList<>();
        ipFiles.add("/Users/longkan/git/Java/src/main/resources/idsips/blq-ip.xlsx");
        ipFiles.add("/Users/longkan/git/Java/src/main/resources/idsips/cym-ip.xlsx");
        ipFiles.add("/Users/longkan/git/Java/src/main/resources/idsips/lzy-ip.xlsx");
        ipFiles.add("/Users/longkan/git/Java/src/main/resources/idsips/mjt-ip.xlsx");

        // 获取id所对应的内容
        Map<String, String> maps = getMaps(idFiles);

        for (int i = 0; i <4; i++) {

            List<String> ids = ExcelUtilWithXSSF.getIds(idFiles.get(i));

            List<String> ips = ExcelUtilWithXSSF.getIps(ipFiles.get(i));

            File f = new File("/Users/longkan/git/Java/src/main/resources/W3SVC4");
            File[] files = f.listFiles();
            int count = 0;
            List<Content> matchedIds = new ArrayList<>();
            for (File file :
                 files) {
                matchedIds.addAll(parse(file, ips, ids, maps));
            }

            String matchFile = "/Users/longkan/git/Java/src/main/resources/match-"+ipFiles.get(i).substring(ipFiles.get(i).indexOf("idsips") +7, ipFiles.get(i).indexOf("idsips") + 10)+".xlsx";

            ExcelUtilWithXSSF.setValues(matchFile, matchedIds);
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

    private List<Content> parse(File file, List<String> ips, List<String> ids, Map<String, String> maps) throws IOException {

        FileReader reader = new FileReader(file.getAbsolutePath());

        BufferedReader br = new BufferedReader(reader);

        String str = null;
        int count = 0;

        List<Content> matchedIds = new ArrayList<>();

        while((str = br.readLine()) != null) {
            for (String ip:
                 ips) {

                if (str.contains(ip) && str.contains("/module/mod_expense/edit.asp") && !str.contains("FormCode=217")) {

                    String matchedStr = str.substring(0, str.indexOf(ip) + ip.length());
                    String matchedStr2 = matchedStr.toLowerCase();
                    String id = matchedStr.substring(matchedStr2.indexOf("id") +3 , matchedStr2.lastIndexOf('-') -1);

                    if (ids.contains(id)) {
                        String date = matchedStr.substring(0, matchedStr.indexOf(' '));
                        String hour = "";
                        int ihour = Integer.valueOf(matchedStr.substring(date.length()+1, date.length()+3)) + 8;
                        if (ihour >= 24) {
                            hour = String.valueOf(ihour - 24);
                        } else {
                            hour = String.valueOf(ihour);
                        }
                        String time = hour + matchedStr.substring(date.length()+3, date.length()+9);
                        String url = "o.a" + matchedStr.substring(matchedStr.indexOf("GET") + 4, matchedStr.lastIndexOf('-') -1).replace(' ', '?');

                        Content content = new Content(date, time, url, ip, maps.get(id));
                        matchedIds.add(content);
                        count++;
                    }
                }
            }
        }

        br.close();
        reader.close();
        return matchedIds;
    }


}
