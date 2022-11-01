package org.springgear;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springgear.core.beans.AbstractSpringGearProxyProcessor;
import org.springgear.core.beans.DefaultBeanDefinitionProcessor;
import org.springgear.core.register.SpringGearBeanRegistrarAnnotation;
import org.springgear.core.execute.AbstractSpringGearEngineExecutor;
import org.springgear.core.execute.DefaultSpringGearEngineExecutor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SpringGearConfiguration.class, SpringGearBeanRegistrarAnnotation.class})
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
     * 代理处理器
     *
     * @return
     */
    Class<? extends AbstractSpringGearProxyProcessor> processor() default DefaultBeanDefinitionProcessor.class;

    /**
     * 执行过程处理器
     *
     * @return
     */
    Class<? extends AbstractSpringGearEngineExecutor> executor() default DefaultSpringGearEngineExecutor.class;
}
