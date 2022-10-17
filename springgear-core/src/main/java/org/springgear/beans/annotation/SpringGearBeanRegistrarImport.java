package org.springgear.beans.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springgear.EnableSpringGear;
import org.springgear.beans.AbstractSpringGearProxyProcessor;
import org.springgear.beans.DefaultBeanDefinitionProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpringGearBeanRegistrarImport extends AbstractSpringGearBeanRegistrar implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MergedAnnotations annotations = importingClassMetadata.getAnnotations();
        if (false == annotations.isPresent(EnableSpringGear.class)) {
            return;
        }
        List<String> basePackages = new ArrayList<>();

        EnableSpringGear springGearAnno = annotations.get(EnableSpringGear.class).synthesize();

        basePackages.addAll(
                Arrays.stream(springGearAnno.basePackages()).filter(StringUtils::hasText).collect(Collectors.toList())
        );

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

        AbstractSpringGearProxyProcessor processor;
        if (springGearAnno.processor() == null) {
            processor = new DefaultBeanDefinitionProcessor();
        } else {
            processor = new DefaultBeanDefinitionProcessor();
        }

        this.registerBeanDefinitions(registry, basePackages, processor);
    }

}
