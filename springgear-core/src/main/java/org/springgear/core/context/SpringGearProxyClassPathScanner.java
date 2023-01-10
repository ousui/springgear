package org.springgear.core.context;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionDefaults;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springgear.core.annotation.SpringGearProxy;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springgear.engine.beans.factory.SpringGearProxyFactoryBean;

import java.util.Arrays;
import java.util.Set;

/**
 * 类扫描及后续处理的一个 scanner.
 * 主要是扫描 {@link SpringGearProxy} 的代理接口，形成可执行的接口
 *
 * @author
 * @see SpringGearProxy
 **/
public class SpringGearProxyClassPathScanner extends ClassPathBeanDefinitionScanner {


    public SpringGearProxyClassPathScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
        // 使用注解 {@link SpringGearProxy} 的过滤保证可以找到需要代理的类
        this.addIncludeFilter(new AnnotationTypeFilter(SpringGearProxy.class));
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
        String targetBeanClassName = definition.getBeanClassName();
        String beanName = holder.getBeanName();

        Class<?> proxyInterface;
        try {
            proxyInterface = Class.forName(targetBeanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        SpringGearProxy proxyAnnotation = (SpringGearProxy) proxyInterface.getAnnotation(SpringGearProxy.class);
        if (proxyAnnotation == null) {
            logger.info(String.format("this component is not '%s', need not process.", SpringGearProxy.class));
            return;
        }

        logger.debug(
                String.format("Creating SpringGearProxy Interface implement FactoryBean with name '%s' and definition bean '%s'",
                        beanName, definition.getBeanClassName())
        );

        /**
         * 放入两个构造参数
         */
        definition.getConstructorArgumentValues().addGenericArgumentValue(targetBeanClassName);
        definition.setBeanClass(SpringGearProxyFactoryBean.class);
        definition.applyDefaults(new BeanDefinitionDefaults());

        /**
         * 默认按照类型注入
         */
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    }

//    private void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
//        logger.info(String.format("Creating bean with name '%s' for '%s'.", name, beanDefinition.getBeanClassName()));
//
//        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//        beanFactory.registerBeanDefinition(name, beanDefinition);
//    }

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
