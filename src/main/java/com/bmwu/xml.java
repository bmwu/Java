package com.bmwu;

import org.dom4j.*;

import java.util.List;

/**
 * Created by michael on 3/8/17.
 */
public class xml {

    public static void main(String[] args) {

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
}


