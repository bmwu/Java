package com.bmwu.logistics.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URLConnection;
import java.util.Map;

/**
 * Description: open client
 * User: 麦口
 * Date: 18/1/18
 */
public class ExpressOpenClient {

    private static ExpressOpenClient client = new ExpressOpenClient();
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String REQUEST_METHOD_POST = "POST";
    private static final String REQUEST_METHOD_GET = "GET";
    private static final String UTF_8 = "UTF-8";
    private static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded;charset=utf-8";

    public ExpressOpenClient() {
    }

    public static ExpressOpenClient getInstance() {
        if (client == null) {
            synchronized (ExpressOpenClient.class) {
                if (client == null) {
                    client = new ExpressOpenClient();
                }
            }
        }

        return client;
    }

    public String doPost(String url, Map<String, String> paramMap, String contentType) throws Exception {
        URLConnection conn = URLConnectionUtils.getHttpConnection(ParamUtils.initParamToURL(url, paramMap), REQUEST_METHOD_POST, contentType);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, paramMap);
        System.out.println(stringWriter.toString());
        conn.getOutputStream().write(stringWriter.toString().getBytes(UTF_8));
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //获取服务端的反馈
        String responseString = "";
        String strLine = "";
        InputStream in = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while ((strLine = reader.readLine()) != null) {
            responseString += strLine + "\n";
        }
        in.close();

        return responseString;
    }

    public String doPostJson(String url, Map<String, String> paramMap) throws Exception {
        return doPost(url, paramMap, CONTENT_TYPE_JSON);
    }

    public String doPostForm(String url, Map<String, String> paramMap) throws Exception {
        return doPost(url, paramMap, CONTENT_TYPE_FORM);
    }

    public String doGet(String url, Map<String, String> paramMap, String contentType) throws Exception {
        URLConnection conn = URLConnectionUtils.getHttpConnection(ParamUtils.initParamToURL(url, paramMap), REQUEST_METHOD_GET, contentType);
        return objectMapper.readValue(new BufferedInputStream(conn.getInputStream()), String.class);
    }

    public String doGetJson(String url, Map<String, String> paramMap) throws Exception {
        return doGet(url, paramMap, CONTENT_TYPE_JSON);
    }

    public String doGetForm(String url, Map<String, String> paramMap) throws Exception {
        return doGet(url, paramMap, CONTENT_TYPE_FORM);
    }
}
