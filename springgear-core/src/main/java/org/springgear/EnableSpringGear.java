package org.springgear;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springgear.core.context.registrar.SpringGearBeanAnnotationRegistrar;
import org.springgear.engine.execute.executors.AbstractSpringGearEngineExecutor;
import org.springgear.engine.execute.executors.DefaultSpringGearEngineExecutor;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SpringGearConfiguration.class, SpringGearBeanAnnotationRegistrar.class})
public @interface EnableSpringGear {

    /**
     * 默认要进行扫描的包，会和 {@link org.springframework.context.annotation.ComponentScan} 所扫描的包进行合并
     *
     * @return
     * @see org.springframework.context.annotation.ComponentScan
     */
    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * value 的别名
     *
     * @return
     */
    @AliasFor("value")
    String[] basePackages() default {};

    /**
     * 执行过程处理器
     *
     * @return
     */
    Class<? extends AbstractSpringGearEngineExecutor> executor() default DefaultSpringGearEngineExecutor.class;
}
