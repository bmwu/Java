package com.bmwu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 3/5/17.
 */
public class HelloWorld {

    public static Double getRatio() {
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key", null);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

//        Double ratio1 = getRatio();
//        Double ratio2 = 1.0 - ratio1;
    }
}
