package org.springgear.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springgear.context.SpringGearBeanRegistrar;
import org.springgear.core.engine.SpringGearEngineBeanProcessor;
import org.springgear.core.engine.executor.AbstractSpringGearEngineExecutor;
import org.springgear.example.service.MyServiceInterface;

public class Main {

    @Configuration
    // use this to scan handler
    @ComponentScan(value = "org.springgear.example")
    static class Config {

        @Bean
        public SpringGearBeanRegistrar springGearBeanRegister() {
            return new SpringGearBeanRegistrar("org.springgear.example.service");
        }

        @Bean
        public SpringGearEngineBeanProcessor springGearEngineProcessor() {
            return new SpringGearEngineBeanProcessor(AbstractSpringGearEngineExecutor.class);
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