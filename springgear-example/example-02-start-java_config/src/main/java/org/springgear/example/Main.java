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
        System.out.println("Hello world! Hello SpringGear!");

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        MyServiceInterface service = ctx.getBean(MyServiceInterface.class);


        String out = service.single("this is single input params");
        System.out.println(out);


    }


}