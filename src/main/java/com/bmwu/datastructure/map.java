package com.bmwu.datastructure;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by michael on 8/25/17.
 */
public class map {

    @Test
    public void testMap() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("A", "A1");
        map1.put("B", "B1");

        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("A", "A1");
        map2.put("B", "B1");

        for (Map.Entry<String, String> obj : map2.entrySet()) {
            System.out.println("Key: " + obj.getKey());
            System.out.println("Value: " + obj.getValue());
        }


    }
}
