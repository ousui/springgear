package org.springgear.core.context.registrars;

import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springgear.core.beans.AbstractSpringGearProxyProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springgear.core.context.AbstractSpringGearBeanRegistrar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Spring Gear 框架配置入口
 * 类内执行顺序
 * 1. postProcessBeanDefinitionRegistry
 * 2. postProcessBeanFactory
 * 在这之前的 bean definition 可以查找到 ${} 类型的注解。 spring 会对注入的数据进行处理。
 * 在这之后定义的 bean definition 是找不到的，类已经完成了依赖分析。
 * 3. postProcessBeforeInitialization
 * 4. postProcessAfterInitialization
 *
 * @author SHUAI.W
 * @since 1.0.0 2018-01-05
 **/
@Slf4j
public class SpringGearBeanNormalRegistrar extends AbstractSpringGearBeanRegistrar implements BeanDefinitionRegistryPostProcessor {

    /**
     * 需要扫描的包。
     */
    @Setter
    private List<String> basePackages;

    @Setter
    private AbstractSpringGearProxyProcessor beanDefinitionProcessor;

    public SpringGearBeanNormalRegistrar() {
        this("");
    }

    public SpringGearBeanNormalRegistrar(String... basePackages) {
        if (this.basePackages == null) {
            this.basePackages = new ArrayList<>();
        }
        if (basePackages != null) {
            this.basePackages.addAll(Arrays.stream(basePackages).filter(StringUtils::hasText).collect(Collectors.toList()));
        }
    }


    /**
     * 处理修改接口自动实现.
     *
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        super.registerBeanDefinitions(registry, this.basePackages, this.beanDefinitionProcessor);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        保持空
    }

    public List<String> setBasePackage(String basePackage) {
        if (this.basePackages == null) {
            this.basePackages = new ArrayList<>();
        }
        this.basePackages.add(basePackage);
        return this.basePackages;
    }
}
