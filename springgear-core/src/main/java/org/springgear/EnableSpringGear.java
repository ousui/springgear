package org.springgear;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springgear.context.SpringGearBeanAnnotationRegistrar;

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

}
