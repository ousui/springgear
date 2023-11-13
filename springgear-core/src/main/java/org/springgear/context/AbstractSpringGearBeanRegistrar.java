package org.springgear.context;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;
import org.springgear.core.router.SpringGearRouterBeanProcessor;

import java.util.Collections;
import java.util.List;

/**
 * SpringGear Bean 注册流程
 * TODO 重新考虑注册流程，优化代码结构
 */
@Slf4j
public abstract class AbstractSpringGearBeanRegistrar implements ResourceLoaderAware {

    @Setter
    private ResourceLoader resourceLoader;

    private static final List<String> DEFAULT_BASE_PACKAGES = Collections.singletonList("*");

    protected void registerBeanDefinitions(BeanDefinitionRegistry registry, List<String> basePackages) {

        if (CollectionUtils.isEmpty(basePackages)) {
            basePackages = DEFAULT_BASE_PACKAGES;
        }

        log.debug("start SpringGearProxyClass scan, packages: {}", basePackages);

        SpringGearRouterBeanProcessor scanner = new SpringGearRouterBeanProcessor(registry);

        scanner.setResourceLoader(this.resourceLoader);
        scanner.scan(basePackages.toArray(new String[0]));
    }

}
