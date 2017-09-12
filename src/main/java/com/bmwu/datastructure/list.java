package com.bmwu.datastructure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 9/11/17.
 */
public class list {
    @Test
    public void listTest() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        List<String> list1 = new ArrayList<>();
        for ( String str:
             list) {
            if (!str.equals("1")) {
                list1.add(str);
            }
        }

        for ( String str:
                list1gitg) {
            System.out.println(str + "\n");
        }
    }
}
