package org.springgear.core.context.registrars;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springgear.EnableSpringGear;
import org.springgear.core.beans.AbstractSpringGearProxyProcessor;
import org.springgear.core.beans.DefaultBeanDefinitionProcessor;
import org.springgear.core.context.AbstractSpringGearBeanRegistrar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用 注解 形式的注入
 *
 * @since 2.1.0
 */
public class SpringGearBeanAnnotationRegistrar extends AbstractSpringGearBeanRegistrar implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MergedAnnotations annotations = importingClassMetadata.getAnnotations();
        if (false == annotations.isPresent(EnableSpringGear.class)) {
            return;
        }

        // 获取注解
        EnableSpringGear springGearAnno = annotations.get(EnableSpringGear.class).synthesize();

        List<String> basePackages = this.getBasePackages(importingClassMetadata, springGearAnno);

        AbstractSpringGearProxyProcessor processor;
        if (springGearAnno.processor() == null) {
            processor = new DefaultBeanDefinitionProcessor();
        } else {
            processor = new DefaultBeanDefinitionProcessor();
        }

        this.registerBeanDefinitions(registry, basePackages, processor);
    }

    /**
     * 获取 base packages
     *
     * @param importingClassMetadata
     * @param springGearAnno
     * @return
     */
    private static List<String> getBasePackages(AnnotationMetadata importingClassMetadata, EnableSpringGear springGearAnno) {
        List<String> basePackages = new ArrayList<>();
        basePackages.addAll(
                Arrays.stream(springGearAnno.basePackages()).filter(StringUtils::hasText).collect(Collectors.toList())
        );

        // 查找 ComponentScan 的包路径
        AnnotationAttributes componentScanAnno = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(ComponentScan.class.getName()));

        if (null != componentScanAnno) {
            basePackages.addAll(
                    Arrays.stream(componentScanAnno.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));

            basePackages.addAll(Arrays.stream(componentScanAnno.getStringArray("basePackages")).filter(StringUtils::hasText)
                    .collect(Collectors.toList()));

            basePackages.addAll(Arrays.stream(componentScanAnno.getClassArray("basePackageClasses")).map(ClassUtils::getPackageName)
                    .collect(Collectors.toList()));
        }
        return basePackages;
    }

}
