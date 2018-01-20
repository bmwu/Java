package com.bmwu.logistics.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * Description: add something here
 * User: 麦口
 * Date: 18/1/20
 */
@Data
@XStreamAlias("Body")
public class SfOrderCancelBody {

    private static final long serialVersionUID = 8291679017957509599L;

    @XStreamAlias("OrderConfirm")
    private SfOrderCancel sfOrderCancel;
}
