package org.springgear.context;

import org.springframework.beans.factory.InitializingBean;
import org.springgear.beans.factory.SpringGearProxyFactoryBean;
import org.springgear.context.utils.SpringGearEngineUtils;
import org.springgear.core.AbstractSpringGearEngineExecutor;
import org.springgear.core.annotation.SpringGearEngine;
import org.springgear.core.context.SpringGearResultWrapper;
import org.springgear.core.handler.SpringGearHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * spring gear 框架核心业务流程处理类
 *
 * @author SHUAI.W
 * @since 2020/12/13
 **/
@Slf4j
public class SpringGearEngineProcessor implements BeanPostProcessor, ApplicationContextAware, InitializingBean {

    /**
     * spring 上下文
     */
    @Setter
    private ApplicationContext applicationContext;

    /**
     * 引擎执行类
     */
    private final Class<? extends AbstractSpringGearEngineExecutor> springGearEngineExecutorClass;

    /**
     * 用于存储
     *
     * @see SpringGearHandler
     */
    private Map<String, List<SpringGearHandler>> handlers = new HashMap<>();

    /**
     * 构造方法
     *
     * @param springGearEngineExecutorClass
     */
    public SpringGearEngineProcessor(Class<? extends AbstractSpringGearEngineExecutor> springGearEngineExecutorClass) {
        this.springGearEngineExecutorClass = springGearEngineExecutorClass;
    }

    @Override
    public void afterPropertiesSet() {
        // 初始化 spring gear handler
        SpringGearEngineUtils.groupBeanByQualifier(applicationContext, SpringGearHandler.class, handlers, (clazz) -> {
            Qualifier group = clazz.getAnnotation(Qualifier.class);
//            SpringGearEvent event = clazz.getAnnotation(SpringGearEvent.class);
//            if (event != null) {
//                SpringGearEventListener listener = applicationContext.getBean(event.bean());
//                if (listener != null) {
//                    listeners.put(group.value(), listener);
//                } else {
//                    log.warn("the bean of class {} have no instance, please use spring do it.", event.bean());
//                }
//            }
            return group;
        });

    }


    /**
     * 在初始化前，需要处理 spring gear core 注解的相关方法。</br >
     * 基于 {@link SpringGearProxyFactoryBean} 进行处理，非工厂方法不进行处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 判断是否为代理工厂类
        if (!SpringGearProxyFactoryBean.class.isAssignableFrom(bean.getClass())) {
            return bean;
        }
        log.info("This bean '{}' need process to spring gear core '{}'.", bean.getClass(), SpringGearProxyFactoryBean.class);

        Class proxyInterface = ((SpringGearProxyFactoryBean) bean).getObjectType();
        Method[] methods = proxyInterface.getDeclaredMethods();

        // 找到接口中所有标记 @SpringGearEngine 注解的方法，未标记不做处理
        for (Method method : methods) {
            SpringGearEngine engine = AnnotationUtils.findAnnotation(method, SpringGearEngine.class);

            if (engine == null) {
                continue;
            }

            // 构建 spring gear core
            this.buildSpringGearEngine(engine, method);
        }

        return bean;
    }


    /**
     * 构建 spring gear core
     *
     * @param engine
     * @param method
     */
    private void buildSpringGearEngine(SpringGearEngine engine, Method method) {
        String beanName = SpringGearEngineUtils.getInterfaceBeanName(engine, method);

        // 如果这个 bean 已经注册
        if (this.applicationContext.containsBean(beanName)) {
            log.warn("spring has contains the bean {}", beanName);
            return;
        }

        // 生成 bean definition，将 handler 注入，形成工作工作流
        final Qualifier[] handlers = engine.handlers();
        String wrapperName = engine.wrapper().value();

        if (false == StringUtils.hasText(wrapperName)) {
            wrapperName = SpringGearResultWrapper.DEFAULT_BEAN_NAME;
        }

        // 获取所有 wrapper 类
        Map<String, SpringGearResultWrapper> wrappers = this.applicationContext.getBeansOfType(SpringGearResultWrapper.class);

        SpringGearResultWrapper wrapper = null;
        if (false == CollectionUtils.isEmpty(wrappers)) {
            wrapper = wrappers.get(wrapperName);
        }

        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(springGearEngineExecutorClass)
                .addPropertyValue("handlers", this.getBeanList(handlers, this.handlers, true))
//                .addPropertyValue("listeners", this.getBeanList(handlers, this.listeners, false))
                .addPropertyValue("wrapper", wrapper)
                .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME)
                .getBeanDefinition();

        // 注册 bean definition
        SpringGearEngineUtils.registerBeanDefinition(applicationContext, beanName, beanDefinition);
    }

    /**
     * 根据 qualifier 查找 bean list
     *
     * @param qualifiers
     * @return
     */
    private <T> List<T> getBeanList(Qualifier[] qualifiers, Map<String,List<T>> map, boolean sort) {
        List<T> beans = new ArrayList<>();
        for (Qualifier qualifier : qualifiers) {

            final Collection<T> elements = map.get(qualifier.value());
            if (CollectionUtils.isEmpty(elements)) {
                continue;
            }
            beans.addAll(elements);
        }
        if (sort) {
            // 使用 spring 提供的 ordered 比较器进行比较排序。
            AnnotationAwareOrderComparator.sort(beans);
        }
        return beans;
    }


}
