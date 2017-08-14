package com.bmwu;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import java.util.Map;

/**
 * Created by michael on 8/11/17.
 */
public class StringUtil {

    @Test
    public void replacement() {

        String url = "http://localhost:6621/acc/1/strategy/recommend/{appKey}/{requestId}";
        Map<String, Object> path = new HashedMap();
        path.put("appKey", "dd00482e24");
        path.put("requestId", 6);
        if (path != null && !path.isEmpty()) {
            for (Map.Entry<String, Object> entry : path.entrySet()) {

                String key = "{"+entry.getKey()+"}";
//                String key = entry.getKey();
                String replacement = entry.getValue().toString();
                url = url.replace(key, replacement);
            }
        }
    }
}
