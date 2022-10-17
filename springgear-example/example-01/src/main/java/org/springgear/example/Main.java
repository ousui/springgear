package org.springgear.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springgear.beans.annotation.SpringGearBeanRegister;
import org.springgear.context.SpringGearEngineProcessor;
import org.springgear.example.service.MyServiceInterface;

public class Main {

    @Configuration
    // use this to scan handler
    @ComponentScan(value = "org.springgear.example")
    static class Config {

        @Bean
        public SpringGearBeanRegister springGearBeanRegister() {
            return new SpringGearBeanRegister("org.springgear.example.service");
        }

        @Bean
        public SpringGearEngineProcessor springGearEngineProcessor() {
            return new SpringGearEngineProcessor();
        }

    }

    public static void main(String[] args) {
        System.out.println("Hello world!");

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        MyServiceInterface service = ctx.getBean(MyServiceInterface.class);

        single(service);
        multi(service);
        order(service);
        mixed(service);

    }

    public static void single(MyServiceInterface service) {
        String out = service.single("this is single input params");
        System.out.println(out);
    }

    public static void multi(MyServiceInterface service) {
        String out = service.multi("this is multi input params");
        System.out.println(out);
    }

    public static void order(MyServiceInterface service) {
        String out = service.order("order");
        System.out.println(out);
    }

    public static void mixed(MyServiceInterface service) {
        String out = service.mixed("mixed");
        System.out.println(out);
    }
}