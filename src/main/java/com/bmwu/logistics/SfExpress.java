package com.bmwu.logistics;

import com.bmwu.logistics.model.*;
import com.bmwu.logistics.utils.ExpressOpenClient;
import com.bmwu.logistics.utils.ParamUtils;
import com.bmwu.logistics.utils.XStreamNameCoder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: add something here
 * User: 麦口
 * Date: 18/1/20
 */
@Slf4j
public class SfExpress {

    public static final XStreamNameCoder nameCoder = new XStreamNameCoder();

    // 编码格式
    private static final String ENCODING = "UTF-8";

    // dom解析驱动
    private static final DomDriver fixDriver = new DomDriver(ENCODING, nameCoder);

    // 通用解析器
    public static final XStream XSTREAM = new XStream(fixDriver);

    @Test
    public void test() {

        SfOrderDetail sfOrder = new SfOrderDetail();
        sfOrder.setOrderId("123");
        sfOrder.setMailNo("123456");
        // receiver
        sfOrder.setDContact("Test");
        sfOrder.setDTel("15987650918");
        sfOrder.setDMobile("15909878902");
        sfOrder.setDProvince("浙江省");
        sfOrder.setDCity("杭州市");
        sfOrder.setDCounty("余杭区");
        sfOrder.setDAddress("123");
        // sender
        sfOrder.setJContact("sender");
        sfOrder.setJTel("15987651234");
        sfOrder.setJMobile("15989098765");
        sfOrder.setJProvince("浙江省");
        sfOrder.setJCity("杭州市");
        sfOrder.setJCounty("余杭区");
        sfOrder.setJAddress("345");

        sfOrder.setPayMethod("1");

        SfOrderRequest sfRequest = new SfOrderRequest();
        sfRequest.setService("OrderService");
        sfRequest.setHead("BSPdevelop");
        SfOrderBody sfOrderBody = new SfOrderBody();
        sfOrderBody.setSfOrderDetail(sfOrder);
        sfRequest.setBody(sfOrderBody);

        XStream xStream = new XStream(fixDriver);

//        Converter converter = new ToAttributedValueConverter(SfOrderDetail.class,
//                xStream.getMapper(), xStream.getReflectionProvider(), xStream.getConverterLookup(), null);
//        xStream.registerConverter(converter);
//
        xStream.processAnnotations(SfOrderRequest.class);
        orderService(xStream.toXML(sfRequest));
    }

    @Test
    public void orderCancelRequest() {

        SfOrderCancel sfOrderCancel = new SfOrderCancel();
        sfOrderCancel.setOrderId("123");
        sfOrderCancel.setMailNo("123456");

        SfOrderCancelRequest sfRequest = new SfOrderCancelRequest();
        sfRequest.setService("OrderConfirmService");
        sfRequest.setHead("BSPdevelop");
        SfOrderCancelBody sfOrderCancelBody = new SfOrderCancelBody();
        sfOrderCancelBody.setSfOrderCancel(sfOrderCancel);
        sfRequest.setBody(sfOrderCancelBody);

        XStream xStream = new XStream();
        xStream.processAnnotations(SfOrderCancelRequest.class);
        orderService(xStream.toXML(sfRequest));
    }

    private void orderService(final String xml) {

        try {
            Map<String, String> paramMap = new HashMap<>();
            String xmlNew = URLEncoder.encode(xml);
            paramMap.put("xml", xmlNew);
            System.out.println("xml");
            System.out.println(xml);
            System.out.println("xml encode");
            System.out.println(xmlNew);
            paramMap.put("verifyCode", genVerifyCode(xmlNew));
            System.out.println("verifyCode");
            System.out.println(genVerifyCode(xmlNew));
//            String result = ExpressOpenClient.getInstance().doPostForm("https://bsp-ois.sit.sf-express.com:9443/bsp-ois/sfexpressService", paramMap);
            String result = ExpressOpenClient.getInstance().doPostForm("http://bsp-ois.sit.sf-express.com:9080/bsp-ois/sfexpressService", paramMap);
            System.out.println(result);
            XStream xStream = new XStream();
            xStream.processAnnotations(SfResponse.class);
            SfResponse sfResponse = (SfResponse)xStream.fromXML(result);

            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 按以下逻辑生成校验码:
     先把 XML 报文与 checkword 前后连接。
     把连接后的字符串做 MD5 编码。
     把 MD5 编码后的数据进行 Base64 编码,此时编码后的字符串即为校验码
     * @param xml
     * @return
     * @throws Exception
     */
    private String genVerifyCode(String xml) throws Exception {
        return ParamUtils.MD5(xml + "j8DzkIFgmlomPt0aLuwU", "UTF-8");
    }
}
