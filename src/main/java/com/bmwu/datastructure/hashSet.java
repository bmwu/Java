package com.bmwu.datastructure;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by michael on 8/21/17.
 */
public class hashSet {

    private HashSet<String> restfulUrlMap = new HashSet<>();

    @Test
    public void test() {
//        restfulUrlMap.add("restful/test");
        restfulUrlMap.add("restful/test/.*/.*");
//        restfulUrlMap.add("restful/test/create");
//        restfulUrlMap.add("restful/test/update");
//        restfulUrlMap.add("restful/test/delete/.*/.*");

        List<String> apiList = new ArrayList<>();
        apiList.add("restful/test");
        apiList.add("restful/test/key/123");
        apiList.add("restful/test/create");
        apiList.add("restful/test/update");
        apiList.add("restful/test/delete/key");


        for (String api : apiList) {

            Iterator<String> iterator = restfulUrlMap.iterator();
            boolean matched = false;
            while (iterator.hasNext()) {
                if (Pattern.matches(iterator.next(), api)) {
                    matched = true;
                }
            }
            System.out.print(api + ": " + matched + "\n");
        }
    }
}
