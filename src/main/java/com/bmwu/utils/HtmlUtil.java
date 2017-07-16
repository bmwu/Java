package com.bmwu.utils;

import org.springframework.web.util.HtmlUtils;

import java.util.regex.Pattern;

/**
 * Created by michael on 7/14/17.
 */
public class HtmlUtil {

    String html = "<P><p>&lt;Noranda Income Fund (TSX:NIF.UN) \n今日宣布Noranda OperatingTrust董事会已批准派发2015年7月份每优先单位达$0.04167的股息，该股息将在2015年8月25日向截至2015年7月31日在录的优先单位持有人派发。</p></P>";
    /**
     * 把html的标签特殊字符转换成普通字符
     */
    public void testhtmlEscape(){
        String value = HtmlUtils.htmlEscape(html);
        System.out.println(value);
    }
    /**
     * 把html的特殊字符转换成普通数字
     */
    public void testhtmlEscapeDecimal(){
        String value = HtmlUtils.htmlEscapeDecimal(html);
        System.out.println(value);
    }
    /**
     * 把html的特殊字符转换成符合Intel HEX文件的字符串
     */
    public void htmlEscapeHex(){
        String value = HtmlUtils.htmlEscapeHex(html);
        System.out.println(value);
    }
    /**
     * 把html的特殊字符反转换成html标签
     * 以上三种方法都可以反转换
     */

    public void htmlUnescape(){
        String tmp = HtmlUtils.htmlEscapeDecimal(html);
        System.out.println(tmp);

        String value = HtmlUtils.htmlUnescape(html);
        value=HtmlUtils.htmlUnescape(value);
        System.out.println(value.replaceAll("\n","</p><p>"));
        System.out.println(value.replaceAll("</?[^>]+>","").replaceAll("\n","</p><p>"));
    }

    public static String Html2Text(String inputString) {
        String htmlStr = inputString; //含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEdx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "</?[^>]+>"; //定义HTML标签的正则表达式

//            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
//            m_script = p_script.matcher(htmlStr);
//            htmlStr = m_script.replaceAll(""); //过滤script标签
//
//            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
//            m_style = p_style.matcher(htmlStr);
//            htmlStr = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            boolean ss = m_html.find();
            htmlStr = m_html.replaceAll(""); //过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        System.out.println(textStr);

        return textStr;//返回文本字符串
    }

    public static void main(String[] args) {
//        HtmlUtil th = new HtmlUtil();
//        th.testhtmlEscape();
//        th.testhtmlEscapeDecimal();
//        th.htmlEscapeHex();
//        th.htmlUnescape();

        Html2Text("<p>' \" ; > < % </p>");
    }
}
