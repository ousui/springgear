package org.springgear.core.engine;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springgear.core.engine.executors.AbstractSpringGearEngineExecutor;
import org.springgear.core.handler.SpringGearHandlerInterface;
import org.springgear.support.utils.SpringGearUtils;
import org.springgear.core.annotation.SpringGearEngine;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring gear SpringGearEngine bean 处理器
 *
 * @author SHUAI.W
 * @since 2020/12/13
 **/
@Slf4j
public class SpringGearEngineBeanProcessor implements
        BeanPostProcessor
        , ApplicationContextAware, InitializingBean {

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
     * @see SpringGearHandlerInterface
     */
    private Map<String, List<SpringGearHandlerInterface>> handlers = new ConcurrentHashMap<>();

    private final static String PROPERTY_FIELD_HANDLER_NAME = "handlers";

    /**
     * 构造方法
     */
    public SpringGearEngineBeanProcessor(Class<? extends AbstractSpringGearEngineExecutor> springGearEngineExecutorClass) {
        this.springGearEngineExecutorClass = springGearEngineExecutorClass;
    }

    @Override
    public void afterPropertiesSet() {
        // 初始化 spring gear handler
        SpringGearUtils.groupBeanByQualifier(
                applicationContext,
                SpringGearHandlerInterface.class,
                handlers,
                (clazz) -> clazz.getAnnotation(Qualifier.class)
        );
    }


    /**
     * 在初始化前，需要处理 spring gear core 注解的相关方法。</br >
     * 基于 {@link SpringGearEngineFactoryBean} 进行处理，非工厂方法不进行处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 判断是否为代理工厂类
        if (!SpringGearEngineFactoryBean.class.isAssignableFrom(bean.getClass())) {
            return bean;
        }
        log.info("This bean '{}' need process to spring gear core '{}'.", bean.getClass(), SpringGearEngineFactoryBean.class);

        Class proxyInterface = ((SpringGearEngineFactoryBean) bean).getObjectType();
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
     * @param engineAnno
     * @param method
     */
    private void buildSpringGearEngine(SpringGearEngine engineAnno, Method method) {
        String beanName = SpringGearUtils.getInterfaceBeanName(engineAnno, method);

        // 如果这个 bean 已经注册
        if (this.applicationContext.containsBean(beanName)) {
            log.warn("springgear has contains the bean {}", beanName);
            return;
        }

        // 生成 bean definition，将 handler 注入，形成工作工作流
        final Qualifier[] handlerDefs = engineAnno.handlers();
        List<?> handlers = this.getBeanList(handlerDefs, this.handlers, true);
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(this.springGearEngineExecutorClass)
                // handler
                .addPropertyValue(PROPERTY_FIELD_HANDLER_NAME, handlers)
                // context class
                .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME)
                .getBeanDefinition();

        // 注册 bean definition
        SpringGearUtils.registerBeanDefinition(applicationContext, beanName, beanDefinition);
    }

    /**
     * 根据 qualifier 查找 bean list
     *
     * @param qualifiers
     * @return
     */
    private <T> List<T> getBeanList(Qualifier[] qualifiers, Map<String, List<T>> map, boolean sort) {
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
