package org.springgear.eventbus.annotation;

import org.springgear.eventbus.SpringGearEventListener;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * spring event bus 注解
 *
 * @author SHUAI.W
 * @since 2020/12/26
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpringGearEvent {

    /**
     * 关联的 event 事件处理 bean
     *
     * @return
     */
    Class<? extends SpringGearEventListener> bean();

//    Qualifier value();
//    String beanName();

    /**
     * 对传递的上下文对象进行复制
     *
     * @return
     */
    boolean copied() default false;

}
