package com.bmwu.utils;

import java.util.regex.Pattern;

/**
 * Created by michael on 7/19/17.
 */
public class RegUtil {

    public static boolean hasHtmlLabel(String inputString) {
        String htmlStr = inputString; //含html标签的字符串

        String regEx_html = "</?[^>]+>"; //定义HTML标签的正则表达式
        java.util.regex.Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher m_html = p_html.matcher(htmlStr);
        return m_html.find();
    }

    public static void main(String[] args) {
        boolean b = hasHtmlLabel("<p>fsd&nbsp;</p>");
        System.out.println("value: " + b);
    }
}
