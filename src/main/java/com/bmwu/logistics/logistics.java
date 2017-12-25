package com.bmwu.logistics;

import com.alibaba.fastjson.JSON;
import com.bmwu.client.HttpClient;
import com.bmwu.utils.xml;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: add something here
 * User: 麦口
 * Date: 17/12/25
 */
@Slf4j
public class logistics {

    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
//              String apiUrl = "http://jingang.yto56.com.cn/ordws/Vip16Servlet" 生产环境
        String apiUrl = "http://jingangtest.yto56.com.cn/ordws/Vip16Servlet";// 测试地址
//		String apiUrl="http://localhost:8081/ordws/Vip16Servlet";
//		String apiUrl="http://10.129.220.53/ordws/Vip16Servlet";
//		String apiUrl="http://jingangtest.yto56.com.cn/ordws/Vip16Servlet";//新测试环境
//		String apiUrl="http://10.129.220.53/ordws/Vip16Servlet";
//		String parternId = "1eiCOM08"; //加密id
//		String clientId = "MYTOTEST"; //客户编码
//		String customerId = "MYTOTEST"; //客户编码
        String parternId = "123456"; //加密id
        String clientId = "TEST"; //客户编码
        String customerId = "TEST"; //客户编码
        String mailNo="";  //运单号（如果有就填，线下单必须有）
        try{

            //数据
            StringBuilder xmlBuilder = new StringBuilder();
			/*xmlBuilder.append("<RequestOrder>");
			xmlBuilder.append("    <clientID>"+clientId+"</clientID>");
			xmlBuilder.append("    <logisticProviderID>YTO</logisticProviderID>");
			xmlBuilder.append("    <customerId>"+customerId+"</customerId>");
			xmlBuilder.append("    <txLogisticID>AL124131153666</txLogisticID>");
			xmlBuilder.append("    <tradeNo>1</tradeNo>");
			xmlBuilder.append("    <mailNo>"+mailNo+"</mailNo>");
			xmlBuilder.append("    <type>1</type>");
			xmlBuilder.append("    <orderType>1</orderType>");//普通订单
			xmlBuilder.append("    <serviceType>0</serviceType>");
			xmlBuilder.append("    <flag>0</flag>");
			xmlBuilder.append("    <sender>");
			xmlBuilder.append("        <name>王府井网上商城</name>");
			xmlBuilder.append("        <postCode>0</postCode>");
			xmlBuilder.append("        <phone>0</phone>");
			xmlBuilder.append("        <mobile>400-890-6600dasdasdasda</mobile>");
			xmlBuilder.append("        <prov>北京</prov>");
			xmlBuilder.append("        <city>北京市</city>");
			xmlBuilder.append("        <address>北京市王府井仓</address>");
			xmlBuilder.append("    </sender>");
			xmlBuilder.append("    <receiver>");
			xmlBuilder.append("        <name>王世浪</name>");
			xmlBuilder.append("        <postCode>123456</postCode>");
			xmlBuilder.append("        <phone>0</phone>");
			xmlBuilder.append("        <mobile>D00025110418</mobile>");
			xmlBuilder.append("        <prov>贵州省</prov>");
			xmlBuilder.append("        <city>黔西南布依族苗族自治州,户县</city>");
			xmlBuilder.append("        <address>贵州省 黔西南布依族苗族自治州 晴隆县 贵州省晴隆县地方税务局</address>");
			xmlBuilder.append("    </receiver>");
			xmlBuilder.append("    <sendStartTime>2012-12-12 12:12:12</sendStartTime>");
			xmlBuilder.append("    <sendEndTime>2012-12-12 12:12:12</sendEndTime>");
			xmlBuilder.append("    <goodsValue>100</goodsValue>");
			xmlBuilder.append("    <totalValue>0</totalValue>");
			xmlBuilder.append("    <itemsValue>1</itemsValue>");
			xmlBuilder.append("    <itemsWeight>1</itemsWeight>");
			xmlBuilder.append("    <items>");
			xmlBuilder.append("        <item>");
			xmlBuilder.append("            <itemName>奥迪</itemName>");
			xmlBuilder.append("            <number>2</number>");
			xmlBuilder.append("            <itemValue>50</itemValue>");
			xmlBuilder.append("        </item>");
			xmlBuilder.append("        <item>");
			xmlBuilder.append("            <itemName>大奔</itemName>");
			xmlBuilder.append("            <number>2</number>");
			xmlBuilder.append("            <itemValue>50</itemValue>");
			xmlBuilder.append("        </item>");
			xmlBuilder.append("    </items>");
			xmlBuilder.append("    <special>1</special>");
			xmlBuilder.append("    <remark>1</remark>");
			xmlBuilder.append("    <insuranceValue>1</insuranceValue>");
			xmlBuilder.append("    <totalServiceFee>1</totalServiceFee>");
			xmlBuilder.append("    <codSplitFee>1</codSplitFee>");
			xmlBuilder.append("</RequestOrder>");*/
//			xmlBuilder.append("{\"Order\":{\"txLogistid\":\"454\",\"logisticProviderID\":\"YTO\"}}");
//			String string = xmlBuilder.toString();
//			JSONObject fromObject = JSONObject.fromObject(string);
//			DateOrder dateOrder = new DateOrder();
//			Order order = new Order();
//			order.setTxLogisticID("46546454");

//			Sender sender = new Sender();
//			sender.setCity(" 是说");
//			sender.setAddress("ssss");
//			sender.setPhone("132125");
//			order.setSender(sender);
//			dateOrder.setOrder(order);
//			JSONObject fromObject2 = JSONObject.fromObject(dateOrder);
//			System.out.println(fromObject2);
//			System.out.println(fromObject);
            xmlBuilder.append("<RequestOrder><clientID>TEST</clientID ><logisticProviderID>YTO</logisticProviderID><customerId></customerId><txLogisticID>VC5555555555555666</txLogisticID><tradeNo></tradeNo><mailNo></mailNo><totalServiceFee></totalServiceFee><codSplitFee></codSplitFee><orderType>1</orderType><serviceType>0</serviceType><flag></flag><sender>	<name>周小庆</name>	<postCode></postCode>	<phone></phone>	<mobile>8613681645278</mobile>	<prov>上海</prov>	<city>上海市,青浦区</city>	<address>青浦区华新镇华徐公路3029弄5号3号楼</address></sender><receiver>	<name>董小姐</name>	<postCode></postCode>	<phone></phone>	<mobile>8613601608775</mobile>	<prov>上海</prov>	<city>上海市,青浦区</city>	<address>青浦区华新镇叙中村二 78号</address></receiver><sendStartTime></sendStartTime><sendEndTime></sendEndTime><goodsValue></goodsValue><itemsValue></itemsValue><items><item>	<itemName>0</itemName>	<number>0</number>	<itemValue></itemValue></item></items><insuranceValue></insuranceValue><special>0</special><remark></remark></RequestOrder>");
            //加密
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update((xmlBuilder.toString() + parternId).getBytes("UTF-8"));
            byte[] abyte0 = messagedigest.digest();
            String data_digest = new String(Base64.encodeBase64(abyte0));
            System.out.println(data_digest);
            System.out.println(xmlBuilder.toString());
            //最终的xml
//			System.out.println(URLEncoder.encode(xmlBuilder.toString(), "UTF-8"));
            String queryString = "logistics_interface=" + URLEncoder.encode(xmlBuilder.toString(), "UTF-8")
                    + "&data_digest="+ URLEncoder.encode(data_digest,"UTF-8")
                    + "&clientId=" + URLEncoder.encode(clientId, "UTF-8")
                    +"&type=offline";

            he(apiUrl, queryString);


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void test2() {

        logisticsInterface(xml.javeBean2Xml());
        String testxxxxx = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestOrder><clientID>TEST</clientID><logisticProviderID>YTO</logisticProviderID><txLogisticID>AS83752763</txLogisticID><orderType>1</orderType><serviceType>1</serviceType><sender>	<name>周小庆</name>	<postCode></postCode>	<phone></phone>	<mobile>8613681645278</mobile>	<prov>上海</prov>	<city>上海市,青浦区</city>	<address>青浦区华新镇华徐公路3029弄5号3号楼</address></sender><receiver><name>test</name><phone>231234134</phone><postCode></postCode>	<prov>上海</prov><city>上海市,青浦区</city><address>address</address></receiver><special>0</special></RequestOrder>";
        logisticsInterface(testxxxxx);
        logisticsInterface("<RequestOrder><clientID>TEST</clientID><logisticProviderID>YTO</logisticProviderID><customerId></customerId><txLogisticID>VC5555555555555666</txLogisticID><tradeNo></tradeNo><mailNo></mailNo><totalServiceFee></totalServiceFee><codSplitFee></codSplitFee><orderType>1</orderType><serviceType>0</serviceType><flag></flag><sender>	<name>周小庆</name>	<postCode></postCode>	<phone></phone>	<mobile>8613681645278</mobile>	<prov>上海</prov>	<city>上海市,青浦区</city>	<address>青浦区华新镇华徐公路3029弄5号3号楼</address></sender><receiver>	<name>董小姐</name>	<postCode></postCode>	<phone></phone>	<mobile>8613601608775</mobile>	<prov>上海</prov>	<city>上海市,青浦区</city>	<address>青浦区华新镇叙中村二 78号</address></receiver><sendStartTime></sendStartTime><sendEndTime></sendEndTime><goodsValue></goodsValue><itemsValue></itemsValue><items><item>	<itemName>0</itemName>	<number>0</number>	<itemValue></itemValue></item></items><insuranceValue></insuranceValue><special>0</special><remark></remark></RequestOrder>");
    }

    public static void he(String apiUrl, String queryString) {

        try {
            //打开连接
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out.write(queryString);
            out.flush();
            out.close();
            //获取服务端的反馈
            String responseString = "";
            String strLine = "";
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((strLine = reader.readLine()) != null) {
                responseString += strLine + "\n";
            }
            in.close();
            System.err.print("请求的返回信息：" + responseString);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void logisticsInterface(String logisticsInterface) {
        String data_digest = null;
        String queryString = null;
        Map<String, Object> params = new HashMap<>();
        try {
            data_digest = MD5(logisticsInterface + "123456", "UTF-8");

            params.put("logistics_interface", URLEncoder.encode(logisticsInterface, "UTF-8"));
            params.put("data_digest", URLEncoder.encode(data_digest,"UTF-8"));
            params.put("clientId", URLEncoder.encode("TEST", "UTF-8"));
            params.put("type", "offline");

            System.out.println("logistics_interface:" + URLEncoder.encode(logisticsInterface, "UTF-8"));
            System.out.println("data_digest:" + URLEncoder.encode(data_digest,"UTF-8"));
            System.out.println("clientId:" + URLEncoder.encode("TEST", "UTF-8"));
            System.out.println("type:" + "offline");
            queryString = "logistics_interface=" + URLEncoder.encode(logisticsInterface, "UTF-8")
                    + "&data_digest="+ URLEncoder.encode(data_digest,"UTF-8")
                    + "&clientId=" + URLEncoder.encode("TEST", "UTF-8")
                    +"&type=offline";

        } catch (Exception e) {
        }

        he("http://jingangtest.yto56.com.cn/ordws/Vip16Servlet", queryString);

//        HttpClient httpClient = new HttpClient();
//        return httpClient.doPost("http://jingangtest.yto56.com.cn/ordws/Vip16Servlet", params);
    }

    private static String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        return new String(Base64.encodeBase64(result));
    }
}
