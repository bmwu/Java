package com.bmwu.logistics.utils;

import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: add something here
 * User: 麦口
 * Date: 18/1/18
 */
public class ParamUtils {
    public ParamUtils() {
    }

    public static String initParamToURL(String url, Map<String, String> paramMap) {
        return initCommonParam(initPathParam(url, paramMap), paramMap);
    }

    private static String initPathParam(String url, Map<String, String> paramMap) {
        if (url != null && !"".equals(url) && paramMap != null && !paramMap.isEmpty()) {
            Iterator<String> it = paramMap.keySet().iterator();
            String[] usedParam = new String[paramMap.size()];
            int var4 = 0;

            String usedKey;
            while(it.hasNext()) {
                usedKey = it.next();
                Pattern pattern = Pattern.compile("\\{[" + usedKey + "]+\\}");
                Matcher matcher = pattern.matcher(url);
                if (matcher.find()) {
                    url = matcher.replaceAll(paramMap.get(usedKey) == null ? "" : (String)paramMap.get(usedKey));
                    usedParam[var4++] = usedKey;
                }
            }

            String[] var8 = usedParam;
            int var10 = usedParam.length;

            for(int var9 = 0; var9 < var10; ++var9) {
                usedKey = var8[var9];
                if (usedKey != null && !"".equals(usedKey)) {
                    paramMap.remove(usedKey);
                }
            }
        }

        return url;
    }

    private static String initCommonParam(String url, Map<String, String> paramMap) {
        if (url != null && !"".equals(url) && paramMap != null && !paramMap.isEmpty()) {
            Iterator<String> it = paramMap.keySet().iterator();

            String paramName;
            String paramValue;
            for(url = url + "?"; it.hasNext(); url = url + paramName + "=" + paramValue + "&") {
                paramName = it.next();
                paramValue = paramMap.get(paramName);
            }

            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    /**
     * MD5加密
     * @param str 内容
     * @param charset 编码方式
     * @throws Exception
     */
    public static String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        return new String(Base64.encodeBase64(result));
    }

    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }
}
