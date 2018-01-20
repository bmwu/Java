package com.bmwu.logistics.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 顺丰订单Response
 * User: 麦口
 * Date: 18/1/18
 */
@Data
@XStreamAlias("OrderResponse")
@XStreamConverter(value = ToAttributedValueConverter.class)
public class SfOrderResponse implements Serializable {

    private static final long serialVersionUID = 8819081803700988229L;

    @XStreamAlias("orderid")
    private String orderId;

    @XStreamAlias("mailno")
    private String mailNo;

    @XStreamAlias("origincode")
    private String originCode;

    @XStreamAlias("destcode")
    private String destCode;

    @XStreamAlias("filter_result")
    private String filterResult;

}
