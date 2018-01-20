package com.bmwu.logistics.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 顺丰订单取消
 * User: 麦口
 * Date: 18/1/18
 */
@Data
@XStreamAlias("")
public class SfOrderCancel implements Serializable {

    private static final long serialVersionUID = 3002213359256198206L;

    @XStreamAlias("orderid")
    private String orderId;

    @XStreamAlias("mailno")
    private String mailNo;

}
