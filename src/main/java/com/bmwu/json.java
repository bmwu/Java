package com.bmwu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
}
