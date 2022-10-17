package org.springgear;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springgear.beans.AbstractSpringGearProxyProcessor;
import org.springgear.beans.DefaultBeanDefinitionProcessor;
import org.springgear.beans.annotation.SpringGearBeanRegistrarImport;
import org.springgear.core.AbstractSpringGearEngineExecutor;
import org.springgear.core.DefaultSpringGearEngineExecutor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SpringGearConfiguration.class, SpringGearBeanRegistrarImport.class})
public @interface EnableSpringGear {

    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

    Class<? extends AbstractSpringGearProxyProcessor> processor() default DefaultBeanDefinitionProcessor.class;

    Class<? extends AbstractSpringGearEngineExecutor> executor() default DefaultSpringGearEngineExecutor.class;
}
