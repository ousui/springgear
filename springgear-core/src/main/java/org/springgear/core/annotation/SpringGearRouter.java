package org.springgear.core.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注本注解表示将 service 生成 spring gear 代理类
 *
 * @author SHUAI.W 2018-01-05
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SpringGearRouter {

    /**
     * 该组件的 bean 默认生成的 bean name。
     *
     * @return 代理值
     */
    String value() default "";
}
