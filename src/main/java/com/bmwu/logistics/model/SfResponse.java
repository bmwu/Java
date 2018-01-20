package com.bmwu.logistics.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 * 响应 XML 报文:
 * Head 元素值为 OK 或 ERR;OK 代表交易成功,ERR 代表发生系统或业务异常,交易失败;对于批量交易场景,只能为成功/失败,无部分成功/部分
 * 失败,只要存在有未成功接收的信息即认为为失败。
 * Head 元素值为 OK 时只返回 Body 元素,为 ERR 时只返回 Error 元素,Body 与 Error 元素不能同时存在。
 * Error 元素中的 code 属性值为四位数字,错误编码的描述请参考附录《原因代码表》。
 *
 *  <Response service="服务名"> <Head>OK|ERR</HEAD>
        <Body>正常响应数据 XML</Body>
        <ERROR code="NNNN">错误详细信息</ERROR>
    </Response>
 * User: 麦口
 * Date: 18/1/18
 */
@Data
@XStreamAlias("Response")
public class SfResponse implements Serializable {

    private static final long serialVersionUID = 8156596228042792555L;

    @XStreamAsAttribute
    private String service;

    @XStreamAlias("Head")
    private String head;

    @XStreamAlias("Body")
    private String body;

    @XStreamAlias("ERROR")
    private String error;

}
