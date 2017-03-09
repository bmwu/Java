package com.bmwu;

import org.dom4j.*;

/**
 * Created by michael on 3/8/17.
 */
public class xml {

    public static void main(String[] args) {

        String xml = "<person> <name>James</name> </person>";
//        System.out.println(xml);
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
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
            } else {
                node.setText("");
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


