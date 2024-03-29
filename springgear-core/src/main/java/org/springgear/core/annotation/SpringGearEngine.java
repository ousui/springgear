package org.springgear.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springgear.core.context.SpringGearContextValue;
import org.springgear.core.engine.wrapper.SpringGearResultWrapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 绑定到方法上的工作流注解。
 * 在这里不定义直接使用的 handler 实现类，是因为 service 不会向下 impl 查找依赖。这样会避免出现循环依赖的问题。
 *
 * @author SHUAI.W 2018-01-05
 * @see Qualifier
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpringGearEngine {
    /**
     * 绑定的 bean name，留空使用对应的方法名。
     *
     * @return name
     */
    String name() default "";

    /**
     * 标记本流程处理的来源，如果未 empty，使用默认处理
     *
     * @return
     */
    String source() default "";

    /**
     * handler 组，支持多重定义。
     *
     * @return Qualifier
     */
    Qualifier[] handlers();

    /**
     * 对返回数据的包裹器，@Qualifier == null 的情况，会使用 original wrapper
     *
     * @return
     */
    Qualifier wrapper() default @Qualifier(SpringGearResultWrapper.DEFAULT_BEAN_NAME);

    /**
     * spring gear context value boxes class
     *
     * @return
     */
    Class<? extends SpringGearContextValue> contextValueClass() default SpringGearContextValue.class;

}
