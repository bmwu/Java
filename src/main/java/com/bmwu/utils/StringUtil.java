package com.bmwu.utils;

import org.junit.Test;

import java.util.*;

/**
 * Created by michael on 8/11/17.
 */
public class StringUtil {

    @Test
    public void replacement() {

        String url = "http://localhost:6621/acc/1/strategy/recommend/{key}/{rid}";
        Map<String, Object> path = new HashMap<>();
        path.put("appKey", "dd00482e24");
        path.put("requestId", 6);
        if (path != null && !path.isEmpty()) {
            for (Map.Entry<String, Object> entry : path.entrySet()) {

                String key = "{"+entry.getKey()+"}";
//                String key = entry.getKey();
                String replacement = entry.getValue().toString();
                url = url.replace(key, replacement);
            }
        }
    }

    @Test
    public void subString() {
        List<String> list = new ArrayList();
        list.add("	1.OA登陆记录 	");
        list.add("	1.发布欢迎辞 	");
        list.add("	1.企业 Twitter 	");
        list.add("	10.- CL Pushed 	");
        list.add("	10.- My Pool 	");
        list.add("	10.- Public Pool(Agrochem) 	");
        list.add("	10.- Public Pool(CHEM) 	");
        list.add("	10.- Public Pool(Cosmetic) 	");
        list.add("	10.- Public Pool(Food) 	");
        list.add("	10.- 客户信息(食品类) 	");
        list.add("	10.ChemLinked业务资源 	");
        list.add("	10.公司物质(Substance) 	");
        list.add("	10.海关数据(2006出口) 	");
        list.add("	10.客户信息(add, search, list)	");
        list.add("	10.联系人(contact persons) 	");
        list.add("	13.基础物质 申请 {new} 	");
        list.add("	13.我绑定企业的物质SIEF 	");
        list.add("	13.我关注的物质SIEF 	");
        list.add("	16.2014项目 	");
        list.add("	16.到款认领 	");
        list.add("	16.合同列表 	");
        list.add("	16.合同申请 	");
        list.add("	16.申请Invoice 	");
        list.add("	16.申请国内发票 	");
        list.add("	16.审核国内发票 	");
        list.add("	16.我的到款认领记录 	");
        list.add("	16.项目列表 	");
        list.add("	16.专票信息 	");
        list.add("	17.Invoice开票 	");
        list.add("	17.国内开票信息 	");
        list.add("	17.收支报表（部门） 	");
        list.add("	17.专票开票 Bill Information 	");
        list.add("	21.- 固定资产管理 	");
        list.add("	21.公司资产 管理 	");
        list.add("	21.企业通讯录 	");
        list.add("	21.物资采购 申请 	");
        list.add("	21.物资采购 审核 	");
        list.add("	21.物资领用 申请 	");
        list.add("	22.合同统计 	");
        list.add("	22.数据概览 	");
        list.add("	22.项目详情 	");
        list.add("	24.业务绑定公告 	");
        list.add("	25.案例：电话录音 	");
        list.add("	28.考核 列表	");
        list.add("	28.考核 申请 	");
        list.add("	28.年休信息 	");
        list.add("	29.报销/付款 列表 	");
        list.add("	29.报销/付款 申请 	");
        list.add("	29.报销/付款 审核 	");
        list.add("	29.借款申请 列表 	");
        list.add("	9.- 访问记录( 22:00 ~ 8:00 ) 	");
        list.add("	9.访问记录 	");
        list.add("	9.我的信息 	");

        for (String str : list) {
            int index = 0;
            if (str.contains("-")) {
                index = str.indexOf('-') + 2;
            } else {
                index = str.indexOf('.') + 1;
            }
            System.out.print(str.substring(index).trim() + "\n");
        }
    }

    public static final String BASE_PACKAGE = "com.test";//项目基础包名称，根据自己公司的项目修改

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".modules.%s.model";//Model所在包

    @Test
    public void replace() {
        String str = String.format(MODEL_PACKAGE, "sys");
        System.out.println(str);

        String packageName = "com.jukmall.modules.%s.service";
        String packageName2 = String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
        System.out.println(packageName2);

    }

    /**
     * Optional
     */
    @Test
    public void test() {
        System.out.println(Optional.empty().get());
        System.out.println(Optional.ofNullable(null).get());
    }


}
