package org.springgear.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springgear.EnableSpringGear;
import org.springgear.beans.annotation.SpringGearBeanRegister;
import org.springgear.context.SpringGearEngineProcessor;
import org.springgear.example.service.MyServiceInterface;

public class Main {

    @Configuration
    @ComponentScan(value = "org.springgear.example")
    /**
     * 默认会使用 {@link ComponentScan} 进行包的扫描
     * 当然如果你的执行任务过多，或者包有些分离，或者不想用 {@link  CompomentScan}，那也可以使用 {@link EnableSpringGear} 的 basePackages 属性
     */
    @EnableSpringGear
    static class Config {
    }

    public static void main(String[] args) {
        System.out.println("Hello world! Hello SpringGear!");

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        MyServiceInterface service = ctx.getBean(MyServiceInterface.class);

        String out = service.single("this is single input params");
        System.out.println(out);
    }

}