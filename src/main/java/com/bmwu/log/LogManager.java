package com.bmwu.log;

import com.bmwu.log.metadata.BaseMeta;
import com.bmwu.log.metadata.LogArchive;
import com.bmwu.log.metadata.LogData;
import com.bmwu.log.metadata.RequestMetaBase;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.*;

import static com.bmwu.log.json.*;
import io.netty.handler.codec.http.*;

/**
 * Created by michael on 8/24/17.
 */
public class LogManager {

    protected static final Gson gson = new Gson();

    public List<String> logCache = new ArrayList<>();
    protected List<String> logList = new ArrayList<>();

    private Map<String,String > getHeadersMap(HttpHeaders headers){
        Iterator iter = headers.iteratorAsString();
        Map<String,String> map = new HashMap<>();
        while (iter.hasNext())
        {
            Map.Entry<String,String > entry =(Map.Entry<String,String>) iter.next();
            map.put(entry.getKey(),entry.getValue());
        }
        return map;
    }

    public FullHttpRequest build() {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/test/api");
        return request;
    }


    public void requestMetaLog() {
        LogData<RequestMetaBase> requestMetaLogMessage = new LogData();
        requestMetaLogMessage.setBusiness(new RequestMetaBase());
        requestMetaLogMessage.setMeta(new BaseMeta());

        requestMetaLogMessage.getMeta().setRid("12345");


        FullHttpRequest request = build();
        HttpHeaders headers = request.headers();
        Map<String,String> headersMap = getHeadersMap(headers);
        headersMap.put("header中文1", "value1");
        headersMap.put("header2", "value2");

        requestMetaLogMessage.getBusiness().setMap1(headersMap);
        requestMetaLogMessage.getBusiness().setField4("POST");
        requestMetaLogMessage.getBusiness().setField2("locket.\"tt");
        requestMetaLogMessage.getBusiness().setField1("/test/api");
        requestMetaLogMessage.getBusiness().setField5(100L);
        requestMetaLogMessage.getBusiness().setField6("127.0.0.1");

        LogData<RequestMetaBase> locketLogData = requestMetaLogMessage;
        String aLogMessage = BaseData(locketLogData);
        logCache.add(aLogMessage);
    }

    public static String BaseData(LogData logData) {
        String data1 = gson.toJson(constructBaseMeta(logData));
        String data2 = constructBaseMeta(logData).toString();
        return data2;
    }

    public static LogData constructBaseMeta(LogData logData) {
        if (logData.getMeta() == null) {
            logData.setMeta(new BaseMeta());
        }
        logData.getMeta().setTimestamp(System.currentTimeMillis());
        logData.getMeta().setId("JI9TADJTQV5V3CRO5L3A");
        logData.getMeta().setKey("d28cb4b3e6");
        return logData;
    }

    public String LogCache2() {
        StringBuilder builder = new StringBuilder();
        int size = logCache.size();

        if (0 == size) {
            builder.append(LKT_STR_BRACKET_LEFT+LKT_STR_BRACKET_RIGHT);
        } else {
            builder.append(LKT_STR_BRACKET_LEFT);
        }

        for (String logItem : logCache) {
            if (logItem != null) {
//                builder.append(LKT_STR_QUOTE).append(logItem).append(LKT_STR_QUOTE);
                builder.append(logItem);
                if (--size > 0) {
                    builder.append(LKT_STR_COMMA);
                } else {
                    builder.append(LKT_STR_BRACKET_RIGHT);
                }
            }
        }

        return builder.toString();
    }

    public String LogCache() {
        String data = gson.toJson(logCache);
        return data;
    }

    @Test
    public void commitLog() {
        requestMetaLog();
        logList.add(LogCache2());
        logList.add(LogCache2());
//        logList.add(LogCache());
        postLog(logList);
    }

    protected static boolean postLog(List<String> list) {

        if (list != null) {
            ArrayList<String> toPostList = new ArrayList<>();
            toPostList.addAll(list);
            list.clear();

            if(toPostList.size() > 0) {
                sendLog(toPostList);
                return true;
            }
        }
        return false;
    }

    private static void sendLog(List<String> list) {
        String message = buildLogArchive(list);
        System.out.print(message);
    }

    private static String buildLogArchive(List<String> list) throws ConcurrentModificationException {
        LogArchive archive = new LogArchive(list);

        System.out.print(archive.getData() + "\n");

        StringBuilder messageBuilder = new StringBuilder();
        try {
            messageBuilder.append(LKT_STR_BRACE_LEFT)
                    .append(LKT_STR_QUOTE).append("level").append(LKT_STR_QUOTE)
                    .append(LKT_STR_COLON)
                    .append(LKT_STR_QUOTE).append(archive.getLevel()).append(LKT_STR_QUOTE)
                    .append(LKT_STR_COMMA)

                    .append(LKT_STR_QUOTE).append("logType").append(LKT_STR_QUOTE)
                    .append(LKT_STR_COLON)
                    .append(LKT_STR_QUOTE).append(archive.getLogType()).append(LKT_STR_QUOTE)
                    .append(LKT_STR_COMMA)

                    .append(LKT_STR_QUOTE).append("key").append(LKT_STR_QUOTE)
                    .append(LKT_STR_COLON)
                    .append(LKT_STR_QUOTE).append(archive.getKey()).append(LKT_STR_QUOTE)
                    .append(LKT_STR_COMMA)

                    .append(LKT_STR_QUOTE).append("data").append(LKT_STR_QUOTE)
                    .append(LKT_STR_COLON)
//                    .append(LKT_STR_QUOTE).append(archive.getData()).append(LKT_STR_QUOTE)
                    .append(archive.getData())
                    .append(LKT_STR_BRACE_RIGHT);
            return messageBuilder.toString();

        } finally {
            archive = null;
            messageBuilder = null;
            list.clear();
        }
    }
}
