package com.bmwu.utils;

import com.bmwu.utils.dto.RequestOrder;
import com.bmwu.utils.dto.RequestUser;
import org.apache.commons.lang.RandomStringUtils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by michael on 3/8/17.
 */
public class xml {

    public static void main(String[] args) {

        javeBean2Xml();

    }

    public static String javeBean2Xml() {

        RequestOrder order = new RequestOrder();
        order.setTxLogisticID("AS" + RandomStringUtils.randomNumeric(8));

        RequestUser sender = new RequestUser();
        sender.setName("test");
        sender.setProv("上海");
        sender.setCity("上海市,青浦区");
        sender.setAddress("address");
        sender.setPostCode("12");
        sender.setPhone("231234134");

        RequestUser receiver = new RequestUser();
        receiver.setName("test");
        receiver.setProv("上海");
        receiver.setPostCode("12");
        receiver.setCity("上海市,青浦区");
        receiver.setAddress("address");
        receiver.setPhone("231234134");

        order.setSender(sender);
        order.setReceiver(receiver);

        Document doc = DocumentHelper.createDocument();
        //创建根节点
        Element root = DocumentHelper.createElement("RequestOrder");//根节点
        doc.setRootElement(root);

        try {
            XmlUtil.copyAttrToEle(order, root);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println(document2String(doc));
        return document2String(doc);
    }

    private static String document2String(Document doc) {
        String str = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputFormat format = new OutputFormat(" ", true , "UTF-8");
            XMLWriter writer = new XMLWriter(out,format);
            writer.write(doc);
            str = out.toString("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public void testXml() {

        String xml = "<note att='bb'>\n" +
                     "  <to at='aa' readonly='true'><test bt='bb'>Tove</test></to>\n" +
                     "  <from>michael</from>\n" +
                     "  <heading>Reminder</heading>\n" +
                     "  <body>Don't forget me this weekend!</body>\n" +
                     "</note>";
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        removeAttributeData(root.attributes());
        treeWalk(root);
        Document doc = root.getDocument();
        String xmlNew = doc.asXML();
        System.out.println(xmlNew);

        boolean b = isValidXML("%lkt!00960016f7zfU2pgT/b5Twnf+x% ");
    }



    public static void treeWalk(Element element) {

        for (int i = 0, size = element.nodeCount(); i < size ; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                treeWalk((Element) node);
                removeAttributeData(((Element) node).attributes());
            } else {
                node.setText("");
            }
        }
    }

    public static void removeAttributeData(List<Attribute> attributeList) {

        for (Attribute attribute : attributeList) {
            if (!attribute.isReadOnly()) {
                attribute.setData("");
            }
        }
    }

    public static void print(Element element) {

        for (int i = 0, size = element.nodeCount(); i < size ; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                print((Element) node);
            } else {
                System.out.println("Name: " + node.getName());
                System.out.println("Text: " + node.getText());
            }
        }
    }

    public static boolean isValidXML(String value) {
        try {
            DocumentHelper.parseText(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}


