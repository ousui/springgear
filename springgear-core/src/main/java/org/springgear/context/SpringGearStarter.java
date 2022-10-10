package org.springgear.context;

import org.springgear.beans.AbstractSpringGearProxyProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springgear.beans.DefaultBeanDefinitionProcessor;

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
 * @dae 2018-01-05
 **/
@Slf4j
public class SpringGearStarter implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware {

    /**
     * 需要扫描的包。
     */
    private String[] basePackages;

    /**
     * spring 上下文
     */
    private ApplicationContext applicationContext;


    private final AbstractSpringGearProxyProcessor beanDefinitionProcessor;

    public SpringGearStarter(String... basePackages) {
        this(new DefaultBeanDefinitionProcessor(), basePackages);
    }

    public SpringGearStarter(AbstractSpringGearProxyProcessor beanDefinitionProcessor, String... basePackages) {
        Assert.notEmpty(basePackages, "必须注入使用包名！");
        this.basePackages = basePackages;
        this.beanDefinitionProcessor = beanDefinitionProcessor;
    }




    @Override
    public void afterPropertiesSet() {
        Assert.notEmpty(basePackages, "必须注入使用包名！");
    }

    /**
     * 处理修改接口自动实现.
     *
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        SpringGearProxyClassPathScanner scanner = new SpringGearProxyClassPathScanner(beanDefinitionRegistry, this.beanDefinitionProcessor);

        scanner.setResourceLoader(this.applicationContext);
        scanner.scan(basePackages);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        保持空

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
