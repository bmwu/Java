package com.bmwu.file.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description: add something here
 * User: 麦口
 * Date: 18/1/14
 */
// date 日期
// time 时间段
// url 访问地址
// ip
// content 内容

@Data
@AllArgsConstructor
public class Content {
    private String date;
    private String time;
    private String url;
    private String ip;
    private String content;
}
