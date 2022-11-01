package org.springgear.core.register.context.utils;

import org.springgear.core.annotation.SpringGearEngine;
import org.springgear.support.enums.SymbolEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author SHUAI.W
 * @since 2020/12/13
 **/
@Slf4j
public class SpringGearEngineUtils {

    /**
     * 代理类 bean 的前缀
     */
    public final static String INTERFACE_PREFIX = "springgear.";

    /**
     * 按照 method 获取接口定义的 bean name，规则：
     * springgear . interface 类的 simple class name # method name
     * 例如： springgear.springGearInterface#method1
     *
     * @param engine SpringGearEngine
     * @param method 默认调用的  method
     * @return bean name
     */
    public static String getInterfaceBeanName(SpringGearEngine engine, Method method) {
        if (engine != null && StringUtils.hasText(engine.name())) {
            return engine.name();
        }
        String clazz = method.getDeclaringClass().getSimpleName();
        return INTERFACE_PREFIX.concat(clazz).concat(SymbolEnum.HASH.getString()).concat(method.getName());
    }

    /**
     * 注册 bean definition，进行文件描述
     *
     * @param applicationContext
     * @param name
     * @param beanDefinition
     */
    public static void registerBeanDefinition(ApplicationContext applicationContext, String name, BeanDefinition beanDefinition) {
        log.info("Creating bean with name '{}' for '{}'.", name, beanDefinition.getBeanClassName());

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        beanFactory.registerBeanDefinition(name, beanDefinition);
    }

    /**
     * 使用 {@link Qualifier } 内部定义进行分组
     *
     * @param applicationContext
     * @param clazz
     * @param map
     * @param function
     * @param <T>
     * @see Qualifier
     */
    public static <T> void groupBeanByQualifier(ApplicationContext applicationContext, Class<T> clazz, Map<String, List<T>> map, Function<Class<T>, Qualifier> function) {
        Map<String, T> classMap = applicationContext.getBeansOfType(clazz);
        if (CollectionUtils.isEmpty(classMap)) {
            return;
        }

        if (function == null) {
            return;
        }

        classMap.values().forEach(bean -> {
            Qualifier group = function.apply((Class<T>) bean.getClass());
            if (group == null) {
                return;
            }

            String key = group.value();
            List<T> value = map.get(key);
            if (value == null) {
                value = new ArrayList<>();
                map.put(key, value);
            }
            value.add(bean);
        });
    }

}
