package org.springgear.core.beans;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springgear.core.engine.execute.results.SpringGearOriginalResultWrapper;
import org.springgear.core.engine.execute.SpringGearResultWrapper;
import org.springgear.core.engine.execute.SpringGearEngineParts;
import org.springgear.core.support.SpringGearEngineUtils;
import org.springgear.core.engine.execute.SpringGearEngineExecutor;
import org.springgear.core.annotation.SpringGearEngine;
import org.springgear.exception.SpringGearError;
import org.springgear.exception.SpringGearInterruptException;
import org.springgear.exception.SpringGearException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SpringGear 的动态代理实现类。
 * 这里使用内部类直接实现。
 */
@Slf4j
public class SpringGearProxyInstance implements InvocationHandler, Serializable {

    private final ApplicationContext applicationContext;

    /**
     * bean 缓存
     */
    private final Map<String, SpringGearEngineExecutor<?>> interfaceCachedMap = new ConcurrentHashMap<>();

    public SpringGearProxyInstance(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果是对象，直接返回。
        // 这里是基于接口扫描并注册的类，所以这里基本不可能。
        if (method.getDeclaringClass().equals(Object.class)) {
            return method.invoke(this, args);
        }

        // 获取代理方法的注解
        SpringGearEngine engineAnno = method.getAnnotation(SpringGearEngine.class);
        // 没有注解抛出异常，也可以考虑做一个默认的实现。
        if (null == engineAnno) {
            log.error("You may be not use annotation '{}' for this method ''.", SpringGearEngine.class, method.getName());
            throw new UnsupportedOperationException(
                    String.format("class '%s' is an interface, you must use annotation '@%s' to implementation method '%s'.",
                            proxy.getClass().getSimpleName(), SpringGearEngine.class.getSimpleName(), method.getName())
            );
        }

        // 获取指定的 core name
        String beanName = SpringGearEngineUtils.getInterfaceBeanName(engineAnno, method);
        // 单例
        SpringGearEngineExecutor<?> engine = this.getEngineBean(beanName);

        log.debug("Engine bean is {}", engine.getClass());

        Object resp = null;
        SpringGearError ex = null;

        SpringGearEngineParts parts = new SpringGearEngineParts(args, beanName, engineAnno);

        try {
            resp = engine.execute(parts);
        } catch (SpringGearException e) {
            ex = e;
            if (e instanceof SpringGearInterruptException) {
                resp = ((SpringGearInterruptException) e).getResponse();
            }
        } catch (SpringGearError e) {
            ex = e;
            log.error("Spring Gear Engine execute happened some error must fixed: ", e);
            resp = e.getLocalizedMessage();
        } catch (Exception e) {
            throw e;
        } finally {

        }

        SpringGearResultWrapper<?> wrapper = this.getResultWrapper(engineAnno.wrapper().value());

        log.debug("[{}] '{}' process context: {}", parts.getTimestamp(), beanName, parts);
        return wrapper.process(resp, ex, parts);
    }

    /**
     * 处理获取输出 wrapper
     *
     * @param beanName
     * @return
     */
    private SpringGearResultWrapper<?> getResultWrapper(String beanName) {
        if (false == StringUtils.hasText(beanName)) {
            beanName = SpringGearResultWrapper.DEFAULT_BEAN_NAME;
        }
        // 获取所有 wrapper 类
        Map<String, SpringGearResultWrapper> wrappers = this.applicationContext.getBeansOfType(SpringGearResultWrapper.class);

        SpringGearResultWrapper<?> wrapper = null;
        if (false == CollectionUtils.isEmpty(wrappers)) {
            wrapper = wrappers.get(beanName);
        }
        if (wrapper != null) {
            return wrapper;
        }
        return new SpringGearOriginalResultWrapper<>();
    }

    /**
     * 从缓存池或者 application context 中查找 bean
     *
     * @param engineBeanName
     * @return
     */
    private SpringGearEngineExecutor<?> getEngineBean(String engineBeanName) {
        SpringGearEngineExecutor<?> face = interfaceCachedMap.get(engineBeanName);
        if (face != null) {
            return face;
        }
        Object bean = applicationContext.getBean(engineBeanName);

        if (null == bean) {
            log.error("not found this bean '{}'. ", engineBeanName);
            throw new NoSuchBeanDefinitionException(engineBeanName);
        }

        if (!SpringGearEngineExecutor.class.isAssignableFrom(bean.getClass())) {
            log.error("Bean '{}' type '{}' is wrong, need type '{}'.", engineBeanName, bean.getClass(), SpringGearEngineExecutor.class);
            throw new TypeMismatchException(bean, SpringGearEngineExecutor.class);
        }

        face = (SpringGearEngineExecutor<?>) bean;

        // 将 bean 缓存，提高性能。
        interfaceCachedMap.put(engineBeanName, face);
        return face;
    }

}
