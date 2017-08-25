package com.bmwu.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by michael on 3/7/17.
 */
public class json {

    private static String jsonStr = "{\n" +
            "  \"testA\": {\n" +
            "    \"key2\": {\n" +
            "      \"key31\": \"value\",\n" +
            "      \"key32\": \"value\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"testB\": {\n" +
            "    \"key2\": {\n" +
            "      \"key31\": \"value\",\n" +
            "      \"key32\": \"value\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"testC\": {\n" +
            "    \"key\": \"value\"\n" +
            "  }\n" +
            "}";

    private static String jsonArray = "[ \"Ford\", \"BMW\", \"Fiat\" ]";

    public static void main(String[] args) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Object object = decodeJSONObject(jsonObject);
        JSONObject jsonObject1 = (JSONObject)object;
        System.out.println(jsonObject1.toJSONString());

        boolean isValidJson = isValidJSON(jsonArray);
        isValidJson = isValidJSON(jsonStr);
        isValidJson = isValidJSON(null);
        isValidJson = isValidJSON("%lkt!00960016f7zfU2pgT/b5Twnf+x% ");

    }

    public static Object decodeJSONObject(Object json){

        if (json instanceof JSONObject) {
            JSONObject retObj = new JSONObject();
            JSONObject jsonObject = (JSONObject)json;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                retObj.put(entry.getKey(), decodeJSONObject(entry.getValue()));
            }
            return retObj;
        } else if (json instanceof JSONArray) {
            JSONArray retArr = new JSONArray();
            JSONArray jsonArray = (JSONArray)json;
            for (Object object : jsonArray) {
                retArr.add(decodeJSONObject(object));
            }
            return retArr;
        } else {
            return "";
        }
    }

    public static boolean isValidJSON(String text) {

        try {
            return null != JSON.parseObject(text);
        } catch (Exception ex) {
            // e.g. in case JSONArray is valid as well...
            try {
                return null != JSON.parseArray(text);
            } catch (Exception ex1) {
                return false;
            }
        }
    }

    public static final String LKT_EMPTY_STRING                 = "";
    public static final String LKT_STR_QUOTE                    = "\"";
    public static final String LKT_STR_BRACE_LEFT               = "{";
    public static final String LKT_STR_BRACE_RIGHT              = "}";
    public static final String LKT_STR_BRACKET_LEFT             = "[";
    public static final String LKT_STR_BRACKET_RIGHT            = "]";
    public static final String LKT_STR_COLON                    = ":";
    public static final String LKT_STR_COMMA                    = ",";


    @Test
    public void testBuild() {
        List<String> logItems = new ArrayList<>();
        logItems.add("\"request_meta\": {\n" +
                "      \"timestamp\": 1503399137513,\n" +
                "      \"id\": \"JI9TADJTQV5V3CRO5L3A\",\n" +
                "      \"rid\": \"\",\n" +
                "      \"key\": \"d28cb4b3e6\"\n" +
                "    },\n" +
                "    \"business\": {\n" +
                "      \"level\": \"INFO\",\n" +
                "      \"position\": \"CoreLauncher\",\n" +
                "      \"message\": \"[Service] Shutting down the Server...\"\n" +
                "    },\n" +
                "    \"logType\": null");

        logItems.add("\"request_meta\": {\n" +
                "      \"timestamp\": 1503399137516,\n" +
                "      \"id\": \"JI9TADJTQV5V3CRO5L3A\",\n" +
                "      \"rid\": \"\",\n" +
                "      \"key\": \"d28cb4b3e6\"\n" +
                "    },\n" +
                "    \"business\": {\n" +
                "      \"level\": \"INFO\",\n" +
                "      \"position\": \"CoreLauncher\",\n" +
                "      \"message\": \"[Service] Server shut down\"\n" +
                "    },\n" +
                "    \"logType\": null");

        buildData(logItems);
    }

    private void buildData(List<String> logItems) {

        StringBuilder builder = new StringBuilder();
        int size = logItems.size();

        if (0 == size) {
            builder.append(LKT_STR_BRACKET_LEFT+LKT_STR_BRACKET_RIGHT);
        } else {
            builder.append(LKT_STR_BRACKET_LEFT);
        }

        for (String logItem : logItems) {
            if (logItem != null) {
                builder.append(LKT_STR_QUOTE).append(logItem).append(LKT_STR_QUOTE);
                if (--size > 0) {
                    builder.append(LKT_STR_COMMA);
                } else {
                    builder.append(LKT_STR_BRACKET_RIGHT);
                }
            }
        }

    }
}
