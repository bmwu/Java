package com.bmwu.logistics;

import com.bmwu.logistics.model.*;
import com.bmwu.logistics.utils.ExpressOpenClient;
import com.bmwu.logistics.utils.ParamUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
        sfOrder.setDAddress("余杭区");
        // sender
        sfOrder.setJContact("sender");
        sfOrder.setJTel("15987651234");
        sfOrder.setJMobile("15989098765");
        sfOrder.setJProvince("浙江省");
        sfOrder.setJCity("杭州市");
        sfOrder.setJAddress("余杭区");

        SfOrderRequest sfRequest = new SfOrderRequest();
        sfRequest.setService("OrderService");
        sfRequest.setHead("BSPdevelop");
        SfOrderBody sfOrderBody = new SfOrderBody();
        sfOrderBody.setSfOrderDetail(sfOrder);
        sfRequest.setBody(sfOrderBody);

        XStream xStream = new XStream();

        Converter converter = new ToAttributedValueConverter(SfOrderDetail.class,
                xStream.getMapper(), xStream.getReflectionProvider(), xStream.getConverterLookup(), null);
        xStream.registerConverter(converter);

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
            paramMap.put("verifyCode", genVerifyCode(xmlNew));
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
