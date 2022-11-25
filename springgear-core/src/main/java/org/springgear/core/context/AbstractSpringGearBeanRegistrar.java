package org.springgear.core.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * SpringGear Bean 注册流程
 */
@Slf4j
public abstract class AbstractSpringGearBeanRegistrar implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private static final List<String> DEFAULT_BASE_PACKAGES = Collections.singletonList("*");


    protected void registerBeanDefinitions(BeanDefinitionRegistry registry, List<String> basePackages) {

        if (CollectionUtils.isEmpty(basePackages)) {
            basePackages = DEFAULT_BASE_PACKAGES;
        }

        log.debug("start SpringGearProxyClass scan, packages: {}", basePackages);

        SpringGearProxyClassPathScanner scanner = new SpringGearProxyClassPathScanner(registry);

        scanner.setResourceLoader(this.resourceLoader);
        scanner.scan(basePackages.toArray(new String[basePackages.size()]));
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


}
