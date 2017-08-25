package com.bmwu.log.metadata;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by michael on 8/24/17.
 */
public class LogArchive {
    private int level;
    private String from;
    private String logType;
    private String data;
    private String key;

    public LogArchive( List<String> logItems) {
        this.level = 45000;
        this.from = "TEST";
        this.logType = "METADATA";
        this.key = "METADATA_V1.0.0";
        this.data = buildData(logItems);
    }

    private String buildData(List<String> logItems) {
//        StringBuilder builder = new StringBuilder();
//        int size = logItems.size();
//
//        if (0 == size) {
//            builder.append(LKT_STR_BRACKET_LEFT+LKT_STR_BRACKET_RIGHT);
//        } else {
//            builder.append(LKT_STR_BRACKET_LEFT);
//        }
//
//        for (String logItem : logItems) {
//            if (logItem != null) {
////                builder.append(LKT_STR_QUOTE).append(logItem).append(LKT_STR_QUOTE);
//                builder.append(logItem);
//                if (--size > 0) {
//                    builder.append(LKT_STR_COMMA);
//                } else {
//                    builder.append(LKT_STR_BRACKET_RIGHT);
//                }
//            }
//        }
//
//        System.out.print(builder.toString() + "\n");

        JSONArray array = new JSONArray();
        for (Object logItem : logItems) {
            if (logItem != null) {
                array.add(logItem);
            }
        }

        System.out.print(array.toJSONString() + "\n");

        return "";


//        byte[] compressMsg = GzipUtil.compress(array.toJSONString());
//        String compressedMsg = LKT_EMPTY_STRING;
//        if (null != compressMsg) {
//            compressedMsg = new String(Base64.getEncoder().encode(compressMsg), StandardCharsets.UTF_8);
//        }
//
//        return compressedMsg;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
