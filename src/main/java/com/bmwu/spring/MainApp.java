package com.bmwu.spring;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by michael on 3/28/17.
 */
public class MainApp {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("Beans.xml");

        HelloWorld helloWorld = (HelloWorld) context.getBean("helloWorld");
        helloWorld.getMessage();
        System.out.println("helloWorld hashcode: " + helloWorld.hashCode());

        // @Deprecated
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("Beans.xml"));
        HelloWorld helloWorld1 = (HelloWorld) xmlBeanFactory.getBean("helloWorld");
        helloWorld1.getMessage();
        System.out.println("helloWorld1 hashcode: " + helloWorld1.hashCode());

        // scope = singleton
        HelloWorld helloWorld2 = (HelloWorld) context.getBean("helloWorldScope");
        helloWorld2.setMessage("scope");
        helloWorld2.getMessage();
        System.out.println("helloWorld2 hashcode: " + helloWorld2.hashCode());

        HelloWorld helloWorld3 = (HelloWorld) context.getBean("helloWorldScope");
        helloWorld3.getMessage();
        System.out.println("helloWorld3 hashcode: " + helloWorld3.hashCode());

        // scope = prototype
        HelloWorld helloWorld4 = (HelloWorld) context.getBean("helloWorldScope2");
        helloWorld4.setMessage("scope");
        helloWorld4.getMessage();
        System.out.println("helloWorld4 hashcode: " + helloWorld4.hashCode());

        HelloWorld helloWorld5 = (HelloWorld) context.getBean("helloWorldScope2");
        helloWorld5.getMessage();
        System.out.println("helloWorld5 hashcode: " + helloWorld5.hashCode());

    }
}
