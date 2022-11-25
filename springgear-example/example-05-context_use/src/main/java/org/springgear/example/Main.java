package org.springgear.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springgear.EnableSpringGear;
import org.springgear.example.service.MyServiceInterface;

import java.util.Map;

public class Main {

    @Configuration
    @ComponentScan(value = "org.springgear.example")
    @EnableSpringGear
    static class Config {
    }

    public static void main(String[] args) {
        System.out.println("Hello world! Hello SpringGear!");

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        MyServiceInterface service = ctx.getBean(MyServiceInterface.class);

        single(service);
        multi(service);
        order(service);
//        mixed(service);

    }

    public static void single(MyServiceInterface service) {
        Map<String, String> out = service.single("this is single input params");
        System.out.println(out);
        System.out.println("------------------------------------------------");
    }

    public static void multi(MyServiceInterface service) {
        String out = service.multi("this is multi input params");
        System.out.println(out);
        System.out.println("------------------------------------------------");
    }

    public static void order(MyServiceInterface service) {
        String out = service.order("order");
        System.out.println(out);
        System.out.println("------------------------------------------------");
    }

    public static void mixed(MyServiceInterface service) {
        String out = service.mixed("mixed");
        System.out.println(out);
        System.out.println("------------------------------------------------");
    }
}