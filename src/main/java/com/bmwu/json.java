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

    public static void main(String[] args) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Object object = decodeJSONObject(jsonObject);
        JSONObject jsonObject1 = (JSONObject)object;
        System.out.println(jsonObject1.toJSONString());
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
}
