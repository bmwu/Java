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

        // @Deprecated
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("Beans.xml"));
        HelloWorld helloWorld1 = (HelloWorld) xmlBeanFactory.getBean("helloWorld");
        helloWorld1.getMessage();

        // scope = singleton
        HelloWorld helloWorld2 = (HelloWorld) context.getBean("helloWorldScope");
        helloWorld2.setMessage("scope");
        helloWorld2.getMessage();

        HelloWorld helloWorld3 = (HelloWorld) context.getBean("helloWorldScope");
        helloWorld3.getMessage();

        // scope = prototype
        HelloWorld helloWorld4 = (HelloWorld) context.getBean("helloWorldScope2");
        helloWorld4.setMessage("scope");
        helloWorld4.getMessage();

        HelloWorld helloWorld5 = (HelloWorld) context.getBean("helloWorldScope2");
        helloWorld5.getMessage();

    }
}
