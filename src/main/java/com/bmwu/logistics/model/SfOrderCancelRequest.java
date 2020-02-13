package com.bmwu.logistics.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 * 请求 XML 报文:
 * service 属性与 Head 元素预先定义了“服务名”及“接入编码”。
 * “接入编码”统一由顺丰 BSP 系统管理员分配。
 * lang 属性用于定义响应报文的语言,缺省值为 zh-CN,目前支持以下值 zh-CN 表示中文简体,zh-TW 或 zh-HK 或 zh-MO 表示中文繁体,en 表示英文。
 *  <?xml version='1.0' encoding='UTF-8'?>
 *   <Request service="服务名" lang="zh-CN">
        <Head>接入编码</Head>
        <Body>请求数据 XML</Body>
    </Request>
 * User: 麦口
 * Date: 18/1/18
 */
@Data
@XStreamAlias("Request")
public class SfOrderCancelRequest implements Serializable {

    private static final long serialVersionUID = -288085292561930545L;

    @XStreamAsAttribute
    private String service;

    @XStreamAsAttribute
    private String lang = "zh-CN";

    @XStreamAlias("Head")
    private String head;

    @XStreamAlias("Body")
    private SfOrderCancelBody body;

}
