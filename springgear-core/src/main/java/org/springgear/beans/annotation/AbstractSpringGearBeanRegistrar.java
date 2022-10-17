package org.springgear.beans.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springgear.beans.AbstractSpringGearProxyProcessor;
import org.springgear.beans.DefaultBeanDefinitionProcessor;
import org.springgear.context.SpringGearProxyClassPathScanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractSpringGearBeanRegistrar implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private static final List<String> DEFAULT_BASE_PACKAGES = Collections.singletonList("*");


//    protected abstract void registerBeanDefinitions()

    protected void registerBeanDefinitions(BeanDefinitionRegistry registry, List<String> basePackages, AbstractSpringGearProxyProcessor processor) {

        if (CollectionUtils.isEmpty(basePackages)) {
            basePackages = DEFAULT_BASE_PACKAGES;
        }


        log.debug("start SpringGearProxyClass scan. ");

        if (processor == null) {
            processor = new DefaultBeanDefinitionProcessor();
        }


        SpringGearProxyClassPathScanner scanner = new SpringGearProxyClassPathScanner(registry, processor);

        scanner.setResourceLoader(this.resourceLoader);

        System.out.println(this.resourceLoader);
        System.out.println(basePackages);

        scanner.scan(basePackages.toArray(new String[basePackages.size()]));
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


}
