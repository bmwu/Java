package com.bmwu.client;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Description: add something here
 * User: 麦口
 * Date: 17/12/25
 */
@Slf4j
@Component
public class HttpClient {

    public static final int CONNECT_TIMEOUT = 1000000;

    public static final int READ_TIMEOUT = 1000000;


    public String doGet(String path, Map<String, Object> params) {
        log.info("path = {}, params = {}", path, params);

        HttpRequest r = HttpRequest
                .get(path, params, true)
                .acceptJson()
                .acceptCharset(HttpRequest.CHARSET_UTF8);
        if (r.ok()) {
            return r.body();
        } else {
            try {
                String error = r.body();
                log.error("fail to do get client, path={}, params={}, cause:{}", path, params, error);
            } catch (Exception e) {
                log.error("[get]parse json error, cause: {}", e);
            }
        }
        return "";
    }

    public String doGet(String path) {
        log.info("path = {} ", path);

        HttpRequest r = HttpRequest.get(path)
                .connectTimeout(CONNECT_TIMEOUT)
                .readTimeout(READ_TIMEOUT)
                .acceptJson()
                .acceptCharset(HttpRequest.CHARSET_UTF8);
        if (r.ok()) {
            return r.body();
        } else {
            try {
                String error = r.body();
                log.error("fail to do get client, path={}, cause:{}", path, error);
            } catch (Exception e) {
                log.error("[get]parse json error, cause: {}", e);
            }
        }
        return "";
    }

    public String doPost(String path, Map<String, Object> params) {
        HttpRequest r = HttpRequest
                .post(path, params, true)
                .contentType("application/x-www-form-urlencoded;charset=utf-8")
                .acceptCharset(HttpRequest.CHARSET_UTF8);
        if (r.ok()) {
            return r.body();
        } else {
            log.error("fail to do post client, path={}, params={}, cause:{}",
                    path, params, r.body());
            return "";
        }
    }

    public String doPostJson(String path, Map<String, Object> params) {
        String paramData = JSON.toJSONString(params);
        log.info("path = {}, paramJsonData = {}", path, paramData);

        HttpRequest r = HttpRequest
                .post(path)
                .acceptJson()
                .acceptCharset(HttpRequest.CHARSET_UTF8)
                .contentType(HttpRequest.CONTENT_TYPE_JSON)
                .send(paramData);
        if (r.ok()) {
            return r.body();
        } else {
            try {
                String error = r.body();
                log.error("fail to do post json client, path = {}, params = {}" + ", cause : {}", path, params, error);
            } catch (Exception e) {
                log.error("[post]parse json error, cause: {}", e);
            }
        }
        return "";
    }
}
