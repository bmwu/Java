package com.bmwu.utils.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Description: 订单基本信息
 * User: 麦口
 * Date: 17/12/25
 */
@Data
@XmlRootElement(name = "RequestOrder")
public class RequestOrder {

    /**
     * 客户编码（电商标识，由圆通人员给出）
     */
    private String clientID = "TEST";

    /**
     * 物流公司ID（YTO）
     */
    private String logisticProviderID = "YTO";

    /**
     * 物流号
     */
    private String customerId;

    /**
     * 业务交易号
     */
    private String txLogisticID;

    private String tradeNo;

    private String mailNo;

    private String totalServiceFee;

    private String codSplitFee;

    /**
     * 订单类型(0-COD,1-普通订单)
     */
    private String orderType = "1";

    /**
     * 服务类型(1-上门揽收, 2-次日达 4-次晨达 8-当日达,0-自己联系)。（数据库未使用）（目前暂未使用默认为0）
     */
    private String serviceType = "1";

    private String flag;

    /**
     * 发货方信息
     */
    private RequestUser sender;

    /**
     * 收货方信息
     */
    private RequestUser receiver;

    /**
     * 物流公司上门取货时间段，开始时间
     */
    private Date sendStartTime;

    /**
     * 物流公司上门取货时间段，结束时间
     */
    private Date sendEndTime;

    private String goodsValue;

    private String itemsValue;

    private List<RequestItem> items;

    private String insuranceValue;

    private String special = "0";

    private String remark;
}
