package com.bmwu.logistics.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: add something here
 * User: 麦口
 * Date: 18/1/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XStreamAlias("Order")
@XStreamConverter(value = ToAttributedValueConverter.class)
public class SfOrderDetail implements Serializable   {

    private static final long serialVersionUID = -3981119563415169644L;

    /**
     * 客户订单号
     */
    @XStreamAlias("orderid")
    private String orderId;

    /**
     * 顺丰运单号,一个订单只能 有一个母单号,如果是子母 单的情况,以半角逗号分 隔,主单号在第一个位置, 如 “755123456789,0011234567 89,002123456789”,对于路 由推送注册,此字段为必填。
     */
    @XStreamAlias("mailno")
    private String mailNo;

    // 不要求返回顺丰运单号
    @XStreamAlias("is_gen_bill_no")
    private String isGenBillNo = "0";

    @XStreamAlias("j_company")
    private String jCompany;

    @XStreamAlias("j_contact")
    private String jContact;

    @XStreamAlias("j_tel")
    private String jTel;

    @XStreamAlias("j_mobile")
    private String jMobile;

    @XStreamAlias("j_province")
    private String jProvince;

    @XStreamAlias("j_city")
    private String jCity;

    @XStreamAlias("j_county")
    private String jCounty;

    @XStreamAlias("j_address")
    private String jAddress;

    @XStreamAlias("j_post_code")
    private String jPostCode;

    @XStreamAlias("d_company")
    private String dCompany;

    @XStreamAlias("d_contact")
    private String dContact;

    @XStreamAlias("d_tel")
    private String dTel;

    @XStreamAlias("d_mobile")
    private String dMobile;

    @XStreamAlias("d_province")
    private String dProvince;

    @XStreamAlias("d_city")
    private String dCity;

    @XStreamAlias("d_county")
    private String dCounty;

    @XStreamAlias("d_address")
    private String dAddress;

    @XStreamAlias("d_post_code")
    private String dPostCode;

    @XStreamAlias("express_type")
    private String expressType = "1";

    @XStreamAlias("pay_method")
    private String payMethod = "1";

    @XStreamAlias("parcel_quantity")
    private String parcelQuantity = "1";

    @XStreamAlias("cargo_width")
    private String cargoWidth = "33";

    @XStreamAlias("cargo_length")
    private String cargoLength = "33";

    @XStreamAlias("cargo_height")
    private String cargoHeight = "33";

    @XStreamAlias("remark")
    private String remark = "";

}
