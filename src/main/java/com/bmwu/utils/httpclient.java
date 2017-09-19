package com.bmwu.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 9/19/17.
 */
public class httpclient {

    @Test
    // 测试长连接问题
    public void keepAlive() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("Id", "id");

        HttpClientPost("http://127.0.0.1/test", "utf-8", params, 3000);
    }

    public static String HttpClientPost(String url , String charset , Map<String, String> map, int timeout) throws Exception{
        HttpClient client = null;
        PostMethod post = null;
        String result = "";
        int status = 200;
        try {
            //PostMethod对象用于存放地址
            //总账户的测试方法
            post = new PostMethod(url);
            //NameValuePair数组对象用于传入参数
            post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=" + charset);//在头文件中设置转码

            String key = "";
            String value = "";
            NameValuePair temp = null;
            NameValuePair[] params = new NameValuePair[map.size()];

            int i = 0;
            for (Map.Entry<String, String> obj : map.entrySet()) {
                temp = new NameValuePair(obj.getKey() , obj.getValue());
                params[i++] = temp;
                temp = null;
            }

            HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

            HttpConnectionManagerParams params1 = new HttpConnectionManagerParams();
            params1.setConnectionTimeout(timeout);
            params1.setSoTimeout(timeout);
            connectionManager.setParams(params1);

            HttpClientParams httpClientParams = new HttpClientParams();
            // 设置httpClient的连接超时，对连接管理器设置的连接超时是无用的
            httpClientParams.setConnectionManagerTimeout(timeout); //等价于4.2.3中的CONN_MANAGER_TIMEOUT
            client = new HttpClient(connectionManager);
            client.setParams(httpClientParams);

            post.setRequestBody(params);
            //执行的状态
            status = client.executeMethod(post);

            if(status == 200){
                result = post.getResponseBodyAsString();
            }

            System.out.println(result);

        } catch (Exception ex) {
            // TODO: handle exception
            throw new Exception("通过httpClient进行post提交异常：" + ex.getMessage() + " status = " + status);
        }
        finally{
            post.releaseConnection();
        }
        return result;
    }

}
