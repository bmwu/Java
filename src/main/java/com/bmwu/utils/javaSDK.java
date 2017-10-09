package com.bmwu.utils;

import com.bmwu.javasdk.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michael on 9/28/17.
 */
public class javaSDK {

    @Test
    public void javaSDKTest() {
        sdk s= new sdk();
        String rawData = "1233";
        String cryptData = s.encrypt(rawData);
        String result = s.decrypt(cryptData);
        assertEquals(rawData, result);
    }
}
