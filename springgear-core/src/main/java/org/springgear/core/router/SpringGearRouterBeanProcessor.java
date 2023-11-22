package org.springgear.core.router;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springgear.core.annotation.SpringGearRouter;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springgear.core.engine.SpringGearEngineFactoryBean;

import java.util.Arrays;
import java.util.Set;

/**
 * 类扫描及后续处理的一个 scanner.
 * 主要是扫描 {@link SpringGearRouter} 的代理接口，形成可执行的接口
 *
 * @author
 * @see SpringGearRouter
 **/
public class SpringGearRouterBeanProcessor extends ClassPathBeanDefinitionScanner {


    /**
     * 使用注解 {@link SpringGearRouter} 的过滤保证可以找到需要代理的类
     *
     * @param registry
     * @see SpringGearRouter
     */
    public SpringGearRouterBeanProcessor(BeanDefinitionRegistry registry) {
        super(registry, false);
        this.addIncludeFilter(new AnnotationTypeFilter(SpringGearRouter.class));
    }


    /**
     * 复写方法，扫描包，获取接口，进行代理实现。
     *
     * @param basePackages
     * @return
     */
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        // 扫描包，找到需要定义的 bean
        Set<BeanDefinitionHolder> holders = super.doScan(basePackages);
        if (holders.isEmpty()) {
            logger.warn("No Spring Gear Interface found in package: '" + Arrays.toString(basePackages) + "'. Please check your configuration.");
            return holders;
        }

        for (BeanDefinitionHolder holder : holders) {
            this.autoProxyInterface(holder);
        }

        return holders;
    }

    public void autoProxyInterface(BeanDefinitionHolder holder) {
        GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
        String originInterfaceClassName = definition.getBeanClassName();
        String beanName = holder.getBeanName();

        if (originInterfaceClassName == null) {
            logger.error("originInterfaceClassName not define for bean: " + beanName);
            return;
        }

        Class<?> originInterface;
        try {
            originInterface = Class.forName(originInterfaceClassName);
        } catch (ClassNotFoundException e) {
            logger.error("class not found: ", e);
            return;
        }

        SpringGearRouter proxyAnnotation = originInterface.getAnnotation(SpringGearRouter.class);
        // 一般情况这里都有值
        if (proxyAnnotation == null) {
            logger.info(String.format("this component is not '%s', need not process.", SpringGearRouter.class));
            return;
        }

        logger.debug(
                String.format("Creating SpringGearProxy Interface implement FactoryBean with name '%s' and definition bean '%s'",
                        beanName, definition.getBeanClassName())
        );

        definition.setBeanClass(SpringGearEngineFactoryBean.class);
        definition.getConstructorArgumentValues()
                .addGenericArgumentValue(originInterfaceClassName);
        definition.applyDefaults(this.getBeanDefinitionDefaults());
        // 默认按照类型注入
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    }

    /**
     * 必须是接口类型
     *
     * @param beanDefinition
     * @return true/false
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return metadata.isInterface() && metadata.isIndependent();
    }
}
