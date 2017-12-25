package com.bmwu.utils.dto;

import lombok.Data;

/**
 * Description: 发货方／收货方信息
 * User: 麦口
 * Date: 17/12/25
 */
@Data
public class RequestUser {

    private String name;

    private String postCode;

    private String phone;

    private String mobile;

    private String prov;

    private String city;

    private String address;

}
