package com.bmwu.logistics.utils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description: URLConnectionUtils
 * User: 麦口
 * Date: 18/1/18
 */
public class URLConnectionUtils {

    private static final int CONNECT_TIME_OUT = 5000;
    private static final int READ_TIME_OUT = 100000;

    public URLConnectionUtils() {
    }

    public static HttpURLConnection getHttpConnection(String url, String method, String contentType) throws Exception {
        URL myURL = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection)myURL.openConnection();
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        httpConn.setUseCaches(false);
        httpConn.setRequestMethod(method);
        httpConn.setRequestProperty("Content-Type", contentType);
        httpConn.setConnectTimeout(CONNECT_TIME_OUT);
        httpConn.setReadTimeout(READ_TIME_OUT);
        return httpConn;
    }
}
