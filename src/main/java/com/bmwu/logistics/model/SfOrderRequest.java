package com.bmwu.logistics.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: add something here
 * User: 麦口
 * Date: 18/1/20
 */
@Data
@XStreamAlias("Request")
public class SfOrderRequest implements Serializable {

    private static final long serialVersionUID = 4986369760527460593L;

    @XStreamAsAttribute
    private String service;

    @XStreamAsAttribute
    private String lang = "zh-CN";

    @XStreamAlias("Head")
    private String head;

    @XStreamAlias("Body")
    private SfOrderBody body;
}
