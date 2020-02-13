package com.bmwu.logistics.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 顺丰订单取消Response
 * User: 麦口
 * Date: 18/1/18
 */
@Data
@XStreamAlias("OrderConfirmResponse")
@XStreamConverter(value = ToAttributedValueConverter.class)
public class SfOrderCancelResponse implements Serializable {

    private static final long serialVersionUID = -5828885635783311182L;

    @XStreamAlias("orderid")
    private String orderId;

    @XStreamAlias("mailno")
    private String mailNo;

    @XStreamAlias("res_status")
    private String resStatus;

}
