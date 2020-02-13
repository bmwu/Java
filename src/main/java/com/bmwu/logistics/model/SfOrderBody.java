package com.bmwu.logistics.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 顺丰订单
 * User: 麦口
 * Date: 18/1/18
 */
@Data
@XStreamAlias("Body")
public class SfOrderBody implements Serializable {

    private static final long serialVersionUID = 8291679017957509599L;

    @XStreamAlias("Order")
    private SfOrderDetail sfOrderDetail;
}
