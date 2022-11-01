package org.springgear.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springgear.example.service.MyServiceInterface;

public class Main {


    public static void main(String[] args) {
        System.out.println("Hello world! Hello SpringGear!");

        ApplicationContext ctx = new GenericXmlApplicationContext("classpath:spring-config.xml");

        MyServiceInterface service = ctx.getBean(MyServiceInterface.class);

        String out = service.single("this is single input params");
        System.out.println(out);

    }
}