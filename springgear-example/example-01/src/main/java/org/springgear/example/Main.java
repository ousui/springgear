package org.springgear.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springgear.beans.AbstractSpringGearProxyProcessor;
import org.springgear.beans.DefaultBeanDefinitionProcessor;
import org.springgear.context.SpringGearEngineProcessor;
import org.springgear.context.SpringGearStarter;
import org.springgear.core.AbstractSpringGearEngineExecutor;
import org.springgear.core.DefaultSpringGearEngineExecutor;
import org.springgear.example.service.MyServiceInterface;

public class Main {

    @Configuration
    // use this to scan handler
    @ComponentScan("org.springgear.example")
    static class Config {

        @Bean
        public SpringGearStarter starter() {
            // use this to scan SpringGearProxy
            SpringGearStarter starter = new SpringGearStarter("org.springgear.example.service");
            return starter;
        }

        @Bean
        public SpringGearEngineProcessor springGearEngineProcessor() {
            return new SpringGearEngineProcessor(DefaultSpringGearEngineExecutor.class);
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